package jp.sourceforge.stigmata.birthmarks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

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

    public int[][] getGraphMatrix(){
        int[][] matrix = new int[blocks.length][blocks.length];

        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks.length; j++){
                int nextValue = 0;
                for(Iterator<BasicBlock> iter = blocks[i].nextIterator(); iter.hasNext(); ){
                    BasicBlock nextBlock = iter.next();
                    if(nextBlock == blocks[j]){
                        nextValue = 1;
                        break;
                    }
                }
                matrix[i][j] = nextValue;
            }
        }
        return matrix;
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

    private Set<LabelNode> collectLabels(MethodNode node){
        Set<LabelNode> jumpedTarget = new HashSet<LabelNode>();
        int size = node.instructions.size();
        for(int i = 0; i < size; i++){
            AbstractInsnNode inst = node.instructions.get(i);
            switch(inst.getType()){
            case AbstractInsnNode.JUMP_INSN:
            {
                JumpInsnNode jump = (JumpInsnNode)inst;
                jumpedTarget.add(jump.label);
                break;
            }
            case AbstractInsnNode.LOOKUPSWITCH_INSN:
            {
                LookupSwitchInsnNode lookup = (LookupSwitchInsnNode)inst;
                jumpedTarget.add(lookup.dflt);
                for(Object label: lookup.labels){
                    jumpedTarget.add((LabelNode)label);
                }
                break;
            }
            case AbstractInsnNode.TABLESWITCH_INSN:
            {
                TableSwitchInsnNode lookup = (TableSwitchInsnNode)inst;
                jumpedTarget.add(lookup.dflt);
                for(Object label: lookup.labels){
                    jumpedTarget.add((LabelNode)label);
                }
                break;
            }
            }
        }
        if(isIncludingExceptionFlow()){
            for(Object object: node.tryCatchBlocks){
                jumpedTarget.add(((TryCatchBlockNode)object).handler);
            }
        }
        return jumpedTarget;
    }

    private BasicBlock[] separateBasicBlock(MethodNode node, Set<LabelNode> jumpedTarget){
        int size = node.instructions.size();

        List<BasicBlock> blockList = new ArrayList<BasicBlock>();
        BasicBlock block = new BasicBlock();
        for(int i = 0; i < size; i++){
            AbstractInsnNode inst = node.instructions.get(i);

            if(jumpedTarget.contains(inst)){
                if(!block.isEmpty()){
                    blockList.add(block);
                    block = new BasicBlock();
                }
            }
            block.addNode(inst);
            if(inst.getType() == AbstractInsnNode.JUMP_INSN
                    || inst.getType() == AbstractInsnNode.TABLESWITCH_INSN
                    || inst.getType() == AbstractInsnNode.LOOKUPSWITCH_INSN){
                if(!block.isEmpty()){
                    blockList.add(block);
                    BasicBlock block2 = new BasicBlock();
                    if(inst.getOpcode() != Opcodes.GOTO && inst.getOpcode() != Opcodes.JSR){
                        block2.setPrev(block);
                    }
                    block = block2;
                }
            }
        }
        blockList.add(block);
        return blockList.toArray(new BasicBlock[blockList.size()]);
    }

    private BasicBlock[] joinBasicBlocks(BasicBlock[] blocks){
        for(int i = 0; i < blocks.length; i++){
            Label[] labels = blocks[i].getTargets();
            for(int j = 0; j < labels.length; j++){
                for(int k = 0; k < blocks.length; k++){
                    if(i != k && blocks[k].hasLabel(labels[j])){
                        blocks[i].setNext(blocks[k]);
                        break;
                    }
                }
            }
            if(labels.length == 0 && (i + 1) < blocks.length){
                blocks[i].setNext(blocks[i + 1]);
            }
        }

        return blocks;
    }

    private void parse(MethodNode node){
        Set<LabelNode> jumpedTarget = collectLabels(node);
        BasicBlock[] blocks = separateBasicBlock(node, jumpedTarget);
        this.blocks = joinBasicBlocks(blocks);
    }
}
