import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();

    boolean required() default false;

    String defaultValue() default "";
}

class MissingColumnException extends RuntimeException {
    public MissingColumnException(String message) {
        super(message);
    }
}

class StudentRecord {
    @Column(name = "student_id", required = true)
    private int studentId;

    @Column(name = "full_name", required = true)
    private String fullName;

    @Column(name = "email_address", required = false, defaultValue = "no-reply@university.edu")
    private String email;

    @Column(name = "gpa", required = false, defaultValue = "0.0")
    private double gpa;

    @Column(name = "is_enrolled", required = false, defaultValue = "true")
    private boolean enrolled;

    @Column(name = "scholarship_amount", required = false, defaultValue = "0.0")
    private double scholarshipAmount;

    private String systemAuditStatus = "INITIALIZED_INTERNALLY";

    public StudentRecord() {
    }

    @Override
    public String toString() {
        return "StudentRecord {" +
                "studentId=" + studentId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", gpa=" + gpa +
                ", enrolled=" + enrolled +
                ", scholarshipAmount=$" + scholarshipAmount +
                ", systemAuditStatus='" + systemAuditStatus + '\'' +
                '}';
    }
}

class ColumnPopulator {
    public static <T> T populate(Class<T> clazz, String[] headerRow, String[] dataRow) {
        if (headerRow == null || dataRow == null) {
            throw new IllegalArgumentException("Header row and data row must not be null.");
        }

        Map<String, Integer> headerIndexMap = new HashMap<>();
        for (int i = 0; i < headerRow.length; i++) {
            if (headerRow[i] != null) {
                headerIndexMap.put(headerRow[i].trim().toLowerCase(), i);
            }
        }

        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }

                Column column = field.getAnnotation(Column.class);
                String targetColName = column.name().trim();
                String normalizedTarget = targetColName.toLowerCase();
                boolean isRequired = column.required();
                String defaultValue = column.defaultValue();

                field.setAccessible(true);

                if (!headerIndexMap.containsKey(normalizedTarget)) {
                    if (isRequired) {
                        throw new MissingColumnException(String.format(
                                "CRITICAL: Required column '%s' for field '%s' was NOT found in header row %s",
                                targetColName, field.getName(), Arrays.toString(headerRow)));
                    } else {
                        System.out.printf("  [NOTICE] Optional column '%s' missing from header. ", targetColName);
                        if (!defaultValue.isEmpty()) {
                            Object defVal = convertValue(field.getType(), defaultValue);
                            field.set(instance, defVal);
                            System.out.printf("Applied @Column defaultValue: '%s'\n", defaultValue);
                        } else {
                            System.out.printf("Retained Java default value: '%s'\n", field.get(instance));
                        }
                        continue;
                    }
                }

                int colIndex = headerIndexMap.get(normalizedTarget);
                String rawValue = null;

                if (colIndex < dataRow.length) {
                    rawValue = dataRow[colIndex];
                    if (rawValue != null) {
                        rawValue = rawValue.trim();
                    }
                }

                if (rawValue == null || rawValue.isEmpty()) {
                    if (isRequired) {
                        throw new MissingColumnException(String.format(
                                "CRITICAL: Column '%s' is required, but data row has empty value at index %d.",
                                targetColName, colIndex));
                    } else if (!defaultValue.isEmpty()) {
                        Object defVal = convertValue(field.getType(), defaultValue);
                        field.set(instance, defVal);
                        System.out.printf("  [NOTICE] Column '%s' had empty cell. Applied defaultValue: '%s'\n",
                                targetColName, defaultValue);
                    }
                    continue;
                }

                Object convertedValue = convertValue(field.getType(), rawValue);
                field.set(instance, convertedValue);
            }

            return instance;

        } catch (MissingColumnException mce) {
            throw mce;
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate object: " + e.getMessage(), e);
        }
    }

    private static Object convertValue(Class<?> type, String value) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + type.getName());
        }
    }
}

public class ColumnPopulatorDemo {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     PRACTICAL 7 - PART A (EXERCISE 3 - ADVANCED) ");
        System.out.println("        REFLECTION COLUMN-TO-OBJECT POPULATOR     ");
        System.out.println("==================================================\n");

        System.out.println("--- Scenario 1: Complete Data with Out-of-Order Columns ---");
        String[] headers1 = { "email_address", "student_id", "scholarship_amount", "gpa", "full_name", "is_enrolled",
                "extra_col" };
        String[] data1 = { "alice@stanford.edu", "202401", "12500.00", "3.85", "Alice Wonderland", "true",
                "ignored_data" };

        System.out.println("Header : " + Arrays.toString(headers1));
        System.out.println("Data   : " + Arrays.toString(data1));
        StudentRecord student1 = ColumnPopulator.populate(StudentRecord.class, headers1, data1);
        System.out.println("Result : " + student1);
        System.out.println();

        System.out.println("--- Scenario 2: Missing Optional Columns in Header ---");
        String[] headers2 = { "student_id", "full_name", "gpa", "is_enrolled" };
        String[] data2 = { "202402", "Bob Marley", "3.42", "false" };

        System.out.println("Header : " + Arrays.toString(headers2));
        System.out.println("Data   : " + Arrays.toString(data2));
        StudentRecord student2 = ColumnPopulator.populate(StudentRecord.class, headers2, data2);
        System.out.println("Result : " + student2);
        System.out.println();

        System.out.println("--- Scenario 3: Missing Required Column (Handling & Enforcement) ---");
        String[] headers3 = { "full_name", "gpa", "email_address" };
        String[] data3 = { "Charlie Brown", "2.95", "charlie@peanuts.org" };

        System.out.println("Header : " + Arrays.toString(headers3));
        System.out.println("Data   : " + Arrays.toString(data3));
        try {
            StudentRecord student3 = ColumnPopulator.populate(StudentRecord.class, headers3, data3);
            System.out.println("Result : " + student3);
        } catch (MissingColumnException ex) {
            System.out.println("Caught Expected Exception: " + ex.getMessage());
            System.out
                    .println("Handling Policy: Required columns must be present; aborting mapping for invalid schema.");
        }

        System.out.println("\n==================================================");
        System.out.println("               DEMONSTRATION COMPLETE             ");
        System.out.println("==================================================");
    }
}
