package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import jp.sourceforge.stigmata.plugins.Opcode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class CurrentDepthTest{
    private CurrentDepth depth;
    private Opcode opcode;

    @Before
    public void setup(){
        opcode = new Opcode(4, "iconst_1", 0, 1, Opcode.Category.NORMAL);
        depth = new CurrentDepth(3, opcode);
    }

    @Test
    public void testBasic(){
        Assert.assertEquals(3, depth.getDepth());
        Assert.assertEquals(opcode, depth.getOpcode());
    }
}
