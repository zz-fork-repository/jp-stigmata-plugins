package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Label;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class LabelOpcodeTest{
    private LabelOpcode opcode;
    private Label label;

    @Before
    public void setup(){
        label = new Label();
        opcode = new LabelOpcode(label);
    }

    @Test
    public void testBasic(){
        Assert.assertEquals(Opcode.Category.TARGETER, opcode.getCategory());
        Assert.assertEquals(-1, opcode.getOpcode());
        Assert.assertEquals("targeter", opcode.getName());
        Assert.assertEquals(label, opcode.getLabel());
    }
}
