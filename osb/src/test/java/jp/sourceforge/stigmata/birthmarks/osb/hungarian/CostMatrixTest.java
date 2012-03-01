package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

import org.junit.Assert;
import org.junit.Test;

public class CostMatrixTest{
    @Test
    public void testUnequalsMatrix(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 1.0, 0.0, 0.0, },
            { 0.0, 0.8, 0.0, },
            { 0.0, 0.0, 0.6, },
            { 0.0, 0.2, 0.3, },
        });

        Assert.assertEquals(4, matrix.getSize());
        Assert.assertEquals(1.0, matrix.getValue(0, 0), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(0, 1), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(0, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(0, 3), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(1, 0), 1E-3);
        Assert.assertEquals(0.8, matrix.getValue(1, 1), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(1, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(1, 3), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(2, 0), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(2, 1), 1E-3);
        Assert.assertEquals(0.6, matrix.getValue(2, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(2, 3), 1E-3);
        Assert.assertEquals(0.0, matrix.getValue(3, 0), 1E-3);
        Assert.assertEquals(0.2, matrix.getValue(3, 1), 1E-3);
        Assert.assertEquals(0.3, matrix.getValue(3, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(3, 3), 1E-3);
    }

    @Test
    public void testUnequalsMatrixAndMax(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 1.0, 0.0, 0.0, },
            { 0.0, 0.8, 0.0, },
            { 0.0, 0.0, 0.6, },
            { 0.0, 0.2, 0.3, },
        }, true);

        Assert.assertEquals(4, matrix.getSize());
        Assert.assertEquals(0.0, matrix.getValue(0, 0), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(0, 1), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(0, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(0, 3), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(1, 0), 1E-3);
        Assert.assertEquals(0.2, matrix.getValue(1, 1), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(1, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(1, 3), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(2, 0), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(2, 1), 1E-3);
        Assert.assertEquals(0.4, matrix.getValue(2, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(2, 3), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(3, 0), 1E-3);
        Assert.assertEquals(0.8, matrix.getValue(3, 1), 1E-3);
        Assert.assertEquals(0.7, matrix.getValue(3, 2), 1E-3);
        Assert.assertEquals(1.0, matrix.getValue(3, 3), 1E-3);
    }

    @Test
    public void testMinimize(){
        CostMatrix matrix = new CostMatrix(new double[][]{
            { 0.15, 0.06, 0.09, 0.08, },
            { 0.03, 0.13, 0.07, 0.06, },
            { 0.09, 0.10, 0.05, 0.11, },
            { 0.03, 0.05, 0.07, 0.11, },
        });
        Assert.assertEquals(4, matrix.getSize());
        matrix.minimize();
        Assert.assertEquals(0.09, matrix.getValue(0, 0), 1E-6);
        Assert.assertEquals(0.0,  matrix.getValue(0, 1), 1E-6);
        Assert.assertEquals(0.03, matrix.getValue(0, 2), 1E-6);
        Assert.assertEquals(0.0,  matrix.getValue(0, 3), 1E-6);

        Assert.assertEquals(0.0,  matrix.getValue(1, 0), 1E-6);
        Assert.assertEquals(0.10, matrix.getValue(1, 1), 1E-6);
        Assert.assertEquals(0.04, matrix.getValue(1, 2), 1E-6);
        Assert.assertEquals(0.01, matrix.getValue(1, 3), 1E-6);

        Assert.assertEquals(0.04, matrix.getValue(2, 0), 1E-6);
        Assert.assertEquals(0.05, matrix.getValue(2, 1), 1E-6);
        Assert.assertEquals(0.00, matrix.getValue(2, 2), 1E-6);
        Assert.assertEquals(0.04, matrix.getValue(2, 3), 1E-6);

        Assert.assertEquals(0.0,  matrix.getValue(3, 0), 1E-6);
        Assert.assertEquals(0.02, matrix.getValue(3, 1), 1E-6);
        Assert.assertEquals(0.04, matrix.getValue(3, 2), 1E-6);
        Assert.assertEquals(0.06, matrix.getValue(3, 3), 1E-6);
    }
}
