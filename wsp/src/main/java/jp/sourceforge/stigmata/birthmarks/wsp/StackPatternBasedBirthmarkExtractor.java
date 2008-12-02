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
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
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
    public StackPatternBasedBirthmarkExtractor(BirthmarkSpi service){
        super(service);
    }

    @Override
    public BirthmarkExtractVisitor createExtractVisitor(ClassWriter writer, Birthmark birthmark, BirthmarkContext context){
        return new Visitor(writer, birthmark, context);
    }

    @Override
    public ExtractionUnit[] getAcceptableUnits(){
        return new ExtractionUnit[] {
            ExtractionUnit.CLASS,
        };
    }

    private BirthmarkElement[] buildElement(List<Opcode> opcodes, BirthmarkContext context){
        StackPattern pattern = buildStackPattern(opcodes, context);
        List<BirthmarkElement> elements = new ArrayList<BirthmarkElement>();

        StackPattern subpattern = new StackPattern();
        for(CurrentDepth depth: pattern){
            subpattern.update(depth);
            if(depth.getDepth() == 0){
                elements.add(new StackPatternBasedBirthmarkElement(subpattern));
                subpattern = new StackPattern();
            }
        }
        elements.add(new StackPatternBasedBirthmarkElement(subpattern));

        return elements.toArray(new BirthmarkElement[elements.size()]);
    }

    @SuppressWarnings("unchecked")
    private StackPattern buildStackPattern(List<Opcode> opcodes, BirthmarkContext context){ 
        Map<Label, Integer> tableMap = new HashMap<Label, Integer>();
        StackPattern pattern = new StackPattern();
        Map<Integer, Integer> weights = (Map<Integer, Integer>)context.getProperty("birthmarks.wsp.weights");

        int currentDepth = 0;
        Integer forwardedStatus = null;
        for(Opcode opcode: opcodes){
            opcode.setWeight(weights.get(opcode.getOpcode()));
            if(opcode.getCategory() == Opcode.Category.TARGETER){
                forwardedStatus = tableMap.get(((LabelOpcode)opcode).getLabel());
            }
            else{
                if(forwardedStatus == null){
                    currentDepth += opcode.getAct();
                }
                else{
                    currentDepth = forwardedStatus + opcode.getAct(); 
                }
                forwardedStatus = null;

                pattern.update(currentDepth, opcode);
                if(opcode.getCategory() == Opcode.Category.BRANCH){
                    for(Iterator<Label> i = opcode.labels(); i.hasNext(); ){
                        Label label = i.next();
                        tableMap.put(label, currentDepth);
                    }
                }
            }
        }
        return pattern;
    }

    private class Visitor extends BirthmarkExtractVisitor{
        private List<Opcode> opcodeList = new ArrayList<Opcode>();

        public Visitor(ClassVisitor visitor, Birthmark birthmark, BirthmarkContext context){
            super(visitor, birthmark, context);
        }

        public void visitEnd(){
            BirthmarkElement[] elements = buildElement(opcodeList, getContext());
            for(BirthmarkElement element: elements){
                addElement(element);
            }
        }

        @Override
        public MethodVisitor visitMethod(int arg0, String arg1, String arg2, String arg3, String[] arg4){
            MethodVisitor visitor = super.visitMethod(arg0, arg1, arg2, arg3, arg4);
            OpcodeExtractionMethodVisitor opcodeVisitor = new OpcodeExtractionMethodVisitor(visitor, opcodeList);

            return opcodeVisitor;
        }
    };


}
