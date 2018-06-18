package tokenizer.file.java.xml;

import org.junit.jupiter.api.Test;
import tokenizer.file.java.exception.UnparsableException;

import java.io.IOException;


class JavaASTTokenizerTest {

    @Test
    public void test() throws IOException, UnparsableException {
        String code = "import java.io.IOException;\n" +
                "import java.io.Reader;\n" +
                "import java.io.StringReader;\n" +
                "\n" +
                "\n" +
                "public abstract class AbstractTokenizer {\n" +
                "\n" +
                "    /**\n" +
                "     * Tokenize\n" +
                "     */\n" +
                "\n" +
                "    /**\n" +
                "     * Method to tokenize a mutant\n" +
                "     *\n" +
                "     * @param mutant to use\n" +
                "     * @return an array of token on which all preprocessor registered have been applied\n" +
                "     * @throws IOException in case of mutant exception\n" +
                "     */\n" +
                "    public abstract String[] tokenize(Reader mutant) throws IOException;\n" +
                "\n" +
                "    /**\n" +
                "     * Method to tokenize a string\n" +
                "     *\n" +
                "     * @param s string to tokenize\n" +
                "     * @return an array of token on which all preprocessor registered have been applied\n" +
                "     */\n" +
                "    public String[] tokenize(String s) {\n" +
                "        try {\n" +
                "            Reader r = new StringReader(s);\n" +
                "            String[] result = tokenize(r);\n" +
                "            r.close();\n" +
                "            return result;\n" +
                "        } catch (IOException exception) {\n" +
                "            exception.printStackTrace();\n" +
                "            return null;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    /**\n" +
                "     * Type of the Tokenizer\n" +
                "     *\n" +
                "     * @return\n" +
                "     */\n" +
                "    public abstract String getType();\n" +
                "}\n";

        new JavaASTTokenizer().tokenize(code);
    }

}