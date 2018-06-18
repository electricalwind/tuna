package tokenizer.file.java;

/**import com.github.javaparser.ast.Node;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import tokenizer.file.java.strategy.DepthFirstStrategy;

public class PrunedTokenizerVisitorTest {
    
    private PrunedTokenizerVisitor target;
    
    public PrunedTokenizerVisitorTest() {
    }
    
    @Before
    public void setUp() {
        target = new PrunedTokenizerVisitor(new DepthFirstStrategy());
    }
    
    @Test
    public void testVisit_tokenizer() {
        // Setup
        Node root = (new TestJavaClass()).asAst();
        String block = "BlockStmt";
        // Exercise
        target.startVisit(root);
        
        // Verify
        Iterable<String> result = target.tokens();
        assertNotNull(result);
        containsNot(block, result);
    }
    
    private void containsNot(String s, Iterable<String> set) {
        for(String element : set) {
            assertNotEquals(s, element);
        }
    }
}
*/