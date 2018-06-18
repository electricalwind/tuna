package tokenizer.line;

import tokenizer.line.ccpp.CPPLemmeLineTokenizer;

/**
 * Factory for C CPP line Tokenizer
 */
public class CPPLineTokenizerFactory {


    public static AbstractLineTokenizer createUTFTokenizer() {
        return new UTFLineTokenizer();
    }

    public static AbstractLineTokenizer createLemmeTokenizer() {
        return new CPPLemmeLineTokenizer();
    }
}
