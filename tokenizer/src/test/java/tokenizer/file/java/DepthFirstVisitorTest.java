package tokenizer.file.java;
/**
import tokenizer.file.java.strategy.DepthFirstStrategy;
import com.github.javaparser.ast.Node;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepthFirstVisitorTest {
    
    private TokenizerVisitor target;
    
    
    public DepthFirstVisitorTest() {
    }
    
    @Before
    public void setUp() {
        target = new TokenizerVisitor(new DepthFirstStrategy());
    }
    
    @Test
    public void testVisit_tokenizer() {
        // Setup
        Node root = (new TestJavaClass()).asAst();
        
        // Exercise
        target.startVisit(root);
        
        // Verify
        Iterable<String> result = target.tokens();
        assertNotNull(result);
        containsAtLeast(10, result.iterator());
    }
    
    private void containsAtLeast(int x, Iterator<?> iterator) {
        while (x > 0) {
            assertTrue(iterator.hasNext());
            iterator.next();
            x--;
        }
    }
}*/
