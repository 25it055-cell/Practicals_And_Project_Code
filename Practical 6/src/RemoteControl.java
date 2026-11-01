interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
    }
}

class Fan implements Switchable {
    @Override
    public void on() {
        System.out.println("Fan is ON");
    }

    @Override
    public void off() {
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable {
    @Override
    public void on() {
        System.out.println("Light is ON");
    }

    @Override
    public void off() {
        System.out.println("Light is OFF");
    }
}

@FunctionalInterface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Fan fan = new Fan();
        Light light = new Light();

        Switchable[] devices = {fan, light};

        System.out.println("Toggling Devices:");

        for (Switchable device : devices) {
            device.toggle();
        }

        System.out.println("\nUsing Anonymous Class:");

        SwitchPolicy policy1 = new SwitchPolicy() {
            @Override
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println("Fan at 10 AM: " +
                policy1.maySwitchOn(fan, 10));

        System.out.println("Light at 11 PM: " +
                policy1.maySwitchOn(light, 23));

        System.out.println("\nUsing Lambda:");

        SwitchPolicy policy2 =
                (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println("Fan at 8 AM: " +
                policy2.maySwitchOn(fan, 8));

        System.out.println("Light at 2 AM: " +
                policy2.maySwitchOn(light, 2));
    }
}