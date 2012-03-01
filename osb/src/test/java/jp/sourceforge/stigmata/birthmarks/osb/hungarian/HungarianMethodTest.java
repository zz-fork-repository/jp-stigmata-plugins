package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HungarianMethodTest{
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
        Assert.assertEquals(2.15, method.solve(), 1E-6);
    }

    @Test
    public void testBasic2(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 0.209595, 0.465415, 0.760024, 0.956651, 0.838918, 0.914212, 0.299751, },
            { 0.350187, 0.043090, 0.996489, 0.130135, 0.116187, 0.395586, 0.257572, },
            { 0.400339, 0.340549, 0.518255, 0.301846, 0.215443, 0.481817, 0.167668, },
            { 0.844414, 0.737599, 0.894256, 0.667530, 0.029905, 0.256991, 0.867919, },
            { 0.113587, 0.433392, 0.067705, 0.763238, 0.508876, 0.460116, 0.168199, },
            { 0.697585, 0.874909, 0.568728, 0.646872, 0.027345, 0.428272, 0.857028, },
            { 0.110312, 0.561466, 0.112358, 0.069674, 0.665109, 0.041041, 0.130386, },
            { 0.014101, 0.773713, 0.924393, 0.638526, 0.703886, 0.770603, 0.928259, },
            { 0.426957, 0.288075, 0.947143, 0.093035, 0.241806, 0.970468, 0.891780, },
        }, true);
        HungarianMethod method = new HungarianMethod(matrix);
        Assert.assertEquals(6.236299, method.solve(), 1E-6);
    }

    @Test
    public void testBasic3(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 0.109821, 0.108200, 0.967758, 0.879690, 0.606278, 0.627169, 0.230391, },  
            { 0.517603, 0.045502, 0.279343, 0.942029, 0.630428, 0.517259, 0.659899, },  
            { 0.846279, 0.220084, 0.929258, 0.009920, 0.773227, 0.304251, 0.406748, },  
            { 0.075661, 0.022613, 0.006470, 0.847046, 0.869278, 0.147251, 0.313651, },  
            { 0.118903, 0.911011, 0.800919, 0.634199, 0.909296, 0.865808, 0.780742, },  
            { 0.701107, 0.889183, 0.226646, 0.516193, 0.681720, 0.694964, 0.842307, },  
            { 0.035120, 0.579560, 0.203825, 0.639133, 0.871800, 0.143418, 0.718832, },  
        }, true);
        HungarianMethod method = new HungarianMethod(matrix);
        Assert.assertEquals(6.099167, method.solve(), 1E-6);
    }

    @Test
    public void testBasic4(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 0.2, 0.3, 0.5, 0.2, 0.6, },
            { 0.4, 0.6, 0.6, 0.6, 0.7, },
            { 0.4, 0.4, 0.7, 0.3, 0.4, },
            { 0.3, 0.6, 0.4, 0.5, 0.5, },
            { 0.4, 0.2, 0.7, 0.4, 0.4, },
        }, false);
        HungarianMethod method = new HungarianMethod(matrix);
        double value = method.solve();
        Cell[] solution = method.getSolutions();
        Assert.assertEquals(5, solution.length);

        Assert.assertEquals(0, solution[0].getX());
        Assert.assertEquals(3, solution[0].getY());
        Assert.assertEquals(0.2, solution[0].getValue(), 1E-6);

        Assert.assertEquals(1, solution[1].getX());
        Assert.assertEquals(0, solution[1].getY());
        Assert.assertEquals(0.4, solution[1].getValue(), 1E-6);

        Assert.assertEquals(2, solution[2].getX());
        Assert.assertEquals(4, solution[2].getY());
        Assert.assertEquals(0.4, solution[2].getValue(), 1E-6);

        Assert.assertEquals(3, solution[3].getX());
        Assert.assertEquals(2, solution[3].getY());
        Assert.assertEquals(0.4, solution[3].getValue(), 1E-6);

        Assert.assertEquals(4, solution[4].getX());
        Assert.assertEquals(1, solution[4].getY());
        Assert.assertEquals(0.2, solution[4].getValue(), 1E-6);

        Assert.assertEquals(1.6, value, 1E-6);
    }

    @Test
    public void testBasic5(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 0.2, 0.3, 0.4, 0.3, 0.6, },
            { 0.4, 0.6, 0.5, 0.6, 0.7, },
            { 0.4, 0.4, 0.6, 0.3, 0.4, },
            { 0.4, 0.6, 0.3, 0.5, 0.5, },
            { 0.4, 0.2, 0.6, 0.4, 0.4, },
        }, false);
        HungarianMethod method = new HungarianMethod(matrix);
        double value = method.solve();
        Cell[] solution = method.getSolutions();
        Assert.assertEquals(5, solution.length);

        Assert.assertEquals(0, solution[0].getX());
        Assert.assertEquals(3, solution[0].getY());
        Assert.assertEquals(0.3, solution[0].getValue(), 1E-6);

        Assert.assertEquals(1, solution[1].getX());
        Assert.assertEquals(0, solution[1].getY());
        Assert.assertEquals(0.4, solution[1].getValue(), 1E-6);

        Assert.assertEquals(2, solution[2].getX());
        Assert.assertEquals(4, solution[2].getY());
        Assert.assertEquals(0.4, solution[2].getValue(), 1E-6);

        Assert.assertEquals(3, solution[3].getX());
        Assert.assertEquals(2, solution[3].getY());
        Assert.assertEquals(0.3, solution[3].getValue(), 1E-6);

        Assert.assertEquals(4, solution[4].getX());
        Assert.assertEquals(1, solution[4].getY());
        Assert.assertEquals(0.2, solution[4].getValue(), 1E-6);

        Assert.assertEquals(1.6, value, 1E-6);
    }

    @Test
    public void testBasic6(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 0.8, 0.9, 0.3, 0.8, },
            { 0.6, 0.8, 0.6, 0.2, },
            { 0.7, 0.7, 0.9, 0.6, },
            { 0.2, 0.5, 0.8, 0.4, },
            { 0.4, 0.6, 0.2, 0.7, },
        }, true);
        HungarianMethod method = new HungarianMethod(matrix);
        double value = method.solve();
        Cell[] solutions = method.getSolutions();

        Assert.assertEquals(3.2, value, 1E-6);
        Assert.assertEquals(4, solutions.length);

        Assert.assertEquals(0, solutions[0].getX());
        Assert.assertEquals(0, solutions[0].getY());
        Assert.assertEquals(0.8, solutions[0].getValue(), 1E-6);

        Assert.assertEquals(1, solutions[1].getX());
        Assert.assertEquals(1, solutions[1].getY());
        Assert.assertEquals(0.8, solutions[1].getValue(), 1E-6);

        Assert.assertEquals(2, solutions[2].getX());
        Assert.assertEquals(2, solutions[2].getY());
        Assert.assertEquals(0.9, solutions[2].getValue(), 1E-6);

        Assert.assertEquals(4, solutions[3].getX());
        Assert.assertEquals(3, solutions[3].getY());
        Assert.assertEquals(0.7, solutions[3].getValue(), 1E-6);
    }

    @Test
    public void testBasic7(){
        CostMatrix matrix = new CostMatrix(new double[][]{
            { 1.0, 0.0, 0.2, 0.0, 0.33, 0.2, 0.1, 0.08, 0.2, 0.2, 0.0, 0.2, 0.2, 0.2, 0.2, 0.14, 0.14, 0.11, 0.2, 0.25, 0.09, 0.2, 0.0, 0.0, 0.25, 0.0, },
            { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.33, },
            { 0.2, 0.0, 1.0, 0.17, 0.0, 0.0, 0.44, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.17, 0.27, 0.0, 0.0, 0.2, 0.0, 0.0, },
            { 0.0, 0.0, 0.17, 1.0, 0.0, 0.17, 0.2, 0.07, 0.17, 0.17, 0.0, 0.17, 0.17, 0.17, 0.17, 0.13, 0.13, 0.1, 0.17, 0.2, 0.08, 0.17, 0.0, 0.25, 0.2, 0.0, },
            { 0.33, 0.0, 0.0, 0.0, 1.0, 0.2, 0.1, 0.08, 0.2, 0.2, 0.0, 0.2, 0.2, 0.2, 0.2, 0.14, 0.14, 0.11, 0.2, 0.25, 0.09, 0.2, 0.0, 0.0, 0.25, 0.0, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 1.0, 0.18, 0.23, 1.0, 0.6, 0.0, 0.6, 0.6, 0.6, 0.6, 0.43, 0.43, 0.33, 0.6, 0.4, 0.17, 0.33, 0.0, 0.0, 0.4, 0.17, },
            { 0.1, 0.0, 0.44, 0.2, 0.1, 0.18, 1.0, 0.24, 0.18, 0.18, 0.0, 0.18, 0.18, 0.18, 0.18, 0.25, 0.25, 0.31, 0.18, 0.33, 0.73, 0.18, 0.0, 0.1, 0.33, 0.09, },
            { 0.08, 0.0, 0.0, 0.07, 0.08, 0.23, 0.24, 1.0, 0.23, 0.33, 0.0, 0.33, 0.33, 0.33, 0.33, 0.5, 0.5, 0.67, 0.33, 0.15, 0.29, 0.23, 0.0, 0.08, 0.15, 0.15, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 1.0, 0.18, 0.23, 1.0, 0.6, 0.0, 0.6, 0.6, 0.6, 0.6, 0.43, 0.43, 0.33, 0.6, 0.4, 0.17, 0.33, 0.0, 0.0, 0.4, 0.17, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.6, 0.18, 0.33, 0.6, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.67, 0.67, 0.5, 1.0, 0.4, 0.17, 0.6, 0.0, 0.0, 0.4, 0.17, },
            { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.6, 0.18, 0.33, 0.6, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.67, 0.67, 0.5, 1.0, 0.4, 0.17, 0.6, 0.0, 0.0, 0.4, 0.17, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.6, 0.18, 0.33, 0.6, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.67, 0.67, 0.5, 1.0, 0.4, 0.17, 0.6, 0.0, 0.0, 0.4, 0.17, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.6, 0.18, 0.33, 0.6, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.67, 0.67, 0.5, 1.0, 0.4, 0.17, 0.6, 0.0, 0.0, 0.4, 0.17, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.6, 0.18, 0.33, 0.6, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.67, 0.67, 0.5, 1.0, 0.4, 0.17, 0.6, 0.0, 0.0, 0.4, 0.17, },
            { 0.14, 0.0, 0.0, 0.13, 0.14, 0.43, 0.25, 0.5, 0.43, 0.67, 0.0, 0.67, 0.67, 0.67, 0.67, 1.0, 1.0, 0.75, 0.67, 0.29, 0.23, 0.43, 0.0, 0.14, 0.29, 0.29, },
            { 0.14, 0.0, 0.0, 0.13, 0.14, 0.43, 0.25, 0.5, 0.43, 0.67, 0.0, 0.67, 0.67, 0.67, 0.67, 1.0, 1.0, 0.75, 0.67, 0.29, 0.23, 0.43, 0.0, 0.14, 0.29, 0.29, },
            { 0.11, 0.0, 0.0, 0.1, 0.11, 0.33, 0.31, 0.67, 0.33, 0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.75, 0.75, 1.0, 0.5, 0.22, 0.29, 0.33, 0.0, 0.11, 0.22, 0.22, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.6, 0.18, 0.33, 0.6, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.67, 0.67, 0.5, 1.0, 0.4, 0.17, 0.6, 0.0, 0.0, 0.4, 0.17, },
            { 0.25, 0.0, 0.17, 0.2, 0.25, 0.4, 0.33, 0.15, 0.4, 0.4, 0.0, 0.4, 0.4, 0.4, 0.4, 0.29, 0.29, 0.22, 0.4, 1.0, 0.18, 0.4, 0.0, 0.25, 0.2, 0.2, },
            { 0.09, 0.0, 0.27, 0.08, 0.09, 0.17, 0.73, 0.29, 0.17, 0.17, 0.0, 0.17, 0.17, 0.17, 0.17, 0.23, 0.23, 0.29, 0.17, 0.18, 1.0, 0.17, 0.0, 0.09, 0.3, 0.18, },
            { 0.2, 0.0, 0.0, 0.17, 0.2, 0.33, 0.18, 0.23, 0.33, 0.6, 0.0, 0.6, 0.6, 0.6, 0.6, 0.43, 0.43, 0.33, 0.6, 0.4, 0.17, 1.0, 0.0, 0.0, 0.17, 0.17, },
            { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, },
            { 0.0, 0.0, 0.2, 0.25, 0.0, 0.0, 0.1, 0.08, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.14, 0.14, 0.11, 0.0, 0.25, 0.09, 0.0, 0.0, 1.0, 0.0, 0.25, },
            { 0.25, 0.0, 0.0, 0.2, 0.25, 0.4, 0.33, 0.15, 0.4, 0.4, 0.0, 0.4, 0.4, 0.4, 0.4, 0.29, 0.29, 0.22, 0.4, 0.2, 0.3, 0.17, 0.0, 0.0, 1.0, 0.2, },
            { 0.0, 0.33, 0.0, 0.0, 0.0, 0.17, 0.09, 0.15, 0.17, 0.17, 0.0, 0.17, 0.17, 0.17, 0.17, 0.29, 0.29, 0.22, 0.17, 0.2, 0.18, 0.17, 0.0, 0.25, 0.2, 1.0, },
        }, true);
        HungarianMethod method = new HungarianMethod(matrix);
        double value = method.solve();
        Assert.assertEquals(26, value, 1E-6);
    }

    @Test
    public void testBasic8(){
        CostMatrix matrix = new CostMatrix(new double[][] {
            { 1.0000, 0.0000, 0.0000, 0.0000, 0.2000, 0.1429, 0.6667, 0.0000, },
            { 0.0000, 1.0000, 0.0000, 0.0000, 0.0000, 0.0000, 0.0000, 1.0000, },
            { 0.0000, 0.0000, 1.0000, 1.0000, 0.2000, 0.1429, 0.0000, 0.0000, },
            { 0.0000, 0.0000, 1.0000, 1.0000, 0.2000, 0.1429, 0.0000, 0.0000, },
            { 0.2000, 0.0000, 0.2000, 0.2000, 1.0000, 0.4286, 0.1667, 0.0000, },
            { 0.6667, 0.0000, 0.0000, 0.0000, 0.1667, 0.1250, 1.0000, 0.0000, },
            { 0.0000, 1.0000, 0.0000, 0.0000, 0.0000, 0.0000, 0.0000, 1.0000, },
        }, true);

        HungarianMethod method = new HungarianMethod(matrix);
        double value = method.solve();
        Assert.assertEquals(7, value, 1E-6);
    }
}
