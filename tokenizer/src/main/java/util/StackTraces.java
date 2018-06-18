package util;

final class StackTraces {

    /** The index of the calling method in the stack trace. */
    private static final int CALLING_METHOD_INDEX = 3;

    /** this cannot be instantiated */
    private StackTraces() {
        throw new AssertionError();
    }

    /**
     * @return the name of the calling method.
     */
    static String callingMethod() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return elements[CALLING_METHOD_INDEX].toString();
    }
}
