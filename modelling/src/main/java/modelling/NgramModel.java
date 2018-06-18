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
