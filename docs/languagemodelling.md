# The Language Modelling package

The Language Modelling package offer a functional way to parameterize n-gram models, train them based on tokenized text, and compute the cross-entropy of one or more files

## What is it?

The Language Modelling package provide an interface to train and build Ngram model and compute the cross entropy of some text according to them. It currently offers an implementation of this interface based on [KYLM](https://github.com/neubig/kylm). Hence it offers all parameters integrated in kylm, e.g., n, smoothing techniques, unknown threshold...


## Requirements

No dependencies are required to use this package.

## Architecture

The package can be splitted in two, first is the interface NGramModel which contains everything required to build and use a NgramModel, then the implementation of this interface which in our current situation is based on Kylm, but following the interface segregation principle, alternative implementations can easily be added in the future.


### Interface

The NgramModel interface only contains 3 methods, one to train the model according to a corpus presented under the form of Iterable<Iterable<String>> object, and two to compute cross entropy, the first one targeting a single document the other one several documents hence the need to average.

````java
package modelling;

import modelling.exception.TrainingFailedException;

import java.io.Serializable;

/**
 * NaturalnessModel is a mutable representation of the naturalness of programs.
 *
 * @specfield size : the size of the n-grams
 * @specfield trained : boolean // true iff this has been trained at least once
 */
public interface NgramModel extends Serializable {

    /**
     * @throws TrainingFailedException when 'this' cannot be trained on
     *                                 'documents'
     * @requires documents not null
     * documents does not contain null
     * no element of documents contain null
     * @modifies this
     * @effects Train 'this' based on documents
     */
    void train(Iterable<Iterable<String>> documents)throws TrainingFailedException;

    /**
     * @return The cross-entropy of document according to this
     * @requires document not null
     * document does not contain null
     * this.trained
     */
    double crossEntropy(Iterable<String> document);


    double averageCrossEntropy(Iterable<Iterable<String>> documents);
}
````

### Kylm

Our current implementation of the interface relies on KYLM (Kyoto Langugage Modeling) which was developed by Graham Neubig and can be found in the class NgramModelKylmImpl in the infrastructure subpackage.

````java
package modelling.infrastructure;


/**
 * Implementation of NaturalnessModel based on the Kyoto Language Model
 */
public class NgramModelKylmImpl implements NgramModel {

    private final int size;
    private final NgramLM model;
    private boolean trained;

    /**
     * @requires size > 1 && smoother not null
     * @effects Make this be a new NgramModel with this.size = size and
     * trained = false.
     */
    public NgramModelKylmImpl(int size, NgramSmoother smoother);

    /**
     * @requires size > 1 && smoother not null
     * @effects Make this be a new NgramModel with this.size = size and
     * trained = false.
     */
    public NgramModelKylmImpl(int size, NgramSmoother smoother, int threshold);
}
````

To construct a NgramModelKylmImpl object, two constructors are available, one require a size (n) and a smoother, the other requiring in addition a threshold. Note that in the case of the first constructor, the threshold is put to 1.
Regarding the smoother, kylm provides different smoothing techniques:

* absolute discounting
* good turing
* kneser ney
* maximum likelihood
* modified kneser ney
* witten Bell

All of this smoother can be access through the Factory KylmSmootherFactory to be used straight in the constructor.


## How to use the tool

````java
NgramModel model = new NgramModelKylmImpl(
                2, KylmSmootherFactory.maximumLikelihood());
model.train(myCorpus);
model.crossEntropy(aDocument);
````

## Third Party tool

* Kylm


