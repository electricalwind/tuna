package tokenizer.file.ccpp;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import tokenizer.file.AbstractFileTokenizer;
import tokenizer.line.ccpp.CPP14Lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Tokenizer relying on the CPP 14 Antlr grammar to tokenize
 */
public class CPPLemmeFileTokenizer extends AbstractFileTokenizer {
    public final static String TYPE = "CPPLemmeFileTokenizer";

    @Override
    public Iterable<String> tokenize(Reader reader) throws IOException {
        List<String> tokens = new ArrayList<>();
        ANTLRInputStream inputStream = new ANTLRInputStream(reader);
        CPP14Lexer lexer = new CPP14Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        commonTokenStream.fill();
        List<org.antlr.v4.runtime.Token> list = commonTokenStream.getTokens();
        for (int i = 0; i < list.size(); i++) {
            String res = list.get(i).getText();
            tokens.add(res);
        }
        return tokens;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
