package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.birthmarks.comparators.AbstractBirthmarkComparator;
import jp.sourceforge.stigmata.spi.BirthmarkSpi;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class StackPatternBasedBirthmarkComparator extends AbstractBirthmarkComparator{
    public StackPatternBasedBirthmarkComparator(BirthmarkSpi spi){
        super(spi);
    }

    @Override
    public double compare(Birthmark b1, Birthmark b2, BirthmarkContext context){
        int[][] wcs = createMatrix(b1, b2);
        int weightOfWcs = calculateWeight(wcs);
        int weightOfBirthmark1 = 0;
        for(BirthmarkElement element: b1){
            weightOfBirthmark1 += ((StackPatternBasedBirthmarkElement)element).getWeight();
        }

        return (double)weightOfWcs / (double)weightOfBirthmark1;
    }

    int calculateWeight(int[][] wcs){
        int weight = 0;
        boolean[][] availableFlag = new boolean[wcs.length][wcs[0].length];
        for(int i = 0; i < wcs.length; i++){
            for(int j = 0; j < wcs[i].length; j++){
                availableFlag[i][j] = true;
            }
        }

        int length = wcs.length;
        if(length < wcs[0].length){
            length = wcs[0].length;
        }
        for(int k = 0; k < length; k++){
            int max = Integer.MIN_VALUE;
            int column = -1;
            int row = -1;
            for(int i = 0; i < wcs.length; i++){
                for(int j = 0; j < wcs[i].length; j++){
                    if(max < wcs[i][j] && availableFlag[i][j]){
                        max = wcs[i][j];
                        row = i;
                        column = j;
                    }
                }
            }
            if(column >= 0 && row >= 0){
                for(int i = 0; i < wcs.length; i++){
                    availableFlag[i][column] = false;
                }
                for(int j = 0; j < wcs[0].length; j++){
                    availableFlag[row][j] = false;
                }
                weight += wcs[column][row];
            }
        }

        return weight;
    }

    private int[][] createMatrix(Birthmark b1, Birthmark b2){
        BirthmarkElement[] elementsA = b1.getElements();
        BirthmarkElement[] elementsB = b2.getElements();

        int[][] matrix = new int[elementsA.length][elementsB.length];
        for(int i = 0; i < elementsA.length; i++){
            for(int j = 0; j < elementsB.length; j++){
                StackPatternBasedBirthmarkElement wsp1 = (StackPatternBasedBirthmarkElement)elementsA[i];
                StackPatternBasedBirthmarkElement wsp2 = (StackPatternBasedBirthmarkElement)elementsB[j];
                matrix[i][j] = wsp1.getPattern().calculateWeight(wsp2.getPattern());
            }
        }
        return matrix;
    }
}
