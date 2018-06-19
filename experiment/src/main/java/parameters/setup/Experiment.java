package parameters.setup;

import modelling.NgramModel;
import modelling.exception.TrainingFailedException;
import modelling.infrastructure.NgramModelKylmImpl;
import modelling.util.Pair;

import java.util.concurrent.Callable;

public class Experiment implements Callable<Pair<Setup,Double>>{
    private final Setup setup;
    private final Iterable<Iterable<String>> trainingSet;
    private final Iterable<Iterable<String>> testingSet;

    public Experiment(Setup setup,Iterable<Iterable<String>> trainingSet,
                      Iterable<Iterable<String>> testingSet) {
        this.setup = setup;
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;
    }

    @Override
    public Pair<Setup, Double> call() throws TrainingFailedException {
        NgramModel model = new NgramModelKylmImpl(
                setup.ngramSize(), setup.smoother(), setup.threshold());

        model.train(trainingSet);

        return new Pair<>(setup,model.averageCrossEntropy(testingSet));
    }
}
