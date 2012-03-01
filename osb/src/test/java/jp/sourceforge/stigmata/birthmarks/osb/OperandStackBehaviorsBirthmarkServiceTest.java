package jp.sourceforge.stigmata.birthmarks.osb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperandStackBehaviorsBirthmarkServiceTest{
    private OperandStackBehaviorsBirthmarkService service;

    @Before
    public void setUp(){
        service = new OperandStackBehaviorsBirthmarkService();
    }

    @Test
    public void testBasic(){
        Assert.assertEquals("osb", service.getType());
        Assert.assertEquals("Operand stack behaviors birthmark", service.getDescription());
        Assert.assertNull(service.getPreprocessor());

        Assert.assertEquals(OperandStackBehaviorsBirthmarkExtractor.class, service.getExtractor().getClass());
        Assert.assertEquals(OperandStackBehaviorsBirthmarkComparator.class, service.getComparator().getClass());

        Assert.assertFalse(service.isExperimental());
        Assert.assertFalse(service.isUserDefined());
    }
}
