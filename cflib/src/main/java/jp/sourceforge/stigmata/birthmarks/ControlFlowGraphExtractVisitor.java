package jp.sourceforge.stigmata.birthmarks;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

/**
 *
 *
 * @author tamada
 */
public class ControlFlowGraphExtractVisitor extends BirthmarkExtractVisitor{
    private Map<String, MethodNode> opcodesMap = new LinkedHashMap<String, MethodNode>();

    public ControlFlowGraphExtractVisitor(ClassVisitor visitor, Birthmark birthmark, BirthmarkContext context){
        super(visitor, birthmark, context);
    }

    /**
     * テストのためのコンストラクタ．
     * @param visitor
     */
    ControlFlowGraphExtractVisitor(ClassVisitor visitor){
        super(visitor, null, null);
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
