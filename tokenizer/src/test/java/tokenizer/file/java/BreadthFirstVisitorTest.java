package tokenizer.file.java;

/**import tokenizer.file.java.strategy.BreadthFirstStrategy;
import com.github.javaparser.ast.Node;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BreadthFirstVisitorTest {
    
    private TokenizerVisitor target;
    
    
    public BreadthFirstVisitorTest() {
    }
    
    @Before
    public void setUp() {
        target = new TokenizerVisitor(new BreadthFirstStrategy());
    }
    
    @Test
    public void testVisit_tokenizer() {
        // Setup
        Node root = (new TestJavaClass()).asAst();
        
        // Exercise
        target.startVisit(root);
        
        // Verify
        containsAtLeast(10, target.tokens().iterator());
    }
    
    @Test
    public void testVisit_noCommentInLiteral() {
        // Setup
        Node root = (new TestJavaClass()).asAst();

        // Exercise
        target.startVisit(root);

        // Verify
        containsNoComment(target.tokens());
    }
    
    private void containsAtLeast(int x, Iterator<?> iterator) {
        while (x > 0) {
            assertTrue(iterator.hasNext());
            iterator.next();
            x--;
        }
    }
    
    private void containsNoComment(Iterable<String> tokens) {
        for (String token : tokens) {
            assertFalse(token.contains("/*"));
        }
    }
    
}*/