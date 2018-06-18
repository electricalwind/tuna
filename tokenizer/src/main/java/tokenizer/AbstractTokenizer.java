package tokenizer;

/**
 * AbstractTokenizer interface
 */
public interface AbstractTokenizer {

    /**
     * Scope of the tokenizer, lines or files
     * @return the Scope
     */
    Scope getScope();
    /**
     * Type of the Tokenizer
     * @return the type of tokenizer
     */
    String getType();
}
