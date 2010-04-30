package jp.sourceforge.stigmata.birthmarks;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;

public class BasicBlock {
    private List<AbstractInsnNode> nodes = new ArrayList<AbstractInsnNode>();

    BasicBlock(){
    }

    public BasicBlock(InsnNode[] nodeArray){
        for(InsnNode node: nodeArray){
            nodes.add(node);
        }
    }

    void addNode(AbstractInsnNode node){
        nodes.add(node);
    }
}
