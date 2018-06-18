package tokenizer.line;

import tokenizer.AbstractTokenizer;
import tokenizer.Scope;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public abstract class AbstractLineTokenizer implements AbstractTokenizer {
    /**
     * Tokenize
     */

    /**
     * Method to tokenize a reader
     *
     * @param reader to use
     * @return an array of array(line) of token
     * @throws IOException in case of  exception from the reader
     */
    public abstract Iterable<Iterable<String>> tokenize(Reader reader) throws IOException;

    /**
     * Method to tokenize a string
     *
     * @param s string to tokenize
     * @return an array of array (line) token
     */
    public Iterable<Iterable<String>> tokenize(String s) throws IOException {
            Reader r = new StringReader(s);
            Iterable<Iterable<String>> result = tokenize(r);
            r.close();
            return result;

    }

    public Scope getScope() {
        return Scope.LINE;
    }

}
