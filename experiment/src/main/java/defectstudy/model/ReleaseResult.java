package defectstudy.model;

import java.io.Serializable;

/**
 *
 */
public class ReleaseResult implements Serializable {
    private static final long serialVersionUID = 20180618L;
    private String tokenizer;
    private String entropy;

    public ReleaseResult(String tokenizer, String entropy) {
        this.tokenizer = tokenizer;
        this.entropy = entropy;
    }

    public String getEntropy() {
        return entropy;
    }

    public String getTokenizer() {
        return tokenizer;
    }
}
