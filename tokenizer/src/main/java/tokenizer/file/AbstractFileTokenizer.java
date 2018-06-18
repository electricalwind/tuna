package tokenizer.file;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import tokenizer.AbstractTokenizer;
import tokenizer.Scope;
import tokenizer.file.java.exception.UnparsableException;


public abstract class AbstractFileTokenizer implements AbstractTokenizer {

    /**
     * Tokenize
     */

    /**
     * Method to tokenize a reader
     *
     * @param reader to use
     * @return an array of tokens on which all preprocessor registered have been applied
     * @throws IOException         in case of reader exception
     * @throws UnparsableException if the content of reader could not be parsed
     */
    public abstract Iterable<String> tokenize(Reader reader) throws IOException, UnparsableException;

    /**
     * Method to tokenize a string
     *
     * @param s string to tokenize
     * @return an array of token on which all preprocessor registered have been applied
     */
    public Iterable<String> tokenize(String s) throws IOException, UnparsableException {
            Reader r = new StringReader(s);
            Iterable result = tokenize(r);
            r.close();
            return result;
    }

    public Scope getScope() {
        return Scope.FILE;
    }
}
