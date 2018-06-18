package tokenizer.file.java.xml;

import com.github.javaparser.GeneratedJavaParserConstants;
import com.github.javaparser.JavaParser;
import com.github.javaparser.JavaToken;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import tokenizer.file.AbstractFileTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import static com.github.javaparser.Providers.provider;

import tokenizer.file.java.exception.UnparsableException;
import util.Assert;

public class JavaLemmeWOCTokenizer extends AbstractFileTokenizer {

    public final static String TYPE = "JavaLemmeWOCTokenizer";

    @Override
    public List<String> tokenize(Reader reader) throws IOException, UnparsableException {
        List<String> tokens = new ArrayList<>();
        List<JavaToken> l = tokens(reader);
        if (l != null) {
            for (int i = 0; i < l.size(); i++) {
                JavaToken jt = l.get(i);
                if (!isComment(jt)) {
                    String res = jt.getText();
                    if (res.equals("\n") || !res.trim().isEmpty()) {
                        tokens.add(res);
                    }
                }
            }
            return tokens;
        } else return null;
    }

    public List<String> tokenize(Reader reader, int line) throws IOException, UnparsableException {
        List<String> result = new ArrayList<>();

        if (line < 1) {
            return result;
        }

        int currentLine = 1;
        List<JavaToken> l = tokens(reader);
        for (int i = 0; i < l.size(); i++) {
            JavaToken jt = l.get(i);
            if (isComment(jt)) {
                currentLine = currentLine + numberOfLines(jt);
            } else {
                String token = jt.getText();
                if (currentLine == line) {
                    if (!token.trim().isEmpty() && !token.equals("\n")) {
                        result.add(token);
                    }
                }
                if (token.contains("\n")) {
                    currentLine++;
                }
            }
        }

        return result;
    }

    private List<JavaToken> tokens(Reader reader) {
        JavaParser jp = new JavaParser(JavaParser.getStaticConfiguration());
        ParseResult<CompilationUnit> parseResult
                = jp.parse(COMPILATION_UNIT, provider(reader));
        if (parseResult.isSuccessful()) {
            return parseResult.getTokens().get();
        } else {
            return null;
        }
    }

    private boolean isComment(JavaToken token) {
        int kind = token.getKind();
        return kind == GeneratedJavaParserConstants.SINGLE_LINE_COMMENT
                || kind == GeneratedJavaParserConstants.MULTI_LINE_COMMENT
                || kind == GeneratedJavaParserConstants.JAVA_DOC_COMMENT;

    }

    private int numberOfLines(JavaToken token) {
        Assert.isTrue(isComment(token));

        int kind = token.getKind();
        if (kind == GeneratedJavaParserConstants.SINGLE_LINE_COMMENT) {
            return 1;
        } else {
            return numberOfLines(token.getText()) - 1;
        }
    }

    private int numberOfLines(String text) {
        Assert.notNull(text);

        return text.length() - text.replace("\n", "").length() + 1;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
