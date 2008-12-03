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
public class StackPatternBasedBirthmarkComparatorTest{
    private StackPatternBasedBirthmarkComparator comparator;

    @Before
    public void setup(){
        StackPatternBasedBirthmarkService service = new StackPatternBasedBirthmarkService();
        comparator = new StackPatternBasedBirthmarkComparator(service);
    }

    @Test
    public void testCalculateWeightOfWcs(){
        int[][] wcs = new int[][] {
            {  6,  6,  9,  3, },
            { 21, 18, 18,  6, },
            {  9,  6, 12,  3, },
            {  6,  9, 30,  9, },
        };
        Assert.assertEquals(60, comparator.calculateWeight(wcs));
    }

    @Test
    public void testCalculateWeightOfWcs2(){
        int[][] wcs = new int[][] {
            { 16,  5,  0,  6, },
            {  3,  0, 15,  6, },
            {  2, 10,  0,  3, },
            {  0,  2,  3,  9, },
        };
        Assert.assertEquals(50, comparator.calculateWeight(wcs));
    }
}
