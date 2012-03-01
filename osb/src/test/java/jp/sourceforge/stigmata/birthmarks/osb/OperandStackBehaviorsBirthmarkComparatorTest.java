package jp.sourceforge.stigmata.birthmarks.osb;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkExtractionFailedException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class OperandStackBehaviorsBirthmarkComparatorTest{
    private OperandStackBehaviorsBirthmarkComparator comparator;
    private OperandStackBehaviorsBirthmarkExtractor extractor;

    @Before
    public void setUp(){
        OperandStackBehaviorsBirthmarkService service = new OperandStackBehaviorsBirthmarkService();
        comparator = (OperandStackBehaviorsBirthmarkComparator)service.getComparator();
        extractor = (OperandStackBehaviorsBirthmarkExtractor)service.getExtractor();
    }

    @Test
    public void testBasic() throws BirthmarkExtractionFailedException{
        Birthmark birthmark1 = extractor.extract(getClass().getResourceAsStream("/resources/Sample1.class"));
        Birthmark birthmark2 = extractor.extract(getClass().getResourceAsStream("/resources/Sample2.class"));

        Assert.assertEquals(7, birthmark1.getElementCount());
        Assert.assertEquals(8, birthmark2.getElementCount());

        Assert.assertEquals(7d / 8, comparator.compare(birthmark1, birthmark2, null), 1E-6);
    }
}
