package tokenizer.file.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import util.Assert;

/**
 * Provides a Java AST for testing
 * 
 * @author mcr
 */
/**public class TestJavaClass {
    
    private static final String PATH = "./testFilesJava/"; 
    
    public Node asAst() {
        
        final String FILE = "tokenizer";
        FileInputStream stream = null;
        
	try {
            ClassLoader classLoader = getClass().getClassLoader();
            stream = new FileInputStream(
                    classLoader.getResource(PATH+FILE).getFile());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Assert.shouldNeverGetHere();
        }
        
        return JavaParser.parse(stream);
    }
    
    public Reader asString() {
        final String FILE = "tokenizer";
        FileReader reader = null;
        
	try {
            ClassLoader classLoader = getClass().getClassLoader();
            reader = new FileReader(
                    classLoader.getResource(PATH + FILE).getFile());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Assert.shouldNeverGetHere();
        }
        
        return reader;
    }
    
}*/
