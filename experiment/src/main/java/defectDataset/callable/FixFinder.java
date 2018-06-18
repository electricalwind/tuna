package defectDataset.callable;

import defectDataset.model.Bug;
import defectDataset.model.FileInterest;
import defectDataset.model.Fix;
import gitUtilitaries.GitActions;

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
     * @param bug to analyse
     * @param gitUtilitary git utilitary class directly linked to the .git
     */
    public FixFinder(Bug bug, GitActions gitUtilitary) {
        this.bug = bug;
        this.gitUtilitary = gitUtilitary;
        this.extension = ".*";
    }

    /**
     * Constructor
     * @param bug to analyse
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
