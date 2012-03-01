package jp.sourceforge.stigmata.birthmarks.osb;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.birthmarks.comparators.AbstractBirthmarkComparator;
import jp.sourceforge.stigmata.birthmarks.osb.hungarian.CostMatrix;
import jp.sourceforge.stigmata.birthmarks.osb.hungarian.HungarianMethod;
import jp.sourceforge.stigmata.spi.BirthmarkService;

/**
 * 
 * @author Fumiya Iwama
 * @author Ryouta Obatake
 * @author Akinori Kataoka
 * @author Takayuki Kitano
 */
public class OperandStackBehaviorsBirthmarkComparator extends AbstractBirthmarkComparator{
		public OperandStackBehaviorsBirthmarkComparator(BirthmarkService spi){
			super(spi);
		}		

    @Override
    public double compare(Birthmark b1, Birthmark b2, BirthmarkContext context){
        BirthmarkElement[] elementsA = b1.getElements();
        BirthmarkElement[] elementsB = b2.getElements();

        //両方0だったら(片方でも0だったら、return0)
        if(elementsA.length != 0 && elementsA.length != 0){ 
            double[][] sim = createMatrix(elementsA,elementsB);
            // matchには、類似度の合計値が最大となる値が入る
            double match = calculateMatch(sim); 
            // BehaviorSetのBehaviorの数のうち、大きい方を max に代入     
            int max = Math.max(elementsA.length, elementsB.length);
            
            return match / max;//Similarityを返す
        }
        else{
            return 0;
        }
    }

    public double compare(BirthmarkElement element1, BirthmarkElement element2){
        if(element1 instanceof OperandStackBehaviorsBirthmarkElement &&
                element2 instanceof OperandStackBehaviorsBirthmarkElement){
            return ((OperandStackBehaviorsBirthmarkElement)element1).getSimilarity(
                    (OperandStackBehaviorsBirthmarkElement)element2
            );
        }
        throw new IllegalArgumentException("only OperandStackBehaviorBirthmarkElement");
    }

    protected double[][] createMatrix(BirthmarkElement[] elementsA, BirthmarkElement[] elementsB){
        double[][] matrix = new double[elementsA.length][elementsB.length];//simは実数だからdouble
        for(int i = 0; i < elementsA.length; i++){
            for(int j = 0; j < elementsB.length; j++){
                OperandStackBehaviorsBirthmarkElement osb1 = (OperandStackBehaviorsBirthmarkElement)elementsA[i];
                OperandStackBehaviorsBirthmarkElement osb2 = (OperandStackBehaviorsBirthmarkElement)elementsB[j];
                matrix[i][j] = osb1.getSimilarity(osb2);
            }
        }
        return matrix;
    }

    /**
     * ハンガリアン法を使い，類似度を求める．
     * @param matrixArray Birthmarkの要素間の類似度をまとめた2次元配列．
     * @return 類似度の和が最大となるよう組み合わせた値．
     */
    protected double calculateMatch(double[][] matrixArray){
        CostMatrix matrix = new CostMatrix(matrixArray, true);
        HungarianMethod method = new HungarianMethod(matrix);

        return method.solve();
    }
}