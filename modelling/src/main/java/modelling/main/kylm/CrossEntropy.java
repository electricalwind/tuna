package modelling.main.kylm;

import modelling.infrastructure.kylm.LanguageModel;
import modelling.infrastructure.kylm.ngram.reader.SerializedNgramReader;
import modelling.util.MathUtils;
import modelling.util.SymbolSet;
import modelling.util.TextUtils;

import java.io.IOException;

public class CrossEntropy {

    private final LanguageModel mod;

    private static boolean DEBUG = true;

    public CrossEntropy(String modelPath) throws IOException {
        SerializedNgramReader snr = new SerializedNgramReader();
        mod = snr.read(modelPath);
    }

    public CrossEntropy(LanguageModel model) {
        this.mod = model;
    }

    public static String makeEnt(float all, float simp, float cls, float unk, String unkSym) {
        StringBuffer sb = new StringBuffer();
        sb.append(all);
        if (simp != all) {
            sb.append("(s=");
            sb.append(simp);
            if (cls != 0)
                sb.append(",c=").append(cls);
            if (unk != 0) {
                sb.append(",u");
                if (unkSym != null)
                    sb.append('[').append(unkSym).append(']');
                sb.append('=').append(unk);
            }
            sb.append(')');
        }
        return sb.toString();
    }

    public void computeCrossEntropy(String[][] sentences) {
        float words = 0, simples = 0,
                unknowns = 0, classes = 0;
        float wordSents, simpleSents,
                unkSents, classSents;
        float[] wordEnts, simpleEnts,
                unkEnts, classEnts;
        String[] unkSyms;
        int wordCount = 0,
                sentenceCount = 0;

        for (String[] sent : sentences) {
            wordCount += sent.length;
            sentenceCount++;
            // calculate
            wordEnts = mod.getWordEntropies(sent);
            words += (wordSents = MathUtils.sum(wordEnts));
            simpleEnts = mod.getSimpleEntropies();
            simples += (simpleSents = MathUtils.sum(simpleEnts));
            classEnts = mod.getClassEntropies();
            classes += (classSents = MathUtils.sum(classEnts));
            unkEnts = mod.getUnknownEntropies();
            unknowns += (unkSents = MathUtils.sum(unkEnts));
            unkSyms = new String[unkEnts.length];
            SymbolSet vocab = mod.getVocab();
            for (int j = 0; j < unkEnts.length; j++)
                if (unkEnts[j] != 0)
                    unkSyms[j] = vocab.getSymbol(mod.findUnknownId(sent[j]));

            if (DEBUG) {
                System.out.println(TextUtils.join(" ", sent));
                System.out.println(mod.getName() + ": " +
                        makeEnt(wordSents, simpleSents, classSents, unkSents, null));

                for (int j = 0; j < wordEnts.length; j++) {
                    System.out.print(" " + (j < sent.length ? sent[j] : mod.getTerminalSymbol()) + "\tent: ");
                    for (int i = 0; i < wordEnts.length; i++) {
                        if (i != 0) System.out.print(", ");
                        System.out.print(makeEnt(wordEnts[i], simpleEnts[i], classEnts[i], unkEnts[i], unkSyms[i]));
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        // change from log10
        final float log2 = (float) Math.log10(2);

        System.out.println("Found entropy over " + wordCount + " words, " + sentenceCount + " sentences");
        words /= wordCount * log2 * -1;
        simples /= wordCount * log2 * -1;
        unknowns /= wordCount * log2 * -1;
        classes /= wordCount * log2 * -1;
        System.out.print(mod.getName() + ": entropy=" +
                makeEnt(words, simples, classes, unknowns, null));
        System.out.println(", perplexity=" + Math.pow(2, words));
        System.out.println(mod.printReport());

    }

}
