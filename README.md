# Tuna Project

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

![version](https://img.shields.io/badge/tuna-1.0-green.svg)

## ProjectMap

* [GitUtils](docs/gitUtils.md)
* [Tokenizer](docs/tokenizer.md)
* [Language Modelling](docs/languagemodelling.md)
* [Defect Dataset](docs/dataset.md)
* [Experiment](docs/experiment.md)

## Introduction

The most common types of language model are the n-gram models. They consist of a set of conditional probabilities of the form P(tk|tk−n+1 ...tk−1), each of which records the probability that some token tk follows a preceding sequence tk−n+1 . . . tk−1. 
In the case of source code analysis, tokens are fragments of a given representation of the code, such as a lexical token or a node of the code’s abstract syntax tree. To build an n-gram model for a given source code, one must (1) tokenize this code to obtain sequences of tokens; (2) parameterize the model (e.g. set the size n of the n-grams, specify the unknown threshold, choose a smoothing method); (3) train the model, that is, make it compute the conditional probabilities based on what it observes in the source code. Then, the model can be used to compute the naturalness of some new code, typically as a cross-entropy value obtained from the computed probabilities. A lower cross-entropy means that the new code is more natural, and thus more similar to the code used to train the model. By computing naturalness values, one can then perform valuable source code analyses.
However, in our related ICSME ’18 paper, we have shown that the conclusions of a study can drastically change with respect to how the code is tokenized and how the used n-gram model is parameterized. These choices are thus of utmost importance, and one must carefully make them. To show this and allow the community to benefit from our work, we have developed TUNA (TUning Naturalness-based Analysis), a Java software artifact to perform naturalness-based analyses of source code. More precisely, TUNA provides multiple functionalities through the interaction of dedicated modules.

## Presentation

First, TUNA’s module can retrieve Java source code contained in a public GitHub repository. As such, the module
[tuna-gitUtils](gitUtils.md) includes a class GitClonePull, where one can specify a source repository and a destination folder before cloning or updating this repository.

Second, TUNA can tokenize Java source code based on multiple representations (e.g., as UTF8 tokens, as programming language grammar’s lexical units, as sequences of nodes of the abstract syntax tree visited in depth-first or breadth-first order). To achieve this, the module [tuna-tokenizer](tokenizer.md) provide a factory named JavaFileTokenizerFactory, which provides methods to instantiate any tokenizer mentioned in our work.
 
Third, one can parameterize n-gram models, train them based on tokenized source code, and compute the cross-entropy of one or more source files. To this end, the module [tuna-modelling](languagemodelling.md) provides an interface NgramModel and an implementation of it, based on Kylm and named NgramModelKylmImpl. Following the interface segregation principle, alternative implementations can easily be added in the future.


Finally, the module [tuna-experiment](experiment.md) joint with the [tuna-dataset](dataset.md) contains the code needed to replicate our experiments.

To the best of our knowledge, TUNA is the first open-source, end-to-end toolchain to carry out source code analyses based on naturalness. We continue to make it evolve as we perform additional studies.