package jp.sourceforge.stigmata.birthmarks.osb;

import java.io.IOException;
import java.net.URL;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.BirthmarkExtractionFailedException;
import jp.sourceforge.stigmata.ExtractionUnit;
import jp.sourceforge.stigmata.cflib.Opcode;
import jp.sourceforge.stigmata.cflib.OpcodeManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperandStackBehaviorsBirthmarkExtractorTest{
    private OperandStackBehaviorsBirthmarkExtractor extractor;

    @Before
    public void setUp(){
        OperandStackBehaviorsBirthmarkService service = new OperandStackBehaviorsBirthmarkService();
        extractor = (OperandStackBehaviorsBirthmarkExtractor)service.getExtractor();
    }

    @Test
    public void testBasic(){
        OperandStackBehaviorsBirthmarkElement element = 
            (OperandStackBehaviorsBirthmarkElement)extractor.buildElement("27, 28, 96, 62");

        Assert.assertArrayEquals(
            new ExtractionUnit[] { ExtractionUnit.CLASS, },
            extractor.getAcceptableUnits()
        );

        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(0));
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(1));
        Assert.assertEquals(Opcode.Category.ADD,   element.getCategory(2));
        Assert.assertEquals(Opcode.Category.STORE, element.getCategory(3));

        OpcodeManager manager = OpcodeManager.getInstance();
        Assert.assertEquals(manager.getOpcode(27), element.getOpcode(0));
        Assert.assertEquals(manager.getOpcode(28), element.getOpcode(1));
        Assert.assertEquals(manager.getOpcode(96), element.getOpcode(2));
        Assert.assertEquals(manager.getOpcode(62), element.getOpcode(3));
    }

    @Test
    public void testExtract1() throws BirthmarkExtractionFailedException, IOException{
        URL url = getClass().getResource("/resources/Sample1.class");
        Birthmark birthmark = extractor.extract(url.openStream());

        Assert.assertEquals("osb", birthmark.getType());
        Assert.assertEquals(7, birthmark.getElementCount());

        BirthmarkElement[] elements = birthmark.getElements();
        OperandStackBehaviorsBirthmarkElement element = (OperandStackBehaviorsBirthmarkElement)elements[0];
        Assert.assertEquals(2, element.getLength());
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(0));  // aload_0
        Assert.assertEquals(Opcode.Category.INVOKE, element.getCategory(1)); // invokespecial

        element = (OperandStackBehaviorsBirthmarkElement)elements[1];
        Assert.assertEquals(1, element.getLength());
        Assert.assertEquals(Opcode.Category.RETURN, element.getCategory(0)); // return

        element = (OperandStackBehaviorsBirthmarkElement)elements[2];
        Assert.assertEquals(2, element.getLength());
        Assert.assertEquals(Opcode.Category.CONSTANT, element.getCategory(0)); // iconst_2
        Assert.assertEquals(Opcode.Category.STORE, element.getCategory(1));    // istore_1

        element = (OperandStackBehaviorsBirthmarkElement)elements[3];
        Assert.assertEquals(2, element.getLength());
        Assert.assertEquals(Opcode.Category.CONSTANT, element.getCategory(0)); // iconst_4
        Assert.assertEquals(Opcode.Category.STORE,    element.getCategory(1)); // istore_2

        element = (OperandStackBehaviorsBirthmarkElement)elements[4];
        Assert.assertEquals(4, element.getLength());
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(0)); // iconst_4
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(1)); // iconst_4
        Assert.assertEquals(Opcode.Category.ADD,   element.getCategory(2)); // iadd
        Assert.assertEquals(Opcode.Category.STORE, element.getCategory(3)); // istore_3

        element = (OperandStackBehaviorsBirthmarkElement)elements[5];
        Assert.assertEquals(3, element.getLength());
        Assert.assertEquals(Opcode.Category.FIELD,  element.getCategory(0)); // getstatic
        Assert.assertEquals(Opcode.Category.LOAD,   element.getCategory(1)); // iload_3
        Assert.assertEquals(Opcode.Category.INVOKE, element.getCategory(2)); // invokevirtual

        element = (OperandStackBehaviorsBirthmarkElement)elements[6];
        Assert.assertEquals(1, element.getLength());
        Assert.assertEquals(Opcode.Category.RETURN, element.getCategory(0)); // return
    }

    @Test
    public void testExtract2() throws BirthmarkExtractionFailedException, IOException{
        URL url = getClass().getResource("/resources/Sample2.class");
        Birthmark birthmark = extractor.extract(url.openStream());

        Assert.assertEquals("osb", birthmark.getType());
        Assert.assertEquals(8, birthmark.getElementCount());

        BirthmarkElement[] elements = birthmark.getElements();
        OperandStackBehaviorsBirthmarkElement element = (OperandStackBehaviorsBirthmarkElement)elements[0];
        Assert.assertEquals(2, element.getLength());
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(0));  // aload_0
        Assert.assertEquals(Opcode.Category.INVOKE, element.getCategory(1)); // invokespecial

        element = (OperandStackBehaviorsBirthmarkElement)elements[1];
        Assert.assertEquals(1, element.getLength());
        Assert.assertEquals(Opcode.Category.RETURN, element.getCategory(0)); // return

        element = (OperandStackBehaviorsBirthmarkElement)elements[2];
        Assert.assertEquals(2, element.getLength());
        Assert.assertEquals(Opcode.Category.CONSTANT, element.getCategory(0)); // iconst_2
        Assert.assertEquals(Opcode.Category.STORE, element.getCategory(1));    // istore_1

        element = (OperandStackBehaviorsBirthmarkElement)elements[3];
        Assert.assertEquals(2, element.getLength());
        Assert.assertEquals(Opcode.Category.CONSTANT, element.getCategory(0)); // iconst_4
        Assert.assertEquals(Opcode.Category.STORE,    element.getCategory(1)); // istore_2

        element = (OperandStackBehaviorsBirthmarkElement)elements[4];
        Assert.assertEquals(4, element.getLength());
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(0)); // iconst_4
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(1)); // iconst_4
        Assert.assertEquals(Opcode.Category.ADD,   element.getCategory(2)); // iadd
        Assert.assertEquals(Opcode.Category.STORE, element.getCategory(3)); // istore_3

        element = (OperandStackBehaviorsBirthmarkElement)elements[5];
        Assert.assertEquals(6, element.getLength());
        Assert.assertEquals(Opcode.Category.LOAD,     element.getCategory(0));
        Assert.assertEquals(Opcode.Category.LOAD,     element.getCategory(1));
        Assert.assertEquals(Opcode.Category.MULTIPLY, element.getCategory(2));
        Assert.assertEquals(Opcode.Category.LOAD,     element.getCategory(3));
        Assert.assertEquals(Opcode.Category.SUBTRACT, element.getCategory(4));
        Assert.assertEquals(Opcode.Category.STORE,    element.getCategory(5));

        element = (OperandStackBehaviorsBirthmarkElement)elements[6];
        Assert.assertEquals(3, element.getLength());
        Assert.assertEquals(Opcode.Category.FIELD,  element.getCategory(0)); // getstatic
        Assert.assertEquals(Opcode.Category.LOAD,   element.getCategory(1)); // iload_3
        Assert.assertEquals(Opcode.Category.INVOKE, element.getCategory(2)); // invokevirtual

        element = (OperandStackBehaviorsBirthmarkElement)elements[7];
        Assert.assertEquals(1, element.getLength());
        Assert.assertEquals(Opcode.Category.RETURN, element.getCategory(0)); // return
    }
}
