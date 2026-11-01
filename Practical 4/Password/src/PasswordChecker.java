public class PasswordChecker {


    public static boolean hasMinLength(String password) {
        return password.length() >= 8;
    }

    public static boolean hasUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }


    public static boolean hasDigit(String password) {
        return password.matches(".*[0-9].*");
    }


    public static boolean hasSpecialCharacter(String password) {
        return password.matches(".*[^a-zA-Z0-9].*");
    }

    public static String strength(String password) {

        int count = 0;

        if (hasMinLength(password))
            count++;

        if (hasUpperCase(password))
            count++;

        if (hasDigit(password))
            count++;

        if (hasSpecialCharacter(password))
            count++;

        if (count <= 1)
            return "Weak";
        else if (count <= 3)
            return "Medium";
        else
            return "Strong";
    }
}
