import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String email;
    private transient String secretPin;

    public UserAccount(int id, String username, String email, String secretPin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.secretPin = secretPin;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSecretPin() {
        return secretPin;
    }

    @Override
    public String toString() {
        return "UserAccount { id=" + id + ", username='" + username + "', email='" + email + "', secretPin='" + secretPin + "' }";
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        String filename = "accounts.ser";

        UserAccount[] originalAccounts = new UserAccount[] {
            new UserAccount(101, "alice_w", "alice@example.com", "Secret@991"),
            new UserAccount(102, "bob_m", "bob@example.com", "Secret@442"),
            new UserAccount(103, "charlie_b", "charlie@example.com", "Secret@123")
        };

        System.out.println("=== 1. OBJECTS BEFORE SERIALIZATION ===");
        for (UserAccount acc : originalAccounts) {
            System.out.println(acc);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(originalAccounts);
            System.out.println("\nSuccessfully saved array of " + originalAccounts.length + " objects to " + filename);
        } catch (Exception e) {
            System.err.println("Error saving objects: " + e.getMessage());
            return;
        }

        UserAccount[] loadedAccounts = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            loadedAccounts = (UserAccount[]) ois.readObject();
            System.out.println("\nSuccessfully loaded array of objects from " + filename);
        } catch (Exception e) {
            System.err.println("Error reading objects: " + e.getMessage());
            return;
        }

        System.out.println("\n=== 2. OBJECTS AFTER DESERIALIZATION ===");
        for (UserAccount acc : loadedAccounts) {
            System.out.println(acc);
        }

        System.out.println("\n=== 3. CONFIRMATION AND VERIFICATION ===");
        boolean allSurvived = true;
        boolean transientLost = true;

        for (int i = 0; i < originalAccounts.length; i++) {
            UserAccount orig = originalAccounts[i];
            UserAccount loaded = loadedAccounts[i];

            boolean idMatch = orig.getId() == loaded.getId();
            boolean userMatch = orig.getUsername().equals(loaded.getUsername());
            boolean emailMatch = orig.getEmail().equals(loaded.getEmail());
            boolean pinLost = loaded.getSecretPin() == null;

            System.out.println("Item [" + i + "] id match: " + idMatch
                    + " | username match: " + userMatch
                    + " | email match: " + emailMatch
                    + " | transient secretPin is null: " + pinLost);

            if (!idMatch || !userMatch || !emailMatch) {
                allSurvived = false;
            }
            if (!pinLost) {
                transientLost = false;
            }
        }

        System.out.println("\nSummary:");
        System.out.println("Serializable fields survived: " + allSurvived);
        System.out.println("Transient field correctly omitted: " + transientLost);
    }
}
