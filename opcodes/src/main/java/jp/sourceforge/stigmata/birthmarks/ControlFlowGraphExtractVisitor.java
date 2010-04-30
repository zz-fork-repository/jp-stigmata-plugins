package jp.sourceforge.stigmata.birthmarks;

/*
 * $Id$
 */

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

/**
 *
 *
 * @author tamada
 * @version $Revision$
 */
public class ControlFlowGraphExtractVisitor extends ClassAdapter{
    private Map<String, MethodNode> opcodesMap = new LinkedHashMap<String, MethodNode>();

    public ControlFlowGraphExtractVisitor(ClassVisitor visitor){
        super(visitor);
    }

    public Iterator<String> getMethodNames(){
        return opcodesMap.keySet().iterator();
    }

    public ControlFlowGraph getGraph(String name){
        return buildControlFlow(name, opcodesMap.get(name));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
        MethodNode node = new MethodNode(access, name, descriptor, signature, exceptions);

        opcodesMap.put(name + descriptor, node);

        return node;
    }

    private ControlFlowGraph buildControlFlow(String methodName, MethodNode node){
        return new ControlFlowGraph(methodName, node);
    }
}
