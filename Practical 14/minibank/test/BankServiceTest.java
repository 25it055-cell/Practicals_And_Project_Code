package minibank.test;

import minibank.model.Account;
import minibank.service.BankService;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BankServiceTest {

    @Test
    public void testAddAccountIncreasesCount() {
        BankService service = new BankService();
        Assert.assertEquals(0, service.getAccountCount());

        service.addAccount(new Account("ACC-101", "Alice Smith", 500.0));
        Assert.assertEquals(1, service.getAccountCount());

        service.addAccount(new Account("ACC-102", "Bob Jones", 1200.0));
        Assert.assertEquals(2, service.getAccountCount());
    }

    @Test
    public void testInvalidDepositThrows() {
        BankService service = new BankService();
        service.addAccount(new Account("ACC-103", "Charlie Brown", 300.0));

        boolean thrown = false;
        try {
            service.deposit("ACC-103", -50.0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        Assert.assertTrue(thrown);
    }

    @Test
    public void testOverdraftWithdrawalThrows() {
        BankService service = new BankService();
        service.addAccount(new Account("ACC-104", "Diana Prince", 250.0));

        boolean thrown = false;
        try {
            service.withdraw("ACC-104", 500.0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        Assert.assertTrue(thrown);
    }

    @Test
    public void testSaveAndReloadDataUnchanged() throws Exception {
        String testFile = "test_accounts_backup.ser";
        BankService originalService = new BankService();
        originalService.addAccount(new Account("ACC-201", "Evan Wright", 1500.75));
        originalService.addAccount(new Account("ACC-202", "Fiona Gallagher", 3200.50));

        originalService.saveToFile(testFile);

        BankService reloadedService = new BankService();
        reloadedService.loadFromFile(testFile);

        Assert.assertEquals(originalService.getAccountCount(), reloadedService.getAccountCount());

        Account reloadedAcc1 = reloadedService.findAccount("ACC-201");
        Account reloadedAcc2 = reloadedService.findAccount("ACC-202");

        Assert.assertEquals("Evan Wright", reloadedAcc1.getAccountHolder());
        Assert.assertEquals(1500.75, reloadedAcc1.getBalance(), 0.001);

        Assert.assertEquals("Fiona Gallagher", reloadedAcc2.getAccountHolder());
        Assert.assertEquals(3200.50, reloadedAcc2.getBalance(), 0.001);

        new File(testFile).delete();
    }

    public static boolean runAllTests() {
        System.out.println("==================================================");
        System.out.println("          RUNNING JUNIT BANK SERVICE TESTS        ");
        System.out.println("==================================================");

        int passed = 0;
        int failed = 0;
        BankServiceTest suite = new BankServiceTest();
        Method[] methods = BankServiceTest.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                System.out.print("Running: " + method.getName() + " ... ");
                try {
                    method.invoke(suite);
                    System.out.println("PASSED");
                    passed++;
                } catch (InvocationTargetException ite) {
                    System.out.println("FAILED!");
                    System.out.println("  Reason: " + ite.getCause().getMessage());
                    failed++;
                } catch (Exception e) {
                    System.out.println("ERROR!");
                    System.out.println("  Reason: " + e.getMessage());
                    failed++;
                }
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Test Run Complete: " + passed + " Passed, " + failed + " Failed.");
        System.out.println("==================================================\n");
        return failed == 0;
    }

    public static void main(String[] args) {
        runAllTests();
    }
}
