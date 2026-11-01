import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngineDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Double> prices = new ArrayList<>();

        prices.add(1000.0);
        prices.add(2500.0);
        prices.add(5000.0);
        prices.add(8000.0);

        System.out.println("===== DISCOUNT ENGINE =====");
        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. Flat Rs.500 Discount");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        DiscountRule rule;

        switch (choice) {
            case 1:
                rule = price -> price * 0.90;
                break;

            case 2:
                rule = price -> price * 0.80;
                break;

            case 3:
                rule = price -> Math.max(0, price - 500);
                break;

            default:
                System.out.println("Invalid choice!");
                sc.close();
                return;
        }

        System.out.println("\nPrices after discount:");

        for (double price : prices) {
            double finalPrice = rule.apply(price);

            System.out.printf(
                    "Original Price: Rs.%.2f -> Final Price: Rs.%.2f%n",
                    price,
                    finalPrice
            );
        }

        sc.close();
    }
}
