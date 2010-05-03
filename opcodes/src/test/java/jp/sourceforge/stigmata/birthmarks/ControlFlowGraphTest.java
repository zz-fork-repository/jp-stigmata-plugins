package jp.sourceforge.stigmata.birthmarks;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class ControlFlowGraphTest {
    private ControlFlowGraphExtractVisitor visitor;

    @Before
    public void setUp() throws Exception{
        ClassReader reader = new ClassReader(getClass().getResource("/resources/MyServer.class").openStream());

        visitor = new ControlFlowGraphExtractVisitor(new ClassWriter(0));
        reader.accept(visitor, 0);
    }

    @Test
    public void testBasic() throws Exception{
        Iterator<String> iterator = visitor.getMethodNames();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<init>(I)V", iterator.next());
        Assert.assertFalse(iterator.hasNext());

        ControlFlowGraph graph = visitor.getGraph("<init>(I)V");

        Assert.assertEquals(6, graph.getBasicBlockSize());

        // graph.setIncludingExceptionFlow(true);
        // Assert.assertEquals(6, graph.getBasicBlockSize());
    }

    @Test
    public void testGraph() throws Exception{
        ControlFlowGraph graph = visitor.getGraph("<init>(I)V");
        int[][] graphMatrix = graph.getGraphMatrix();

        Assert.assertEquals(6, graphMatrix.length);

        Assert.assertEquals(0, graphMatrix[0][0]);
        Assert.assertEquals(1, graphMatrix[0][1]);
        Assert.assertEquals(1, graphMatrix[0][2]);
        Assert.assertEquals(0, graphMatrix[0][3]);
        Assert.assertEquals(0, graphMatrix[0][4]);
        Assert.assertEquals(0, graphMatrix[0][5]);

        Assert.assertEquals(0, graphMatrix[1][0]);
        Assert.assertEquals(0, graphMatrix[1][1]);
        Assert.assertEquals(0, graphMatrix[1][2]);
        Assert.assertEquals(1, graphMatrix[1][3]);
        Assert.assertEquals(0, graphMatrix[1][4]);
        Assert.assertEquals(0, graphMatrix[1][5]);

        Assert.assertEquals(0, graphMatrix[2][0]);
        Assert.assertEquals(0, graphMatrix[2][1]);
        Assert.assertEquals(0, graphMatrix[2][2]);
        Assert.assertEquals(1, graphMatrix[2][3]);
        Assert.assertEquals(0, graphMatrix[2][4]);
        Assert.assertEquals(0, graphMatrix[2][5]);

        Assert.assertEquals(0, graphMatrix[3][0]);
        Assert.assertEquals(0, graphMatrix[3][1]);
        Assert.assertEquals(0, graphMatrix[3][2]);
        Assert.assertEquals(0, graphMatrix[3][3]);
        Assert.assertEquals(0, graphMatrix[3][4]);
        Assert.assertEquals(1, graphMatrix[3][5]);

        Assert.assertEquals(0, graphMatrix[4][0]);
        Assert.assertEquals(0, graphMatrix[4][1]);
        Assert.assertEquals(0, graphMatrix[4][2]);
        Assert.assertEquals(0, graphMatrix[4][3]);
        Assert.assertEquals(0, graphMatrix[4][4]);
        Assert.assertEquals(1, graphMatrix[4][5]);

        Assert.assertEquals(0, graphMatrix[5][0]);
        Assert.assertEquals(0, graphMatrix[5][1]);
        Assert.assertEquals(0, graphMatrix[5][2]);
        Assert.assertEquals(0, graphMatrix[5][3]);
        Assert.assertEquals(0, graphMatrix[5][4]);
        Assert.assertEquals(0, graphMatrix[5][5]);
    }
}
