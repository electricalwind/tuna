package modelling.exception;

import modelling.util.assertion.StackTraces;

public class TrainingFailedException extends Exception {
    
    public TrainingFailedException() {
        super(StackTraces.callingMethod());
    }

    public TrainingFailedException(Throwable cause) {
        super(StackTraces.callingMethod(), cause);
    }

    public TrainingFailedException(String message) {
        super(message);
    }

    public TrainingFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
