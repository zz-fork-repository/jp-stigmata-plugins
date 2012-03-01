package jp.sourceforge.stigmata.birthmarks.osb;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.spi.BirthmarkService;

/**
 * 
 * @author Fumiya Iwama
 * @author Ryouta Obatake
 * @author Akinori Kataoka
 * @author Takayuki Kitano
 */
public class OperandStackBehaviorsBirthmarkContainmentCalculator extends OperandStackBehaviorsBirthmarkComparator{
    public OperandStackBehaviorsBirthmarkContainmentCalculator(BirthmarkService spi){
        super(spi);
    }

    @Override
    public double compare(Birthmark b1, Birthmark b2, BirthmarkContext context){
        BirthmarkElement[] elementsA = b1.getElements();
        BirthmarkElement[] elementsB = b2.getElements();
        
        // 片方でも0だったら、return0
        if(elementsA.length != 0 && elementsB.length != 0){ 
            double[][] sim = createMatrix(elementsA, elementsB);
            // matchには、類似度の合計値が最大となる値が入る
            double match = calculateMatch(sim);
            // Similarityを返す
            return match / elementsA.length;
        }
        else{
            return 0;
        }
        
    }
}