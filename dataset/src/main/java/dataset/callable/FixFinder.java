package dataset.callable;

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

import dataset.model.Bug;
import dataset.model.FileInterest;
import dataset.model.Fix;
import gitutils.gitUtilitaries.GitActions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * A class implementing callable that retrieve the files modified to patch a bug in their state before and after patching
 */
public class FixFinder implements Callable<Bug> {

    private final String extension;
    private Bug bug;
    private GitActions gitUtilitary;

    /**
     * Constructor
     * @param bug to csvExporter
     * @param gitUtilitary git utilitary class directly linked to the .git
     */
    public FixFinder(Bug bug, GitActions gitUtilitary) {
        this.bug = bug;
        this.gitUtilitary = gitUtilitary;
        this.extension = ".*";
    }

    /**
     * Constructor
     * @param bug to csvExporter
     * @param gitUtilitary git utilitary class directly linked to the .git
     * @param pattern pattern of file,i.e., extension
     */
    public FixFinder(Bug bug, GitActions gitUtilitary,String pattern) {
        this.bug = bug;
        this.gitUtilitary = gitUtilitary;
        this.extension = pattern;
    }

    /**
     * @return the same bug as received as input but with it patch parameter filled
     */
    @Override
    public Bug call() throws IOException {
        List<String> listOfModifiedFile = gitUtilitary.getListOfModifiedFile(bug.getHash(), extension);
        List<Fix> fixes = new LinkedList<>();
        for (String file : listOfModifiedFile) {
            GitActions.NamedCommit previousCommit = gitUtilitary.previousCommitImpactingAFile(file, bug.getHash());
            String oldname = previousCommit.getFilePath();
            String oldHash = previousCommit.getRevCommit().getName();
            String oldContent = gitUtilitary.retrievingFileFromSpecificCommit(oldHash, oldname);
            String newContent = gitUtilitary.retrievingFileFromSpecificCommit(bug.getHash(), file);
            if (oldContent != null && newContent != null) {
                FileInterest fib = new FileInterest(oldContent, oldname);
                FileInterest fia = new FileInterest(newContent, file);
                fixes.add(new Fix(fib, fia, oldHash));
            }
        }
        bug.getFixes().addAll(fixes);
        return bug;
    }
}
