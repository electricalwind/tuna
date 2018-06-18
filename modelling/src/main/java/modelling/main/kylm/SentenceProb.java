package modelling.main.kylm;

import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.reader.SerializedNgramReader;
import modelling.util.TextUtils;

import java.io.IOException;

public class SentenceProb {

    private final NgramLM mod;

    private static boolean DEBUG = true;

    public SentenceProb(String modelPath) throws IOException {
        SerializedNgramReader snr = new SerializedNgramReader();
        mod = snr.read(modelPath);
    }

    public SentenceProb(NgramLM model) {
        this.mod = model;
    }

    public void sentenceProbability(String[][] sentences) {
        for (String[] sent : sentences) {
            float prob = mod.getSentenceProb(sent);
            System.out.println("Log likelihood of sentence \"" + TextUtils.join(" ", sent) +
                    "\": " + prob + "(" + prob / sent.length + ")");
        }
    }

}
