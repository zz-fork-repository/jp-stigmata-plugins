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

        graph.setIncludingExceptionFlow(true);
        Assert.assertEquals(7, graph.getBasicBlockSize());
    }
}
