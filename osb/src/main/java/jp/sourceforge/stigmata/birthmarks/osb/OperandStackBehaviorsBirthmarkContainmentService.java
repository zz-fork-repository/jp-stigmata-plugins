package jp.sourceforge.stigmata.birthmarks.osb;

import jp.sourceforge.stigmata.BirthmarkComparator;
import jp.sourceforge.stigmata.BirthmarkExtractor;
import jp.sourceforge.stigmata.BirthmarkPreprocessor;
import jp.sourceforge.stigmata.spi.BirthmarkService;

/**
 * Operand Stack Behaviors birthmark.
 *
 * @author Fumiya Iwama
 * @author Ryouta Obatake
 * @author Akinori Kataoka
 * @author Takayuki Kitano
 */
public class OperandStackBehaviorsBirthmarkContainmentService implements BirthmarkService {
    private BirthmarkExtractor extractor =
        new OperandStackBehaviorsBirthmarkExtractor(this);
    private BirthmarkComparator comparator =
        new OperandStackBehaviorsBirthmarkContainmentCalculator(this);

    @Override
    public String getDescription(){
        return "Operand stack behaviors birthmark (containment)";
    }

    @Override
    public boolean isUserDefined(){
        return false;
    }
	@Override
    public boolean isExperimental(){
        return false;
    }

    @Override
    public String getType(){
        return "osb_containment";
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
        return null;
    }
}
