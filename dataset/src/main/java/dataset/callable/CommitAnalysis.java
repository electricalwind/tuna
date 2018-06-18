package dataset.callable;

import dataset.model.Bug;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class implementing callable that analyse the commit message to find mention to the bug issue
 */
public class CommitAnalysis implements Callable<Bug> {
    private final Pattern regexp;
    private final RevCommit commit;

    /**
     * Constructor
     * @param next commit to analyse
     * @param abreviation software abreviation on the bug tracker
     */
    public CommitAnalysis(RevCommit next, String abreviation) {
        this.commit = next;
        this.regexp = Pattern.compile(".*(" + abreviation + "-[0-9]+)");
    }

    /**
     *
     * @return a bug object if the commit match null otherwise
     * */
    @Override
    public Bug call(){
        String fm = commit.getFullMessage();
        Matcher matcher = regexp.matcher(fm);
        if (matcher.find() && !fm.contains("Merge") && !fm.contains("Revert")) {
            String idBug = matcher.group(1);
            return new Bug(idBug, commit.getName(), commit.getFullMessage(), commit.getCommitTime(),new ArrayList<>() ,new ArrayList<>());
        }
        return null;
    }
}
