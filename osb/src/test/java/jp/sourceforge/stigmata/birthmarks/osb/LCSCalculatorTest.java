package jp.sourceforge.stigmata.birthmarks.osb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LCSCalculatorTest{
    private LCSCalculator<String> calculator;

    @Before
    public void setUp(){
        calculator = new LCSCalculator<String>();
    }

    @Test
    public void testBasic(){
        String[] elementA = new String[] { "load", "load", "add", "store", };
        String[] elementB = new String[] { "load", "load", "mul", "load", "sub", "store", };

        Assert.assertEquals(3, calculator.calculate(elementA, elementB));
        Assert.assertEquals(3, calculator.calculate(elementB, elementA));
    }

    @Test
    public void testNullElement(){
        String[] elementA = new String[] { "load", "load", "add", null, };
        String[] elementB = new String[] { "load", "load", "mul", "load", "sub", null, };

        Assert.assertEquals(3, calculator.calculate(elementA, elementB));
    }

    @Test
    public void testZeroLengthArray(){
        String[] elementB = new String[] { "load", "load", "mul", "load", "sub", "store", };

        Assert.assertEquals(0, calculator.calculate(new String[0], elementB));
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck1(){
        String[] elementB = new String[] { "load", "load", "mul", "load", "sub", "store", };

        calculator.calculate(null, elementB);
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck2(){
        String[] elementA = new String[] { "load", "load", "add", "store", };

        calculator.calculate(elementA, null);
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck3(){
        calculator.calculate(null, null);
    }
}
