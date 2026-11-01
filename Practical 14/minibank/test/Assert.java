package minibank.test;

import java.util.Objects;

public class Assert {
    public static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Assertion Failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertEquals(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError("Assertion Failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Assertion Failed! Condition was false.");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Assertion Failed! Condition was true.");
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Assertion Failed! Object was null.");
        }
    }

    public static void fail(String message) {
        throw new AssertionError("Assertion Failed! " + message);
    }
}
