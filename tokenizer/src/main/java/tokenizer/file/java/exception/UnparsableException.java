package tokenizer.file.java.exception;

/**
 *
 * @author mcr
 */
public class UnparsableException extends Exception {
    private final String parseResult;
    
    public UnparsableException(String parseResult) {
        this.parseResult = parseResult;
    }
    
    public String parseResult() {
        return this.parseResult;
    }
    
}
