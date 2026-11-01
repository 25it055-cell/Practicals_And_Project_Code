public class Driver {
    public static void main(String[] args) {

        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);

        System.out.println("f1 prints: " + f1);
        System.out.println("f2 prints: " + f2);
        System.out.println("f3 prints: " + f3);

        boolean allEqual = f1.equals(f2) && f2.equals(f3);
        System.out.println("Are all fractions equal? " + allEqual);

        if (f2.toString().equals("1/2") && f2.equals(f1)) {
            System.out.println("Success: new Fraction(2,4) prints \"1/2\" and equals new Fraction(1,2).");
        }
    }
}

