package parameters.setup;

/*-
 * #%L
 * tuna
 * %%
 * Copyright (C) 2018 Maxime Cordy, Matthieu Jimenez, University of Luxembourg and Namur
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
