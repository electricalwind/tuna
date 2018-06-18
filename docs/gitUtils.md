# Git Utilitaries

This library coded in Kotlin provide some useful function when trying to mine a git repository.
First, it allos to clone or pull a remote repository, then it offers the following methods:

* retrievingFileFromSpecificCommit
* listOfCommitImpactingAFile
* previousCommitImpactingAFile
* gitBlame
* gitBlameNbDev
* getDevHistoryOfAFile (use Developer History class)
* getDeltaFile (Delta History)
* getListOfModifiedFile
* getCommitMessage
* getTimeCommit

this library work on local repository but also offer the possibility to clone a remote repository: 

```kotlin
   // for already existing git repo  
     val git = GitUtilitary("pathto .git folder")
   // to clone the repo
     val git = GitUtilitary("remote repo adress","path to folder")
```