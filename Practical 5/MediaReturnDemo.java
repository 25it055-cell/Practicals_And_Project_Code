
abstract class Media {
    private String title;
    private int daysLate;

    public Media(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    public String getTitle() { return title; }
    public int getDaysLate() { return daysLate; }

    public abstract double computeLateFee();
}


class Book extends Media {
    private static final double DAILY_RATE = 0.50; 

    public Book(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double computeLateFee() {
        return getDaysLate() * DAILY_RATE;
    }
}

class Dvd extends Media {
    private static final double DAILY_RATE = 2.00; 

    public Dvd(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double computeLateFee() {
        return getDaysLate() * DAILY_RATE;
    }
}

class VideoGame extends Media {
    private static final double DAILY_RATE = 3.50;
    private static final double FLAT_PENALTY = 5.00;

    public VideoGame(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double computeLateFee() {
        if (getDaysLate() <= 0) return 0;
        return FLAT_PENALTY + (getDaysLate() * DAILY_RATE);
    }
}

public class MediaReturnDemo {
    public static void main(String[] args) {

        Media[] returnedBatch = {
            new Book("The Great Gatsby", 4),
            new Dvd("Inception Movie", 2),
            new VideoGame("Cyberpunk 2077", 3),
            new Book("Clean Code Guide", 0) 
        };

        double totalBatchFees = 0;

        System.out.println("--- Batch Return Settlement System ---");
        for (Media item : returnedBatch) {
            double itemFee = item.computeLateFee();
            totalBatchFees += itemFee;

            System.out.printf("Item: %-20s | Class: %-10s | Days Late: %-2d | Fee: $%.2f%n", 
                    item.getTitle(), item.getClass().getSimpleName(), item.getDaysLate(), itemFee);
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf("Total Balanced Invoice Amount Due: $%.2f%n", totalBatchFees);
    }
}
