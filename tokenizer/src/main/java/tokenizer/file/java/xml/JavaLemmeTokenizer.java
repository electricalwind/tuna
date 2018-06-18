package tokenizer.file.java.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.JavaToken;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import tokenizer.file.AbstractFileTokenizer;

import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import static com.github.javaparser.Providers.provider;

public class JavaLemmeTokenizer extends AbstractFileTokenizer {

    public final static String TYPE = "JavaLemmeTokenizer";

    @Override
    public Iterable<String> tokenize(Reader reader) throws IOException {
        JavaParser jp = new JavaParser(JavaParser.getStaticConfiguration());
        ParseResult<CompilationUnit> result = jp.parse(COMPILATION_UNIT, provider(reader));
        List<String> tokens = null;
        if (result.isSuccessful()) {
            tokens = new ArrayList<>();
            List<JavaToken> l = result.getTokens().get();
            for (int i = 0; i < l.size(); i++) {
                JavaToken jt = l.get(i);
                String res = jt.getText();
                if (res.equals("\n") || !res.trim().isEmpty())
                    tokens.add(res);
            }
        }
        return tokens;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
