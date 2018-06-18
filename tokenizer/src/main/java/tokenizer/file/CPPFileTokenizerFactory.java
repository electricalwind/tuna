package tokenizer.file;

import tokenizer.file.ccpp.CPPASTFileTokenizer;
import tokenizer.file.ccpp.CPPLemmeFileTokenizer;

/**
 * /**
 * Factory for C CPP line Tokenizer
 */
public class CPPFileTokenizerFactory {

    public static final AbstractFileTokenizer createASTTokenizer() {
        return new CPPASTFileTokenizer();
    }

    public static final AbstractFileTokenizer createLemmeTokenizer() {
        return new CPPLemmeFileTokenizer();
    }

    public static final AbstractFileTokenizer createUTFTokenizer() {
        return new UTFFileTokenizer();
    }
}
