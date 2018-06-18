package tokenizer.file;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * UTF File tokenizer
 * tokenizer that use everything non alphanumerical as a delimiter, delimiter in a row are kept together, while only space delimiter are removed.
 */
public class UTFFileTokenizer extends AbstractFileTokenizer {

    public final static String TYPE = "UTFTokenizer";

    @Override
    public Iterable tokenize(Reader reader) throws IOException {
        List<String> tokens = new LinkedList<>();
        int ch = reader.read();
        StringBuilder sw;
        String s;
        while (ch != -1) {
        /*
         * Delimiters
         */
            sw = new StringBuilder();

            while (ch != -1 && !(Character.isLetterOrDigit((char) ch) || Character.getType((char) ch) == Character.NON_SPACING_MARK || Character.getType((char) ch) == Character.COMBINING_SPACING_MARK)
                    ) {
                sw.append((char)ch);
                ch = reader.read();
            }
            s = sw.toString();
            if (!s.trim().isEmpty()) {
                tokens.add(s);
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
                tokens.add(s);
            }
        }
        return tokens;
    }

    @Override
    public Iterable tokenize(String s) {
        List<String> tokens = new LinkedList<>();
        int i = 0;
        StringBuilder sw;
        String res;
        while (i < s.length()) {
            /*
         * Delimiters
         */
            sw = new StringBuilder();
            while (i < s.length() && !(Character.isLetterOrDigit(s.charAt(i)) || Character.getType(s.charAt(i)) == Character.NON_SPACING_MARK || Character.getType(s.charAt(i)) == Character.COMBINING_SPACING_MARK)
                    ) {
                sw.append(s.charAt(i));
                i++;
            }
            res = sw.toString();
            if (!res.trim().isEmpty()) {
                tokens.add(res);
            }
            /*
              ContentT
             */
            sw = new StringBuilder();
            while (i < s.length() && (Character.isLetterOrDigit(s.charAt(i)) || Character.getType(s.charAt(i)) == Character.NON_SPACING_MARK || Character.getType(s.charAt(i)) == Character.COMBINING_SPACING_MARK)) {
                sw.append(s.charAt(i));
                i++;
            }
            res = sw.toString();
            if (res.length() != 0) {
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
