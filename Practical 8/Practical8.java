import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class Account {
    String name;

    @Positive
    double balance;

    @MaxLength(10)
    String accountType;

    Account(String name, double balance, String accountType) {
        this.name = name;
        this.balance = balance;
        this.accountType = accountType;
    }
}

class AnnotationValidator {

    public static String[] validate(Object obj) {
        String[] errors = new String[10];
        int count = 0;

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);

                if (field.isAnnotationPresent(Positive.class)) {
                    Object value = field.get(obj);

                    if (value instanceof Number) {
                        double number = ((Number) value).doubleValue();

                        if (number <= 0) {
                            errors[count++] =
                                field.getName() + " must be positive";
                        }
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    Object value = field.get(obj);

                    if (value instanceof String) {
                        MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                        int max = annotation.value();

                        if (((String) value).length() > max) {
                            errors[count++] =
                                field.getName() +
                                " must not exceed " + max + " characters";
                        }
                    }
                }

            } catch (Exception e) {
                errors[count++] = "Error checking " + field.getName();
            }
        }

        String[] result = new String[count];

        for (int i = 0; i < count; i++) {
            result[i] = errors[i];
        }

        return result;
    }
}

public class Practical8 {

    public static void main(String[] args) {

        Account acc = new Account("Smeet", -5000, "Savings");

        String[] errors = AnnotationValidator.validate(acc);

        for (String error : errors) {
            System.out.println(error);
        }
    }
}