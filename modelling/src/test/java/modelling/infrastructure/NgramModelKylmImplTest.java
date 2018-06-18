package modelling.infrastructure;

import java.util.LinkedList;
import java.util.List;

import modelling.exception.TrainingFailedException;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

public class NgramModelKylmImplTest {

    private static final double ACCURACY = 0.001;
    private static final int SIZE = 3;


    private NgramModelKylmImpl target;

    @Before
    public void setUp() {
        target = new NgramModelKylmImpl(
                SIZE, KylmSmootherFactory.maximumLikelihood());
    }


    @Test
    public void testTrain_allSame() throws TrainingFailedException {
        // Setup
        List<Iterable<String>> trainingSet = new LinkedList<>();
        trainingSet.add(document());
        trainingSet.add(document());
        trainingSet.add(document());

        // Exercise
        target.train(trainingSet);

        // Verify
        assertEquals(0.0, target.crossEntropy(document()), ACCURACY);
    }

    @Test
    public void testTrain() throws TrainingFailedException {
        // Setup
        List<Iterable<String>> trainingSet = new LinkedList<>();
        trainingSet.add(document());
        trainingSet.add(document());
        trainingSet.add(document_Inverted());

        // Exercise
        target.train(trainingSet);

        // Verify
        assertTrue(target.crossEntropy(document())
                < target.crossEntropy(document_Inverted()));
    }

    private Iterable<String> document() {
        List<String> result = new LinkedList<>();

        result.add("a");
        result.add("b");
        result.add("c");
        result.add("d");
        result.add("e");

        return result;
    }

    private Iterable<String> document_Inverted() {
        List<String> result = new LinkedList<>();

        result.add("a");
        result.add("b");
        result.add("c");
        result.add("e");
        result.add("d");

        return result;
    }
}
