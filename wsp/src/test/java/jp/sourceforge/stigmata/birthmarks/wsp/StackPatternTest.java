package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class StackPatternTest{
    private StackPattern pattern;

    @Before
    public void setup(){
        pattern = new StackPattern();

        pattern.update(new CurrentDepth(1, new Opcode( 26, "iload_0",      0,  1, Opcode.Category.NORMAL, 7)));
        pattern.update(new CurrentDepth(2, new Opcode( 26, "iload_0",      0,  1, Opcode.Category.NORMAL, 7)));
        pattern.update(new CurrentDepth(3, new Opcode(  4, "iconst_1",     0,  1, Opcode.Category.NORMAL, 1)));
        pattern.update(new CurrentDepth(2, new Opcode(100, "isub",         0, -1, Opcode.Category.NORMAL, 4)));
        pattern.update(new CurrentDepth(2, new Opcode(184, "invokestatic", 2,  0, Opcode.Category.INVOKE, 1)));
        pattern.update(new CurrentDepth(1, new Opcode(104, "imul",         0, -1, Opcode.Category.NORMAL, 6)));
        pattern.update(new CurrentDepth(0, new Opcode(172, "ireturn",      0, -1, Opcode.Category.NORMAL, 2)));
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals(7, pattern.getLength());
        Assert.assertEquals(28, pattern.getWeight());

        Assert.assertEquals(1, pattern.getDepth(0).getDepth());
        Assert.assertEquals(2, pattern.getDepth(1).getDepth());
        Assert.assertEquals(3, pattern.getDepth(2).getDepth());
        Assert.assertEquals(2, pattern.getDepth(3).getDepth());
        Assert.assertEquals(2, pattern.getDepth(4).getDepth());
        Assert.assertEquals(1, pattern.getDepth(5).getDepth());
        Assert.assertEquals(0, pattern.getDepth(6).getDepth());
    }

    @Test
    public void testCalculateWeightedCommonSubsequence(){
        StackPattern pattern2 = new StackPattern();
        
        pattern2.update(new CurrentDepth(1, new Opcode( 26, "iload_0",      0,  1, Opcode.Category.NORMAL, 7)));
        pattern2.update(new CurrentDepth(2, new Opcode(  4, "iconst_1",     0,  1, Opcode.Category.NORMAL, 1)));
        pattern2.update(new CurrentDepth(1, new Opcode(100, "isub",         0, -1, Opcode.Category.NORMAL, 4)));
        pattern2.update(new CurrentDepth(1, new Opcode(184, "invokestatic", 2,  0, Opcode.Category.INVOKE, 1)));
        pattern2.update(new CurrentDepth(2, new Opcode( 26, "iload_0",      0,  1, Opcode.Category.NORMAL, 7)));
        pattern2.update(new CurrentDepth(3, new Opcode(  5, "iconst_2",     0,  1, Opcode.Category.NORMAL, 1)));
        pattern2.update(new CurrentDepth(2, new Opcode(100, "isub",         0, -1, Opcode.Category.NORMAL, 4)));
        pattern2.update(new CurrentDepth(2, new Opcode(184, "invokestatic", 2,  0, Opcode.Category.INVOKE, 1)));
        pattern2.update(new CurrentDepth(1, new Opcode( 96, "iadd",         0, -1, Opcode.Category.NORMAL, 3)));
        pattern2.update(new CurrentDepth(0, new Opcode(172, "ireturn",      0, -1, Opcode.Category.NORMAL, 2)));

        Assert.assertEquals(21, pattern.calculateWeight(pattern2));
        Assert.assertEquals(21, pattern2.calculateWeight(pattern));
    }
}
