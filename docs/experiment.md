# Experiment

The experiment package provide the code for the two experiments presented in the paper "On the Impact of Tokenizer and Parameters on N-Gram Based Code Analysis" published at ICSME'18. The first one look at the effect of the different parameters of a NGram Model (n, threshold,smoother, tokenizer) on the cross entropy returned by the model, while the second one study the impact of the choice of the tokenizer for studies on naturalness using a [dataset of defect](dataset.md). More information about the related research questions can be found in the paper.

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

All projects in their latest version present in the defect dataset are used in this experiment.

### Experimental Process

1. All project are first either cloned or pulled
2. For each project in their current state the list of all Java files is retrieved
3. For each tokenizer:
    1. Parse all files
    2. for each project:
        * run 10 fold cross validation of cross entropy for all parameters 
        * compute the cross entropy with all other project
        

### Launching 

The experiment can be launched like this:
````java
import parameters.Application;

Application app = new Application("path To Save");
app.run();
````

This will output corresponding csv with the following titles:

* Tokenizer_Project.csv
* Tokenizer_Project_to_Project.csv

under this format:

    "Smoother", "N-Gram Size", "Threshold", "Cross-entropy"

Note that it is possible to merge all Tokenizer result for a project using:

````java
ResultToCSV.produceGenericCSV(pathSaved+"/"+project, pathToSave);
````


## Defect study


This study look into the effect of the choice of the tokenizer on naturalness studies.In particular, it investigate the case of defect prediction. 

### Studied Parameter

* n : 4
* threshold: 1, 8
* smoother : modified Kneser Ney
* tokenizer : utfTokenizer, utfWocTokenizer, lemmeTokenizer, lemmeWocTokenizer, depthFirstTokenizer, breadthFirstTokenizer, depthFirstPrunedTokenizer, breadthFirstPrunedTokenizer

### Projects

### Experimental Process

        

### Output

### Launching 

The experiment can be launched like this:
````java
import parameters.Application;

Application app = new Application("path To Save");
app.run();
````

