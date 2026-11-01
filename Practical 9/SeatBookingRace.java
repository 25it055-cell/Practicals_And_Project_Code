class TicketBooking {

    int seatsLeft = 5;

    synchronized boolean book() {

        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
            System.out.println("Booking seat...");
            seatsLeft--;
            return true;
        }

        return false;
    }
}

public class SeatBookingRace {

    public static void main(String[] args) throws Exception {

        TicketBooking booking = new TicketBooking();

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {

            int id = i + 1;

            threads[i] = new Thread(() -> {
                if (booking.book()) {
                    System.out.println("Thread " + id + " booked a seat");
                } else {
                    System.out.println("Thread " + id + " failed");
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Seats left: " + booking.seatsLeft);
    }
}