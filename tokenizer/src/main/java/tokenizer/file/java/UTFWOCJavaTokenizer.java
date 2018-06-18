package tokenizer.file.java;

import tokenizer.file.AbstractFileTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class UTFWOCJavaTokenizer extends AbstractFileTokenizer {

    public final static String TYPE = "UTFWOCJTokenizer";

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
                sw.append((char) ch);
                ch = reader.read();
            }
            s = sw.toString();
            if (s.contains("/*")) {
                char oldChar = ((char) ch);
                while (ch != -1 && (((char) ch) != '/' || oldChar != '*')) {
                    oldChar = (char) ch;
                    ch = reader.read();
                }
            } else if (s.contains("//")) {
                while (ch != -1 && ((char) ch) != '\n') {
                    ch = reader.read();
                }
            } else if (!s.trim().isEmpty()) {
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
        public String getType () {
            return TYPE;
        }
    }