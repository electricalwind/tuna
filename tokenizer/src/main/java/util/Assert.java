package util;

import java.util.Collection;
import java.util.Objects;

public class Assert {
    
    /** this cannot be instantiated */
    private Assert() {
        throw new AssertionError();
    }

    /**
     * @effects Fails.
     */
    public static void shouldNeverGetHere() {
        throw new FailureException(StackTraces.callingMethod());
    }

    /**
     * @effects Fails on the unexpected exception e.
     */
    public static void failOnUnexpectedException(Exception e) {
        throw new FailureException(StackTraces.callingMethod(), e);
    }

    /**
     * @effects Asserts the given boolean expression.
     */
    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new FailureException(String.format("assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts the given boolean expression is false.
     */
    public static void isFalse(boolean expression) {
        if (expression) {
            throw new FailureException(String.format("assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts value1 == value2
     */
    public static void equals(long value1, long value2) {
        if (value1 != value2) {
            throw new FailureException(
                    String.format(
                            "equals assertion %s = %s broken at %s",
                            value1,
                            value2,
                            StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts the given objects are equals.
     */
    public static void equals(Object obj1, Object obj2) {
        if (!Objects.equals(obj1, obj2)) {
            throw new FailureException(String.format(
                    "equals assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts value1 != value2
     */
    public static void notEquals(long value1, long value2) {
        if (value1 == value2) {
            throw new FailureException(
                    String.format(
                            "not equals assertion %s != %s broken at %s",
                            value1,
                            value2,
                            StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts the given objects are not equals.
     */
    public static void notEquals(Object obj1, Object obj2) {
        if (Objects.equals(obj1, obj2)) {
            throw new FailureException(String.format(
                    "not equals assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts value in range [min..max]
     */
    public static void inRange(long value, long min, long max) {
        if (value < min || value > max) {
            throw new FailureException(String.format(
                    "between assertion %d in range [%d..%d] broken at %s",
                    value,
                    min,
                    max,
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts the given object is null.
     */
    public static void isNull(Object object) {
        if (object != null) {
            throw new FailureException(String.format(
                    "is null assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts the given object is not null.
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw new FailureException(String.format(
                    "not null assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts the string str is not empty, i.e., str != null &&
     *           str.length > 0.
     */
    public static void notEmpty(String str) {
        if (str == null || str.length() <= 0) {
            throw new FailureException(String.format(
                    "not empty assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts collection is not null and not empty.
     */
    public static <T> void notEmpty(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new FailureException(String.format(
                    "not empty assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    /**
     * @effects Asserts collection is not null and collection has no null
     *          element. Note it does not complain if collection is empty.
     */
    public static <T> void noNullElement(Collection<T> collection) {
        if (collection == null) {
            throw new FailureException(String.format(
                    "no null element assertion broken at %s",
                    StackTraces.callingMethod()));
        }

        for (T t : collection) {
            if (t == null) {
                throw new FailureException(String.format(
                        "no null element assertion broken at %s",
                        StackTraces.callingMethod()));
            }
        }
    }

    /**
     * @effects Asserts the string str is a numerical value
     */
    public static void isNumeric(String str) {
        if (str == null || str.length() <= 0 || !isNumber(str)) {
            throw new FailureException(String.format(
                    "numeric value assertion broken at %s",
                    StackTraces.callingMethod()));
        }
    }

    ////////////////////
    // HELPER METHODS
    ////////////////////

    /**
     * @return true iff str is a long value.
     */
    private static boolean isNumber(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}