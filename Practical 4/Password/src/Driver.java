public class Driver {

    public static void main(String[] args) {

        String[] passwords = {
                "abc",
                "password",
                "Password",
                "Password1",
                "Abcd1234!",
                "Hello@123",
                "JAVA123",
                "Java@2026"
        };

        for (String pw : passwords) {

            System.out.println("----------------------------------");
            System.out.println("Password : " + pw);

            System.out.println("Length >= 8 : "
                    + PasswordChecker.hasMinLength(pw));

            System.out.println("Has Uppercase : "
                    + PasswordChecker.hasUpperCase(pw));

            System.out.println("Has Digit : "
                    + PasswordChecker.hasDigit(pw));

            System.out.println("Has Special Character : "
                    + PasswordChecker.hasSpecialCharacter(pw));

            System.out.println("Strength : "
                    + PasswordChecker.strength(pw));
        }
    }
}