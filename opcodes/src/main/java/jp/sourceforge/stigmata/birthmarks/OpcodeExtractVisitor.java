package jp.sourceforge.stigmata.birthmarks;

/*
 * $Id$
 */

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.birthmarks.BirthmarkExtractVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 *
 *
 * @author tamada
 * @version $Revision$
 */
public class OpcodeExtractVisitor extends BirthmarkExtractVisitor{
    private List<Opcode> opcodeList = new ArrayList<Opcode>();
    private BirthmarkElementBuilder builder;

    public OpcodeExtractVisitor(ClassVisitor visitor, Birthmark birthmark, BirthmarkContext context, BirthmarkElementBuilder builder){
        super(visitor, birthmark, context);
        this.builder = builder;
    }

    public void visitEnd(){
        BirthmarkElement[] elements = builder.buildBirthmarkElements(opcodeList, getContext());
        for(BirthmarkElement element: elements){
            addElement(element);
        }
    }

    @Override
    public MethodVisitor visitMethod(int arg0, String arg1, String arg2, String arg3, String[] arg4){
        MethodVisitor visitor = super.visitMethod(arg0, arg1, arg2, arg3, arg4);
        OpcodeExtractMethodVisitor opcodeVisitor = new OpcodeExtractMethodVisitor(visitor, opcodeList);

        return opcodeVisitor;
    }
}
