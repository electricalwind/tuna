package tokenizer.line;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * UTF Line tokenizer
 * tokenizer that use everything non alphanumerical as a delimiter, delimiter in a row are kept together, while only space delimiter are removed.
 * tokenization is done on a line basis using \n as to distinguish between lines.
 */
public class UTFLineTokenizer extends AbstractLineTokenizer {
    public final static String TYPE = "UTFLineTokenizer";

    @Override
    public Iterable<Iterable<String>> tokenize(Reader reader) throws IOException {
        List<Iterable<String>> tokens = new LinkedList<>();
        int ch = reader.read();
        StringBuilder sw;
        String s;
        List<String> currentLine = new LinkedList<>();
        while (ch != -1) {
        /*
         * Delimiters
         */
            sw = new StringBuilder();

            while (ch != -1 && !(Character.isLetterOrDigit((char) ch) || Character.getType((char) ch) == Character.NON_SPACING_MARK || Character.getType((char) ch) == Character.COMBINING_SPACING_MARK)
                    ) {
                if ((char) ch == '\n'|| (char)ch == '\r') {
                    tokens.add(currentLine);
                    currentLine = new LinkedList<>();
                } else {
                    sw.append((char) ch);
                }
                ch = reader.read();
            }
            s = sw.toString();
            if (!s.trim().isEmpty()) {
                currentLine.add(s);
            }
            /*
              ContentT
             */
            sw = new StringBuilder();
            while (ch != -1 && (Character.isLetterOrDigit((char) ch) || Character.getType((char) ch) == Character.NON_SPACING_MARK || Character.getType((char) ch) == Character.COMBINING_SPACING_MARK)) {
                sw.append((char) ch);
                ch = reader.read();
            }
            s = sw.toString();
            if (s.length() != 0) {
                currentLine.add(s);
            }
        }
        tokens.add(currentLine);
        return tokens;
    }

    @Override
    public Iterable<Iterable<String>> tokenize(String s) {
        List<Iterable<String>> tokens = new LinkedList<>();
        int i = 0;
        StringBuilder sw;
        String res;
        List<String> currentLine = new LinkedList<>();
        while (i < s.length()) {
            /*
         * Delimiters
         */
            sw = new StringBuilder();
            while (i < s.length() && !(Character.isLetterOrDigit(s.charAt(i)) || Character.getType(s.charAt(i)) == Character.NON_SPACING_MARK || Character.getType(s.charAt(i)) == Character.COMBINING_SPACING_MARK)
                    ) {
                if (s.charAt(i) == '\n') {
                    tokens.add(currentLine);
                    currentLine = new LinkedList<>();
                } else {
                    sw.append(s.charAt(i));
                }
                i++;
            }
            res = sw.toString();
            if (!res.trim().isEmpty()) {
                currentLine.add(res);
            }

            sw = new StringBuilder();
            while (i < s.length() && (Character.isLetterOrDigit(s.charAt(i)) || Character.getType(s.charAt(i)) == Character.NON_SPACING_MARK || Character.getType(s.charAt(i)) == Character.COMBINING_SPACING_MARK)) {
                sw.append(s.charAt(i));
                i++;
            }
            res = sw.toString();
            if (res.length() != 0) {
                currentLine.add(res);
            }
        }
        tokens.add(currentLine);
        return tokens;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
