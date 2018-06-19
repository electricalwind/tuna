# Experiment

The experiment package provide the code for the two experiments presented in the paper "On the Impact of Tokenizer and Parameters on N-Gram Based Code Analysis" published at ICSME'18. The first one look at the effect of the different parameters of a NGram Model (n, threshold,smoother, tokenizer) on the cross entropy returned by the model, while the second one studie the impact of the choice of the tokenizer for studies on naturalness using a [dataset of defect](dataset.md). More information about the related research questions can be found in the paper.

## Requirements

There is no requirement in terms of software for this tool, as maven will take care of all dependencies. However, the experiments are heavy on memory (>16GB of RAM) and might require to be run on a server.

## Parameter study

This study investigate the effect of the different parameters of a NGram Model (n, threshold,smoother, tokenizer) on the cross entropy returned by the model. 

### Studied Parameter

* n : 2, 3, 4, 5, 6, 7
* threshold: 1, 2, 4, 8, 16
* smoother : absoluteDiscounting, kneserNey, modified Kneser Ney, witten Bell
* tokenizer : utfTokenizer, utfWocTokenizer, lemmeTokenizer, lemmeWocTokenizer, depthFirstTokenizer, breadthFirstTokenizer, depthFirstPrunedTokenizer, breadthFirstPrunedTokenizer

### Projects

All projects present in the 

## Defect study

