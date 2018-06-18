package modelling.main.kylm;

import modelling.infrastructure.kylm.ngram.smoother.AbsoluteSmoother;
import modelling.infrastructure.kylm.ngram.smoother.GTSmoother;
import modelling.infrastructure.kylm.ngram.smoother.KNSmoother;
import modelling.infrastructure.kylm.ngram.smoother.MKNSmoother;
import modelling.infrastructure.kylm.ngram.smoother.MLSmoother;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;
import modelling.infrastructure.kylm.ngram.smoother.WBSmoother;

public class SmootherFactory {

    public static final NgramSmoother create(String smoother) {
        NgramSmoother ngramSmoother;
        switch (smoother) {
            case (AbsoluteSmoother.ABV):
                ngramSmoother = new AbsoluteSmoother();
                break;
            case (GTSmoother.ABV):
                ngramSmoother = new GTSmoother();
                break;
            case (MKNSmoother.ABV):
                ngramSmoother = new MKNSmoother();
                break;
            case (MLSmoother.ABV):
                ngramSmoother = new MLSmoother();
                break;
            case (WBSmoother.ABV):
                ngramSmoother = new WBSmoother();
                break;
            case (KNSmoother.ABV):
            default:
                ngramSmoother = new KNSmoother();
                break;
        }
        return ngramSmoother;
    }
}
