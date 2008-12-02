package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
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
        // TODO implement this method.
        return 0;
    }

}
