package tokenizer.file.ccpp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;


class CPPLemmeFileTokenizerTest {

    Reader r;

    @BeforeEach
    public void setup() throws URISyntaxException, FileNotFoundException {
        URL uri = getClass().getResource("/testFile/tcp.c");
        r = new FileReader(new File(uri.toURI()));
    }

    @Test
    public void test() throws IOException {
        CPPLemmeFileTokenizer c = new CPPLemmeFileTokenizer();
        Iterable s = c.tokenize(r);
        assertTrue(s.iterator().hasNext());
    }


}