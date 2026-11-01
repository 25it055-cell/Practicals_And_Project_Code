import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
    String message() default "must not be blank";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
    String message() default "exceeds maximum allowed length";
}

class SignupForm {
    @NotBlank(message = "Username is required and cannot be blank")
    @MaxLength(value = 15, message = "Username cannot exceed 15 characters")
    private String username;

    @NotBlank(message = "Email is required and cannot be blank")
    @MaxLength(value = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @MaxLength(value = 20, message = "Password cannot exceed 20 characters")
    private String password;

    @MaxLength(value = 60, message = "Bio cannot exceed 60 characters")
    private String bio;

    public SignupForm(String username, String email, String password, String bio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }
}

class FormValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();

        if (obj == null) {
            errors.add("Target object to validate is null.");
            return errors;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                String fieldName = field.getName();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    NotBlank annotation = field.getAnnotation(NotBlank.class);
                    if (value == null || value.toString().trim().isEmpty()) {
                        errors.add(String.format("Error on '%s': %s", fieldName, annotation.message()));
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength annotation = field.getAnnotation(MaxLength.class);
                    int maxAllowed = annotation.value();

                    if (value != null) {
                        int actualLength = value.toString().length();
                        if (actualLength > maxAllowed) {
                            errors.add(String.format("Error on '%s': %s (Max: %d, Actual: %d)",
                                    fieldName, annotation.message(), maxAllowed, actualLength));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Unable to access field: " + field.getName() + " (" + e.getMessage() + ")");
            }
        }

        return errors;
    }
}

public class FormValidatorDemo {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("        PRACTICAL 7 - PART A (EXERCISE 1)         ");
        System.out.println("            CUSTOM FORM VALIDATOR DEMO            ");
        System.out.println("==================================================\n");

        System.out.println("--- Test Case 1: Valid Form Submission ---");
        SignupForm validForm = new SignupForm(
                "smeet_patel",
                "smeet@example.com",
                "SecurePass123",
                "Computer Science student and Java enthusiast."
        );
        validateAndPrint(validForm);

        System.out.println("--- Test Case 2: Invalid Form Submission ---");
        SignupForm invalidForm = new SignupForm(
                "super_ultra_long_username_exceeding_limit",
                "   ",
                null,
                "This bio is intentionally written to be extremely long and verbose so that it exceeds the sixty character limit defined for the field."
        );
        validateAndPrint(invalidForm);

        System.out.println("--- Test Case 3: Valid Form with Empty Optional Field ---");
        SignupForm optionalBioForm = new SignupForm(
                "alex_dev",
                "alex@domain.org",
                "P@ssw0rd99!",
                null
        );
        validateAndPrint(optionalBioForm);
    }

    private static void validateAndPrint(SignupForm form) {
        System.out.println("Validating form for user: " + form.getUsername());
        List<String> errors = FormValidator.validate(form);

        if (errors.isEmpty()) {
            System.out.println("Status: SUCCESS! No validation errors found.\n");
        } else {
            System.out.println("Status: FAILED! Found " + errors.size() + " validation error(s):");
            for (int i = 0; i < errors.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + errors.get(i));
            }
            System.out.println();
        }
    }
}
