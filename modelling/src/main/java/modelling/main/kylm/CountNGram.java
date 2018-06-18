package modelling.main.kylm;

import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;
import modelling.infrastructure.kylm.ngram.writer.NgramWriter;
import modelling.infrastructure.kylm.ngram.writer.SerializedNgramWriter;
import modelling.infrastructure.kylm.ngram.reader.AlreadyTokenizedSentenceReader;
import modelling.infrastructure.kylm.ngram.reader.SentenceReader;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CountNGram {

    private final NgramWriter writer;
    private final NgramLM lm;
    private final String smoothing;
    private String destFolder;
    private String name;
    private int n;
    private int unkCutOff;
    private NgramSmoother smoother;

    public CountNGram(String destFolder, String name, int n, int unkCutOff, String smoothing) {

        this.destFolder = destFolder;
        this.name = name;
        this.n = n;
        this.unkCutOff = unkCutOff;
        this.smoothing = smoothing;

        this.smoother = SmootherFactory.create(smoothing);

        writer = new SerializedNgramWriter();
        lm = new NgramLM(n, smoother);
        lm.setName(name);
        lm.setVocabFrequency(unkCutOff);

    }

    public void launchTraining(String[][] textToTrainOn) throws Exception {
        SentenceReader loader = new AlreadyTokenizedSentenceReader(textToTrainOn);
        lm.trainModel(loader);
    }

    public NgramLM getLm() {
        return lm;
    }

    public void serialize() throws IOException {
        String nameOFModel = new StringBuilder()
                .append(destFolder)
                .append(n)
                .append(smoothing)
                .append(unkCutOff).toString();
        BufferedOutputStream os = new BufferedOutputStream(
                new FileOutputStream(nameOFModel), 16384
        );

        writer.write(lm, os);
        os.close();
    }

}
