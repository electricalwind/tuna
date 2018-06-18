package tokenizer.file.java.xml;

import org.junit.Test;
import static org.junit.Assert.*;
//import tokenizer.file.java.TestJavaClass;

/**
 *
 * @author mcr
 */
/**public class JavaLemmeWOCTokenizerTest {
    
    public JavaLemmeWOCTokenizer target = new JavaLemmeWOCTokenizer();
    
    public JavaLemmeWOCTokenizerTest() {
    }

    @Test
    public void testTokenize_line() throws Exception {
        // Setup
        TestJavaClass testFile = new TestJavaClass();
        int line = 39;
        String expected 
                = "[String, [, ], result, =, tokenize, (, r, ,, q, ), ;]";
        
        // Exercise & Verify
        assertEquals(expected, 
                target.tokenize(testFile.asString(), line).toString());
    }
    
}
*/