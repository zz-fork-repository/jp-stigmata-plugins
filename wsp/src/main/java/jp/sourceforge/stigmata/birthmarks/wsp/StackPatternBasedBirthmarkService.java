package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import jp.sourceforge.stigmata.BirthmarkComparator;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.BirthmarkExtractor;
import jp.sourceforge.stigmata.BirthmarkPreprocessor;
import jp.sourceforge.stigmata.birthmarks.AbstractBirthmarkService;

/**
 * Weighted Stack Pattern based birthmark.
 * 
 * this birthmark is proposed by LIM et al. in following papers.
 * <ul>
 * <li>Hyun-il Lim, Heewan Park, Seokwoo Choi, Taisook Han, ``Detecting Theft
 * of Java Applications via a Static Birthmark Based on Weighted Stack
 * Patterns,'' IEICE Transactions on Information and Systems, Vol.E91-D No.9
 * pp.2323-2332, September 2008.</li>
 * <li>Heewan Park, Hyun-il Lim, Seokwoo Choi and Taisook Han, ``A Static Java
 * Birthmark Based on Operand Stack Behaviors,'' In Proc. of 2008 International
 * Conference on Information Security and Assurance, pp.133-136, April 2008.</li>
 * </ul>
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class StackPatternBasedBirthmarkService extends AbstractBirthmarkService{
    private BirthmarkPreprocessor preprocessor = new OpcodeWeightCalculatePreprocessor(this);
    private BirthmarkExtractor extractor = new StackPatternBasedBirthmarkExtractor(this);
    private BirthmarkComparator comparator = new StackPatternBasedBirthmarkComparator(this);

    @Override
    public String getDefaultDescription(){
        return "Weighted stack pattern based birthmark";
    }

    @Override
    public String getType(){
        return "wsp";
    }

    @Override
    public BirthmarkComparator getComparator(){
        return comparator;
    }

    @Override
    public BirthmarkExtractor getExtractor(){
        return extractor;
    }

    @Override
    public BirthmarkPreprocessor getPreprocessor(){
        return preprocessor;
    }

    @Override
    public BirthmarkElement buildBirthmarkElement(String value){
        return new StackPatternBasedBirthmarkElement(value);
    }
}
