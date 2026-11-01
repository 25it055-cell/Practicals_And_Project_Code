@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

class UrgentNotifier implements Notifier, Urgent {
    private Notifier notifier;

    public UrgentNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}

public class NotificationDemo {
    public static void main(String[] args) {
        Notifier email =
                message -> System.out.println("Email: " + message);

        Notifier sms =
                message -> System.out.println("SMS: " + message);

        Notifier urgentEmail =
                new UrgentNotifier(
                        message -> System.out.println(
                                "Urgent Email: " + message)
                );

        Notifier[] senders = {
                email,
                sms,
                urgentEmail
        };

        String message = "Meeting starts at 10 AM.";

        System.out.println("Broadcasting message:\n");

        for (Notifier sender : senders) {
            sender.send(message);

            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}