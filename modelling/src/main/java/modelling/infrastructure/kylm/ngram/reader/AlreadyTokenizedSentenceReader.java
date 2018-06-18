package modelling.infrastructure.kylm.ngram.reader;

import java.util.Iterator;

public class AlreadyTokenizedSentenceReader implements SentenceReader {

    private final String[][] sents;

    public AlreadyTokenizedSentenceReader(String[][] sents) {
        this.sents = sents;
    }


    private class TASLIterator implements Iterator<String[]> {

        private int pos;
        private String[][] sents = null;

        public TASLIterator(String[][] sents) {
            pos = 0;
            this.sents = sents;
        }

        @Override
        public boolean hasNext() {
            return pos != sents.length;
        }

        @Override
        public String[] next() {
            return sents[pos++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not implemented");
        }

    }

    @Override
    public Iterator<String[]> iterator() {
        return new TASLIterator(sents);
    }

    @Override
    public boolean supportsReset() {
        return true;
    }
}
