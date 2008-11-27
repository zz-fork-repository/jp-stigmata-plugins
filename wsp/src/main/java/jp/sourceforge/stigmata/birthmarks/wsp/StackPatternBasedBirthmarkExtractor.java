package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.BirthmarkEnvironment;
import jp.sourceforge.stigmata.ExtractionUnit;
import jp.sourceforge.stigmata.birthmarks.ASMBirthmarkExtractor;
import jp.sourceforge.stigmata.birthmarks.BirthmarkExtractVisitor;
import jp.sourceforge.stigmata.spi.BirthmarkSpi;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class StackPatternBasedBirthmarkExtractor extends ASMBirthmarkExtractor{

    @Override
    public BirthmarkExtractVisitor createExtractVisitor(ClassWriter writer, Birthmark birthmark, BirthmarkEnvironment environment){
        return new Visitor(writer, birthmark, environment);
    }

    public StackPatternBasedBirthmarkExtractor(BirthmarkSpi service){
        super(service);
    }

    @Override
    public ExtractionUnit[] getAcceptableUnits(){
        return new ExtractionUnit[] {
            ExtractionUnit.ARCHIVE, ExtractionUnit.PACKAGE, ExtractionUnit.CLASS,
        };
    }

    private BirthmarkElement[] buildElement(List<Opcode> opcodes){
        List<BirthmarkElement> elements = new ArrayList<BirthmarkElement>();
        Map<Label, Integer> tableMap = new HashMap<Label, Integer>();

        int currentDepth = 0;
        for(Opcode opcode: opcodes){
            Integer forwardedStatus = null;
            if(opcode.getCategory() != Opcode.Category.TARGETER){
                forwardedStatus = tableMap.get(((LabelOpcode)opcode).getLabel());
            }
            if(forwardedStatus == null){
                currentDepth += opcode.getAct();
            }
            else{
                currentDepth = forwardedStatus + opcode.getAct(); 
            }
            if(opcode.getCategory() == Opcode.Category.BRANCH){
                for(Iterator<Label> i = opcode.labels(); i.hasNext(); ){
                    Label label = i.next();
                    tableMap.put(label, currentDepth);
                }
            }
        }

        return elements.toArray(new BirthmarkElement[elements.size()]);
    }

    private class Visitor extends BirthmarkExtractVisitor{
        public Visitor(ClassVisitor visitor, Birthmark birthmark, BirthmarkEnvironment environment){
            super(visitor, birthmark, environment);
        }

        public void visitEnd(){
        }

        @Override
        public MethodVisitor visitMethod(int arg0, String arg1, String arg2, String arg3, String[] arg4){
            MethodVisitor visitor = super.visitMethod(arg0, arg1, arg2, arg3, arg4);
            OpcodeExtractionMethodVisitor opcodeVisitor = new OpcodeExtractionMethodVisitor(visitor);
            opcodeVisitor.addOpcodeExtractionFinishListener(new OpcodeExtractionFinishListener(){
                @Override
                public void finishExtractionOpcodes(List<Opcode> opcodes){
                    BirthmarkElement[] elements = buildElement(opcodes);

                    for(BirthmarkElement be: elements){
                        addElement(be);
                    }
                }
            });

            return opcodeVisitor;
        }
    };


}
