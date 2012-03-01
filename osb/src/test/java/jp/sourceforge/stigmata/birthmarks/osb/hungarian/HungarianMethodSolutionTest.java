package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HungarianMethodSolutionTest{
    @Before
    public void setUp(){
    }

    @Test
    public void testBasic(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 0.2, 0.2, 0.333, 0.1, },
            { 0.75, 0.75, 0.666, 0.2, },
            { 0.3, 0.3, 0.4, 0.15, },
            { 0.2, 0.2, 1, 0.2, },
        }, true);
        HungarianMethod method = new HungarianMethod(matrix);

        Assert.assertEquals(0.8,   matrix.getValue(0, 0), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(0, 1), 1E-6);
        Assert.assertEquals(0.667, matrix.getValue(0, 2), 1E-6);
        Assert.assertEquals(0.9,   matrix.getValue(0, 3), 1E-6);
        Assert.assertEquals(0.25,  matrix.getValue(1, 0), 1E-6);
        Assert.assertEquals(0.25,  matrix.getValue(1, 1), 1E-6);
        Assert.assertEquals(0.334, matrix.getValue(1, 2), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(1, 3), 1E-6);
        Assert.assertEquals(0.7,   matrix.getValue(2, 0), 1E-6);
        Assert.assertEquals(0.7,   matrix.getValue(2, 1), 1E-6);
        Assert.assertEquals(0.6,   matrix.getValue(2, 2), 1E-6);
        Assert.assertEquals(0.85,  matrix.getValue(2, 3), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(3, 0), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(3, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(3, 2), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(3, 3), 1E-6);

        matrix.minimize();
        
        Assert.assertEquals(0.133, matrix.getValue(0, 0), 1E-6);
        Assert.assertEquals(0.133, matrix.getValue(0, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(0, 2), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(0, 3), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(1, 0), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(1, 1), 1E-6);
        Assert.assertEquals(0.084, matrix.getValue(1, 2), 1E-6);
        Assert.assertEquals(0.317, matrix.getValue(1, 3), 1E-6);
        Assert.assertEquals(0.1,   matrix.getValue(2, 0), 1E-6);
        Assert.assertEquals(0.1,   matrix.getValue(2, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(2, 2), 1E-6);
        Assert.assertEquals(0.017, matrix.getValue(2, 3), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(3, 0), 1E-6);
        Assert.assertEquals(0.8,   matrix.getValue(3, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(3, 2), 1E-6);
        Assert.assertEquals(0.567, matrix.getValue(3, 3), 1E-6);

        Line[] lines1 = method.getLines();
        Assert.assertEquals(3, lines1.length);

        Assert.assertEquals(Line.Direction.COLUMN, lines1[0].getDirection());
        Assert.assertEquals(2, lines1[0].getIndex());
        Assert.assertEquals(3, lines1[0].getCount());
        Assert.assertEquals("[index: 2, count: 3(0) (COLUMN)]", lines1[0].toString());

        Assert.assertEquals(Line.Direction.ROW, lines1[1].getDirection());
        Assert.assertEquals(1, lines1[1].getIndex());
        Assert.assertEquals(2, lines1[1].getCount());
        Assert.assertEquals("[index: 1, count: 2(1) (ROW)]", lines1[1].toString());

        Assert.assertEquals(Line.Direction.COLUMN, lines1[2].getDirection());
        Assert.assertEquals(3, lines1[2].getIndex());
        Assert.assertEquals(1, lines1[2].getCount());
        Assert.assertEquals("[index: 3, count: 1(1) (COLUMN)]", lines1[2].toString());

        method.reduceMatrix(lines1);
        Assert.assertEquals(0.033, matrix.getValue(0, 0), 1E-6);
        Assert.assertEquals(0.033, matrix.getValue(0, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(0, 2), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(0, 3), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(1, 0), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(1, 1), 1E-6);
        Assert.assertEquals(0.184, matrix.getValue(1, 2), 1E-6);
        Assert.assertEquals(0.417, matrix.getValue(1, 3), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(2, 0), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(2, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(2, 2), 1E-6);
        Assert.assertEquals(0.017, matrix.getValue(2, 3), 1E-6);
        Assert.assertEquals(0.7,   matrix.getValue(3, 0), 1E-6);
        Assert.assertEquals(0.7,   matrix.getValue(3, 1), 1E-6);
        Assert.assertEquals(0,     matrix.getValue(3, 2), 1E-6);
        Assert.assertEquals(0.567, matrix.getValue(3, 3), 1E-6);

        Line[] lines2 = method.getLines();
        Assert.assertEquals(4, lines2.length);

        Assert.assertEquals(Line.Direction.COLUMN, lines2[0].getDirection());
        Assert.assertEquals(2, lines2[0].getIndex());
        Assert.assertEquals(3, lines2[0].getCount());

        Assert.assertEquals(Line.Direction.ROW, lines2[1].getDirection());
        Assert.assertEquals(2, lines2[1].getIndex());
        Assert.assertEquals(2, lines2[1].getCount());

        Assert.assertEquals(Line.Direction.ROW, lines2[2].getDirection());
        Assert.assertEquals(1, lines2[2].getIndex());
        Assert.assertEquals(2, lines2[2].getCount());

        Assert.assertEquals(Line.Direction.COLUMN, lines2[3].getDirection());
        Assert.assertEquals(3, lines2[3].getIndex());
        Assert.assertEquals(1, lines2[3].getCount());

        Assert.assertEquals(2.15, method.solve(), 1E-6);

        Cell[] cell = method.getSolutions();
        Assert.assertEquals(4, cell.length);

        Assert.assertEquals(0, cell[0].getX());
        Assert.assertEquals(3, cell[0].getY());
        Assert.assertEquals(0.1, cell[0].getValue(), 1E-6);

        Assert.assertEquals(1, cell[1].getX());
        Assert.assertEquals(0, cell[1].getY());
        Assert.assertEquals(0.75, cell[1].getValue(), 1E-6);

        Assert.assertEquals(2, cell[2].getX());
        Assert.assertEquals(1, cell[2].getY());
        Assert.assertEquals(0.3, cell[2].getValue(), 1E-6);

        Assert.assertEquals(3, cell[3].getX());
        Assert.assertEquals(2, cell[3].getY());
        Assert.assertEquals(1, cell[3].getValue(), 1E-6);
    }
}
