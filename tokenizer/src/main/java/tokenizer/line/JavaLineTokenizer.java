package tokenizer.line;

import tokenizer.line.java.JavaLemmeLineTokenizer;

/**
 * Factory for Java Line Tokenizer
 */
public class JavaLineTokenizer {

    public static AbstractLineTokenizer createUTFTokenizer() {
        return new UTFLineTokenizer();
    }

    public static AbstractLineTokenizer createLemmeTokenizer() {
        return new JavaLemmeLineTokenizer();
    }
}
