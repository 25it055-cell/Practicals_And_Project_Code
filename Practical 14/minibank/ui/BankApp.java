package minibank.ui;

import minibank.model.Account;
import minibank.service.BankService;
import minibank.test.BankServiceTest;

import java.util.List;
import java.util.Scanner;

public class BankApp {
    private static final String DATA_FILE = "bank_accounts.ser";
    private BankService bankService;
    private Scanner scanner;

    public BankApp() {
        this.bankService = new BankService();
        this.scanner = new Scanner(System.in);
        loadDefaultSampleData();
    }

    private void loadDefaultSampleData() {
        bankService.addAccount(new Account("ACC-101", "Alice Smith", 1250.00));
        bankService.addAccount(new Account("ACC-102", "Bob Jones", 3400.50));
        bankService.addAccount(new Account("ACC-103", "Charlie Brown", 500.25));
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice (1-8): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleCreateAccount();
                    break;
                case "2":
                    handleViewAllAccounts();
                    break;
                case "3":
                    handleDeposit();
                    break;
                case "4":
                    handleWithdraw();
                    break;
                case "5":
                    handleSaveData();
                    break;
                case "6":
                    handleLoadData();
                    break;
                case "7":
                    BankServiceTest.runAllTests();
                    break;
                case "8":
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please choose 1 through 8.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("==========================================");
        System.out.println("          MINIBANK APPLICATION            ");
        System.out.println("==========================================");
        System.out.println("1. Create New Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Save Accounts to File");
        System.out.println("6. Load Accounts from File");
        System.out.println("7. Run Automated Tests");
        System.out.println("8. Exit");
        System.out.println("------------------------------------------");
    }

    private void handleCreateAccount() {
        try {
            System.out.print("Enter Account Number: ");
            String accNum = scanner.nextLine().trim();
            System.out.print("Enter Account Holder Name: ");
            String holder = scanner.nextLine().trim();
            System.out.print("Enter Initial Deposit: ");
            double initBal = Double.parseDouble(scanner.nextLine().trim());

            bankService.addAccount(new Account(accNum, holder, initBal));
            System.out.println("Account created successfully!\n");
        } catch (Exception e) {
            System.out.println("Failed to create account: " + e.getMessage() + "\n");
        }
    }

    private void handleViewAllAccounts() {
        List<Account> accounts = bankService.getAllAccounts();
        System.out.println("\n--- All Registered Accounts (" + accounts.size() + ") ---");
        System.out.println(String.format("%-12s | %-20s | %-12s", "ACCOUNT NO", "HOLDER NAME", "BALANCE"));
        System.out.println("-------------------------------------------------");
        for (Account acc : accounts) {
            System.out.println(String.format("%-12s | %-20s | $%-12.2f",
                    acc.getAccountNumber(), acc.getAccountHolder(), acc.getBalance()));
        }
        System.out.println();
    }

    private void handleDeposit() {
        try {
            System.out.print("Enter Account Number: ");
            String accNum = scanner.nextLine().trim();
            System.out.print("Enter Deposit Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.deposit(accNum, amount);
            Account acc = bankService.findAccount(accNum);
            System.out.println("Deposit successful! New balance: $" + String.format("%.2f", acc.getBalance()) + "\n");
        } catch (Exception e) {
            System.out.println("Deposit failed: " + e.getMessage() + "\n");
        }
    }

    private void handleWithdraw() {
        try {
            System.out.print("Enter Account Number: ");
            String accNum = scanner.nextLine().trim();
            System.out.print("Enter Withdrawal Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.withdraw(accNum, amount);
            Account acc = bankService.findAccount(accNum);
            System.out.println("Withdrawal successful! New balance: $" + String.format("%.2f", acc.getBalance()) + "\n");
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage() + "\n");
        }
    }

    private void handleSaveData() {
        try {
            bankService.saveToFile(DATA_FILE);
            System.out.println("Saved " + bankService.getAccountCount() + " accounts to " + DATA_FILE + " successfully.\n");
        } catch (Exception e) {
            System.out.println("Failed to save data: " + e.getMessage() + "\n");
        }
    }

    private void handleLoadData() {
        try {
            bankService.loadFromFile(DATA_FILE);
            System.out.println("Loaded " + bankService.getAccountCount() + " accounts from " + DATA_FILE + " successfully.\n");
        } catch (Exception e) {
            System.out.println("Failed to load data: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--test")) {
            boolean allPassed = BankServiceTest.runAllTests();
            System.exit(allPassed ? 0 : 1);
        } else if (args.length > 0 && args[0].equalsIgnoreCase("--demo")) {
            BankApp app = new BankApp();
            app.handleViewAllAccounts();
            BankServiceTest.runAllTests();
        } else {
            BankApp app = new BankApp();
            app.start();
        }
    }
}
