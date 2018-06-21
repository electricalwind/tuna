# Experiment

The experiment package provides the code for the two experiments presented in the paper "On the Impact of Tokenizer and Parameters on N-Gram Based Code Analysis" published at ICSME'18. The first one looks at the effect of the different parameters of an Ngram Model (n, threshold,smoother, tokenizer) on the cross entropy returned by the model, while the second one study the impact of the choice of the tokenizer for studies on naturalness using a [dataset of defects](dataset.md). More information about the related research questions can be found in the paper.

## Requirements

There is no requirement in terms of software for this tool, as maven will take care of all dependencies. However, the experiments are heavy on memory (>16GB of RAM) and might require to be run on a server.

## Parameter study

This study investigate the effect of the different parameters of a Ngram Model (n, threshold,smoother, tokenizer) on the cross entropy returned by the model. 

### Studied Parameter

* n : 2, 3, 4, 5, 6, 7
* threshold: 1, 2, 4, 8, 16
* smoother : absoluteDiscounting, kneserNey, modified Kneser Ney, Witten Bell
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


This study look into the effect of the choice of the tokenizer on naturalness studies.In particular, it investigates the case of defect prediction. 

### Studied Parameter

* n : 4
* threshold: 1, 8
* smoother : modified Kneser Ney
* tokenizer : utfTokenizer, utfWocTokenizer, lemmeTokenizer, lemmeWocTokenizer, depthFirstTokenizer, breadthFirstTokenizer, depthFirstPrunedTokenizer, breadthFirstPrunedTokenizer

### Projects

All the projects from the [dataset](dataset.md) are used. 

|Project                 | Latest         | Versions |
|:---------------------------:|:------------:|:---------:|
|BCEL                         | 6.1          | 5        | 
|BeansUtils                   | 1.9.3        |  18       | 
|CLI                          | 1.4          |  6        | 
|Collections                  | 4.1          | 12       | 
|Compress                     | 1.15         |  18       | 
|Configuration                | 2.2          |  15       | 
|CSV                          | 1.4          |  5        | 
|DBUtils                      | 1.7          | 8        | 
|EMail                        | 1.4          | 8        |
|FileUpload                   | 1.3.3        | 10       | 
|IO                           | 2.5          | 14       | 
|JCS                          | 2.2.1        |  6        | 
|Jexl                         | 3.1          | 8        | 
|Lang                         | 3.6          | 20       | 
|Math                         | 3.6.1        | 16       | 
|Net                          | 3.6          | 20       | 
|Pool                         | 2.4.2        | 22       | 
|Rng                          | 1.0          | 1        | 
|Text                         | 1.1          |  2        | 
|VFS                          | 2.2          |  4        | 
|Total                  | -         |  218     |

For each project, the commit corresponding to all major releases are retrieve for a total of 218 versions.

### Experimental Process

For each project:

1. generate or load the bug dataset
2. associate all bugs to a corresponding version
3. for each version V
    * for each Tokenizer T
        * parse all files of the version and if a file has bug or a fix for this version parse it
        * compute the cross entropy of all the parsed files

The cross entropy here use all the other files of the version to build a model and evaluate on the file.
        
### Output

The execution of this experiment result in the creation of two type of binary serialized file.

The first type correspond to the result per version and will be generated as project + "_" + release + ".obj"
This binary serialized file will contain a Map<ReleaseFile, List<ReleaseResult>.
Where ReleaseFile correspond to a file of the version and contains the following information :file, number of bugs, number of fixes, loc.
While the list of ReleaseResult correspond to the result of cross Entropy per tokenizer and contains the following information : tokenizer, entropy.

The second type correspond to the result per bug and tokenizer from the dataset and will be generated as project + "bugs.obj"
This binary serialized file will contain a Map<BugTokenFile, List<BugResult>.
Where BugTokenFile correspond to a file of the version and contains the following information : file,budID,tokenizerAbv
While the list of BugResult correspond to the result of cross Entropy in the previous release, before and after the fix and at the nex release and contains the following information : berelease, buggy, fixed, afrelease, releaseAffected.

### Launching 

The experiment can be launched like this:
````java
import parameters.Application;

Application app = new Application("path To Save");
app.run();
````
Then it is possible to export the serialized binary into csv files using the AnalyseBugs and Analyse Release class in the csvExporter subpackage.
