package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

import org.junit.Assert;
import org.junit.Test;

public class CellTest{
    @Test
    public void testBasic(){
        Cell cell = new Cell(1.0d, 2, 3);

        Assert.assertEquals(1d, cell.getValue(), 1E-6);
        Assert.assertEquals(true, cell.isAvailable());
        Assert.assertEquals(2, cell.getX());
        Assert.assertEquals(3, cell.getY());

        Assert.assertEquals("(2, 3): 1.000", cell.toString());
    }

    @Test
    public void testEmptyCell(){
        Cell cell = new Cell.EmptyCell(4, 5);

        Assert.assertEquals(false, cell.isAvailable());
        Assert.assertEquals(4, cell.getX());
        Assert.assertEquals(5, cell.getY());

        Assert.assertEquals("(4, 5): N/A", cell.toString());
    }

    @Test(expected=IllegalStateException.class)
    public void testIllegalStateException(){
        Cell cell = new Cell.EmptyCell(4, 5);
        cell.getValue();
    }
}
