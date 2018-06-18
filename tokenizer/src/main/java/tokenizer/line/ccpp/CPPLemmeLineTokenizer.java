package tokenizer.line.ccpp;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import tokenizer.line.AbstractLineTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizer relying on the CPP 14 Antlr grammar to tokenize
 * line are retrieved through the meta information of the tokens
 */
public class CPPLemmeLineTokenizer extends AbstractLineTokenizer {
    public final static String TYPE ="CPPLemmeLineTokenizer";
    @Override
    public Iterable<Iterable<String>> tokenize(Reader reader) throws IOException {
        List<Iterable<String>> tokens = new ArrayList<>();
        ANTLRInputStream inputStream = new ANTLRInputStream(reader);
        CPP14Lexer lexer = new CPP14Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        commonTokenStream.fill();
        List<org.antlr.v4.runtime.Token> list = commonTokenStream.getTokens();
        List<String> currentLine = new ArrayList<>();
        int line = 1;
        for (int i = 0; i < list.size(); i++) {
            int cline = list.get(i).getLine();
            while (line < cline) {
                tokens.add(currentLine);
                currentLine = new ArrayList<>();
                line++;
            }
            String res = list.get(i).getText();
            currentLine.add(res);
        }
        tokens.add(currentLine);
        return tokens;
    }

    @Override
    public String getType() {
        return TYPE;
    }


}
