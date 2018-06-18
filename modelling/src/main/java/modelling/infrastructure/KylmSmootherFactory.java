package modelling.infrastructure;

import modelling.infrastructure.kylm.ngram.smoother.AbsoluteSmoother;
import modelling.infrastructure.kylm.ngram.smoother.GTSmoother;
import modelling.infrastructure.kylm.ngram.smoother.KNSmoother;
import modelling.infrastructure.kylm.ngram.smoother.MKNSmoother;
import modelling.infrastructure.kylm.ngram.smoother.MLSmoother;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;
import modelling.infrastructure.kylm.ngram.smoother.WBSmoother;

public class KylmSmootherFactory {

    public static final NgramSmoother absoluteDiscounting() {
        return new AbsoluteSmoother();
    }
    
    public static final NgramSmoother goodTuring() {
        return new GTSmoother();
    }
    
    public static final NgramSmoother kneserNey() {
        return new KNSmoother();
    } 
    
    public static final NgramSmoother maximumLikelihood() {
        return new MLSmoother();
    }
    
    public static final NgramSmoother modifiedKneserNey() {
        return new MKNSmoother();
    }
    
    public static final NgramSmoother wittenBell() {
        return new WBSmoother();
    }
}
