import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
    String description() default "";
}

class MathServiceTest {
    public void setupEnvironment() {
        System.out.println("    [Helper] setupEnvironment() called.");
    }

    @Run(description = "Verify basic integer addition")
    public void testAddition() {
        int result = 10 + 25;
        if (result != 35) {
            throw new AssertionError("Addition failed: expected 35, got " + result);
        }
        System.out.println("    [PASS] testAddition: 10 + 25 == 35");
    }

    @Run(description = "Verify integer multiplication")
    public void testMultiplication() {
        int result = 6 * 7;
        if (result != 42) {
            throw new AssertionError("Multiplication failed: expected 42, got " + result);
        }
        System.out.println("    [PASS] testMultiplication: 6 * 7 == 42");
    }

    @Run(description = "Verify square root calculation")
    public void testSquareRoot() {
        double sqrt = Math.sqrt(64.0);
        if (Double.compare(sqrt, 8.0) != 0) {
            throw new AssertionError("Square root calculation mismatch!");
        }
        System.out.println("    [PASS] testSquareRoot: sqrt(64.0) == 8.0");
    }

    @Run(description = "Verify division by zero handling (intentional failure demo)")
    public void testIntentionalFailure() {
        throw new AssertionError("Intentional failure: Expected value did not match received value!");
    }

    @Run(description = "Verify exception handling (intentional runtime exception)")
    public void testRuntimeException() {
        String nullStr = null;
        nullStr.length();
    }

    public void logDiagnostics() {
        System.out.println("    [Helper] logDiagnostics() called.");
    }

    @Run(description = "Parameterized method - should be skipped by no-arg runner")
    public void testWithParameters(int param) {
        System.out.println("    This should not be invoked automatically without arguments.");
    }
}

class MiniTestRunner {
    public static void runTests(Class<?> testClass) {
        System.out.println("==================================================");
        System.out.println("Running tests for: " + testClass.getSimpleName());
        System.out.println("==================================================");

        int totalAnnotated = 0;
        int totalRan = 0;
        int passedCount = 0;
        int failedCount = 0;
        int skippedCount = 0;

        Object testInstance = null;
        try {
            testInstance = testClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Fatal: Could not instantiate test class " + testClass.getName() + ": " + e.getMessage());
            return;
        }

        Method[] methods = testClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Run.class)) {
                totalAnnotated++;
                Run runAnnotation = method.getAnnotation(Run.class);
                String desc = runAnnotation.description().isEmpty() ? "" : " - " + runAnnotation.description();

                if (method.getParameterCount() != 0) {
                    System.out.println("[SKIP] " + method.getName() + "() requires "
                            + method.getParameterCount() + " parameter(s). Only no-arg methods are supported.");
                    skippedCount++;
                    continue;
                }

                totalRan++;
                System.out.println("\n-> Invoking @Run: " + method.getName() + "()" + desc);

                try {
                    method.setAccessible(true);
                    method.invoke(testInstance);
                    passedCount++;
                } catch (InvocationTargetException ite) {
                    Throwable cause = ite.getCause();
                    failedCount++;
                    System.out.println("    [FAIL] " + method.getName() + " failed!");
                    System.out.println("           Reason: " + cause.getClass().getSimpleName() + ": " + cause.getMessage());
                } catch (IllegalAccessException iae) {
                    failedCount++;
                    System.out.println("    [ERROR] Cannot access method " + method.getName() + ": " + iae.getMessage());
                }
            }
        }

        System.out.println("\n--------------------------------------------------");
        System.out.println("                TEST RUN REPORT                   ");
        System.out.println("--------------------------------------------------");
        System.out.println("Total @Run methods found   : " + totalAnnotated);
        System.out.println("Total @Run methods ran     : " + totalRan);
        System.out.println("Passed                     : " + passedCount);
        System.out.println("Failed                     : " + failedCount);
        System.out.println("Skipped (requires args)    : " + skippedCount);
        System.out.println("--------------------------------------------------");

        if (failedCount == 0 && totalRan > 0) {
            System.out.println("OVERALL RESULT: ALL EXECUTED TESTS PASSED!");
        } else {
            System.out.println("OVERALL RESULT: SOME TESTS FAILED (" + failedCount + " failure(s)).");
        }
        System.out.println("==================================================\n");
    }
}

public class MiniTestRunnerDemo {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("        PRACTICAL 7 - PART A (EXERCISE 2)         ");
        System.out.println("             MINI TEST RUNNER DEMO                ");
        System.out.println("==================================================\n");

        MiniTestRunner.runTests(MathServiceTest.class);
    }
}
