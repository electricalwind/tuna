package defectstudy.model;

import java.io.Serializable;

/**
 *
 */
public class BugResult implements Serializable{
    private String berelease;
    private String buggy;
    private String fixed;
    private String afrelease;
    private String releaseAffected;

    public BugResult() {
    }

    public String getBerelease() {
        return berelease;
    }

    public void setBerelease(String berelease) {
        this.berelease = berelease;
    }

    public String getBuggy() {
        return buggy;
    }

    public void setBuggy(String buggy) {
        this.buggy = buggy;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getAfrelease() {
        return afrelease;
    }

    public void setAfrelease(String afrelease) {
        this.afrelease = afrelease;
    }

    public String getReleaseAffected() {
        return releaseAffected;
    }

    public void setReleaseAffected(String releaseAffected) {
        this.releaseAffected = releaseAffected;
    }
}
