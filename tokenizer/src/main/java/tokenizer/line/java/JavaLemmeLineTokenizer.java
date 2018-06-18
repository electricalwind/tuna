package tokenizer.line.java;

import com.github.javaparser.GeneratedJavaParserConstants;
import com.github.javaparser.JavaParser;
import com.github.javaparser.JavaToken;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import tokenizer.line.AbstractLineTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import static com.github.javaparser.Providers.provider;

/**
 * Java Lemme Line tokenizer (comments are removed)
 * Rely on javaparser, comments are removed
 * Separated according to line of tokens
 */
public class JavaLemmeLineTokenizer extends AbstractLineTokenizer {

    public final static String TYPE = "JavaLemmeLineWOCTokenizer";

    @Override
    public Iterable<Iterable<String>> tokenize(Reader reader) throws IOException {
        List<Iterable<String>> tokens = new ArrayList<>();
        List<JavaToken> l = tokens(reader);
        List<String> currentLine = new ArrayList<>();
        int line = 1;
        for (int i = 0; i < l.size(); i++) {
            JavaToken jt = l.get(i);
            int tline = jt.getRange().begin.line;
            while (line < tline) {
                tokens.add(currentLine);
                currentLine = new ArrayList<>();
                line++;
            }
            int kind = jt.getKind();
            if (kind != GeneratedJavaParserConstants.SINGLE_LINE_COMMENT
                    && kind != GeneratedJavaParserConstants.MULTI_LINE_COMMENT
                    && kind != GeneratedJavaParserConstants.JAVA_DOC_COMMENT) {
                String res = jt.getText();
                if (res.equals("\n") || !res.trim().isEmpty()) {
                    currentLine.add(res);
                }
            }
        }
        tokens.add(currentLine);

        return tokens;
    }


    private List<JavaToken> tokens(Reader reader) {
        JavaParser jp = new JavaParser(JavaParser.getStaticConfiguration());
        ParseResult<CompilationUnit> parseResult
                = jp.parse(COMPILATION_UNIT, provider(reader));
        if (parseResult.isSuccessful()) {
            return parseResult.getTokens().get();
        } else {

            //Assert.shouldNeverGetHere();
            return new ArrayList<>();
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }
}