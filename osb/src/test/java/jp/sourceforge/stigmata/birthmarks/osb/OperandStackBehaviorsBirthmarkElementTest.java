package jp.sourceforge.stigmata.birthmarks.osb;

import java.util.Iterator;

import jp.sourceforge.stigmata.cflib.Opcode;
import jp.sourceforge.stigmata.cflib.OpcodeManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperandStackBehaviorsBirthmarkElementTest{
    private OperandStackBehaviorsBirthmarkElement element;

    @Before
    public void setUp(){
        OpcodeManager manager = OpcodeManager.getInstance();
        Opcode[] opcodes = new Opcode[4];
        opcodes[0] = manager.getOpcode(27); // iload_1
        opcodes[1] = manager.getOpcode(28); // iload_2
        opcodes[2] = manager.getOpcode(96); // iadd
        opcodes[3] = manager.getOpcode(62); // istore_3

        element = new OperandStackBehaviorsBirthmarkElement(opcodes);
    }

    @Test
    public void testSimilarity(){
        OpcodeManager manager = OpcodeManager.getInstance();
        OperandStackBehaviorsBirthmarkElement element2 = new OperandStackBehaviorsBirthmarkElement(
            new Opcode[] {
                manager.getOpcode(27),  // iload_1
                manager.getOpcode(28),  // iload_2
                manager.getOpcode(104), // imul
                manager.getOpcode(29),  // iload_3
                manager.getOpcode(100), // isub
                manager.getOpcode(54),  // istore
            }
        );

        Assert.assertEquals(3d/7d, element.getSimilarity(element2), 1E-5);
        Assert.assertEquals(3d/7d, element2.getSimilarity(element), 1E-5);
    }

    @Test
    public void testBasic(){
        Assert.assertEquals(4, element.getLength());
        Assert.assertEquals("27, 28, 96, 62", element.toString());
    }

    @Test
    public void testCategoryCheck(){
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(0));
        Assert.assertEquals(Opcode.Category.LOAD,  element.getCategory(1));
        Assert.assertEquals(Opcode.Category.ADD,   element.getCategory(2));
        Assert.assertEquals(Opcode.Category.STORE, element.getCategory(3));
    }

    @Test
    public void testOpcodeCheck(){
        OpcodeManager manager = OpcodeManager.getInstance();
        Assert.assertEquals(manager.getOpcode(27), element.getOpcode(0));
        Assert.assertEquals(manager.getOpcode(28), element.getOpcode(1));
        Assert.assertEquals(manager.getOpcode(96), element.getOpcode(2));
        Assert.assertEquals(manager.getOpcode(62), element.getOpcode(3));
    }

    @Test
    public void testOpcodeCheck2(){
        OpcodeManager manager = OpcodeManager.getInstance();
        Iterator<Opcode> iterator = element.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(manager.getOpcode(27), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(manager.getOpcode(28), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(manager.getOpcode(96), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(manager.getOpcode(62), iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }
}
