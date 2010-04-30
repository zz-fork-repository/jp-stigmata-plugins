package jp.sourceforge.stigmata.birthmarks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class ControlFlowGraph {
    private String name;
    private boolean includeException;
    private MethodNode method;
    private BasicBlock[] blocks;

    public ControlFlowGraph(String name, MethodNode node){
        this(name, node, false);
    }

    public ControlFlowGraph(String name, MethodNode node, boolean includeException){
        this.includeException = includeException;
        this.name = name;
        this.method = node;
        parse(method);
    }

    public String getName(){
        return name;
    }

    public int getBasicBlockSize(){
        return blocks.length;
    }

    public boolean isIncludingExceptionFlow(){
        return includeException;
    }

    public void setIncludingExceptionFlow(boolean includeException){
        boolean oldvalue = this.includeException;
        this.includeException = includeException;
        if(oldvalue != includeException){
            parse(method);
        }
    }

    private void separateBasicBlock(MethodNode node){
        Set<LabelNode> jumpedTarget = new HashSet<LabelNode>();
        int size = node.instructions.size();

        for(int i = 0; i < size; i++){
            AbstractInsnNode inst = node.instructions.get(i);
            switch(inst.getType()){
            case AbstractInsnNode.JUMP_INSN:
                jumpedTarget.add(((JumpInsnNode)inst).label);
                break;
            case AbstractInsnNode.LOOKUPSWITCH_INSN:
            {
                LookupSwitchInsnNode lookup = (LookupSwitchInsnNode)inst;
                jumpedTarget.add(lookup.dflt);
                for(Object label: lookup.labels){
                    jumpedTarget.add((LabelNode)label);
                }
            }
            case AbstractInsnNode.TABLESWITCH_INSN:
            {
                TableSwitchInsnNode lookup = (TableSwitchInsnNode)inst;
                jumpedTarget.add(lookup.dflt);
                for(Object label: lookup.labels){
                    jumpedTarget.add((LabelNode)label);
                }
            }
            }
        }
        if(isIncludingExceptionFlow()){
            for(Object object: node.tryCatchBlocks){
                jumpedTarget.add(((TryCatchBlockNode)object).handler);
            }
        }

        List<BasicBlock> blockList = new ArrayList<BasicBlock>();
        BasicBlock block = new BasicBlock();
        for(int i = 0; i < size; i++){
            AbstractInsnNode inst = node.instructions.get(i);
            if(jumpedTarget.contains(inst)){
                blockList.add(block);
                block = new BasicBlock();
            }

            block.addNode(inst);

            if(inst.getType() == AbstractInsnNode.JUMP_INSN
                    || inst.getType() == AbstractInsnNode.TABLESWITCH_INSN
                    || inst.getType() == AbstractInsnNode.LOOKUPSWITCH_INSN){
                blockList.add(block);
                block = new BasicBlock();
            }
        }
        this.blocks = blockList.toArray(new BasicBlock[blockList.size()]);
    }

    /**
     * コントロールフローグラフを作成する．
     */
    private void parse(MethodNode node){
        separateBasicBlock(node);
    }
}
