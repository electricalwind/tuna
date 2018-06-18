package util;

public class FailureException extends RuntimeException {

    /**
     * @effects Initializes this to be a new failure exception with the message
     *          being the name of the calling method.
     */
    public FailureException() {
        super(StackTraces.callingMethod());
    }

    /**
     * @effects Initializes this to be a new failure exception with the given
     *          cause and the message being the name of the calling method.
     */
    public FailureException(Throwable cause) {
        super(StackTraces.callingMethod(), cause);
    }

    /**
     * @effects Initializes this to be a new failure exception with the
     *          provided message.
     */
    public FailureException(String message) {
        super(message);
    }

    /**
     * @effects Initializes this to be a new failure exception with the
     *          provided message and cause.
     */
    public FailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
