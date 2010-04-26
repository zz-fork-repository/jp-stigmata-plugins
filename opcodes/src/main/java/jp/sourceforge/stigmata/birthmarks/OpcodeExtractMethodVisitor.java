package jp.sourceforge.stigmata.birthmarks;

/*
 * $Id$
 */

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 *
 * @author Haruaki TAMADA
 * @version $Revision$
 */
public class OpcodeExtractMethodVisitor extends MethodAdapter{
    private List<Opcode> opcodes;
    private OpcodeManager manager = OpcodeManager.getInstance();
    private List<OpcodeExtractListener> listeners = new ArrayList<OpcodeExtractListener>();

    public OpcodeExtractMethodVisitor(MethodVisitor visitor){
        this(visitor, new ArrayList<Opcode>());
    }

    public OpcodeExtractMethodVisitor(MethodVisitor visitor, List<Opcode> opcodes){
        super(visitor);
        this.opcodes = opcodes;
    }

    public void addOpcodeExtractListener(OpcodeExtractListener listener){
        listeners.add(listener);
    }

    public void removeOpcodeExtractListener(OpcodeExtractListener listener){
        listeners.add(listener);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc){
        Opcode field = manager.getOpcode(opcode);
        Opcode o = new Opcode(field);
        int size = Type.getType(desc).getSize();
        switch(opcode){
        case Opcodes.PUTFIELD:
            size = -1 - size;
            break;
        case Opcodes.PUTSTATIC:
            size = 0 - size;
            break;
        case Opcodes.GETFIELD:
            size = -1 + size;
            break;
        case Opcodes.GETSTATIC:
            size = 0 + size;
            break;
        }
        o.setAct(size);
        opcodes.add(o);
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitIincInsn(int var, int increment){
        opcodes.add(manager.getOpcode(Opcodes.IINC));
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitInsn(int opcode){
        opcodes.add(manager.getOpcode(opcode));
        super.visitInsn(opcode);
    }

    @Override
    public void visitLabel(Label label){
        opcodes.add(new LabelOpcode(label));
    }

    @Override
    public void visitIntInsn(int opcode, int operand){
        opcodes.add(manager.getOpcode(opcode));
        super.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label){
        Opcode o = manager.getOpcode(opcode);
        o.addLabel(label);
        opcodes.add(o);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object value){
        opcodes.add(manager.getOpcode(Opcodes.LDC));
        super.visitLdcInsn(value);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label defaultLabel, Label[] labels){
        Opcode tableSwitch = new Opcode(manager.getOpcode(Opcodes.TABLESWITCH));
        tableSwitch.setLabels(labels);
        tableSwitch.addLabel(defaultLabel);

        opcodes.add(tableSwitch);
        super.visitTableSwitchInsn(min, max, defaultLabel, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label defaultHandle, int[] keys, Label[] labels){
        Opcode lookupSwitch = new Opcode(manager.getOpcode(Opcodes.LOOKUPSWITCH));
        lookupSwitch.setLabels(labels);
        lookupSwitch.addLabel(defaultHandle);

        opcodes.add(lookupSwitch);
        super.visitLookupSwitchInsn(defaultHandle, keys, labels);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc){
        Opcode methodOpcode = new Opcode(manager.getOpcode(opcode));
        Type[] types = Type.getArgumentTypes(desc);
        int argumentSize = 0;
        for(Type type: types) argumentSize = type.getSize();
        int size = Type.getReturnType(desc).getSize();
        switch(opcode){
        case Opcodes.INVOKESTATIC:
            size = size - argumentSize;
            break;
        case Opcodes.INVOKEINTERFACE:
        case Opcodes.INVOKESPECIAL:
        case Opcodes.INVOKEVIRTUAL:
            size = size - argumentSize - 1;
            break;
        }
        methodOpcode.setAct(size);

        opcodes.add(methodOpcode);
        super.visitMethodInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims){
        opcodes.add(manager.getOpcode(Opcodes.MULTIANEWARRAY));
        super.visitMultiANewArrayInsn(desc, dims);
    }

    @Override
    public void visitTypeInsn(int opcode, String desc){
        opcodes.add(manager.getOpcode(opcode));
        super.visitTypeInsn(opcode, desc);
    }

    @Override
    public void visitVarInsn(int opcode, int var){
        opcodes.add(manager.getOpcode(opcode));
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitEnd(){
        for(OpcodeExtractListener listener: listeners){
            listener.extractOpcodesFinished(opcodes);
        }
    }
}
