package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.ExtractionUnit;
import jp.sourceforge.stigmata.birthmarks.ASMBirthmarkExtractor;
import jp.sourceforge.stigmata.birthmarks.BirthmarkElementBuilder;
import jp.sourceforge.stigmata.birthmarks.BirthmarkExtractVisitor;
import jp.sourceforge.stigmata.birthmarks.LabelOpcode;
import jp.sourceforge.stigmata.birthmarks.Opcode;
import jp.sourceforge.stigmata.birthmarks.OpcodeExtractVisitor;
import jp.sourceforge.stigmata.spi.BirthmarkSpi;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

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
        return new OpcodeExtractVisitor(writer, birthmark, context, new WSPBirthmarkElementBuilder());
    }

    @Override
    public ExtractionUnit[] getAcceptableUnits(){
        return new ExtractionUnit[] {
            ExtractionUnit.CLASS,
        };
    }

    private static class WSPBirthmarkElementBuilder implements BirthmarkElementBuilder{
        public BirthmarkElement[] buildElements(List<Opcode> opcodes, BirthmarkContext context){
            List<CurrentDepth> pattern = buildStackPattern(opcodes, context);
            List<BirthmarkElement> elements = new ArrayList<BirthmarkElement>();

            List<CurrentDepth> subPattern = new ArrayList<CurrentDepth>();
            for(CurrentDepth depth: pattern){
                subPattern.add(depth);
                if(depth.getDepth() == 0){
                    elements.add(new StackPatternBasedBirthmarkElement(subPattern.toArray(new CurrentDepth[subPattern.size()])));
                    subPattern.clear();
                }
            }
            elements.add(new StackPatternBasedBirthmarkElement(subPattern.toArray(new CurrentDepth[subPattern.size()])));

            return elements.toArray(new BirthmarkElement[elements.size()]);
        }

        @SuppressWarnings("unchecked")
        private List<CurrentDepth> buildStackPattern(List<Opcode> opcodes, BirthmarkContext context){
            Map<Label, Integer> tableMap = new HashMap<Label, Integer>();
            List<CurrentDepth> pattern = new ArrayList<CurrentDepth>();
            Map<Integer, Integer> weights = (Map<Integer, Integer>)context.getProperty("birthmarks.wsp.weights");

            int currentDepth = 0;
            Integer forwardedStatus = null;
            for(Opcode opcode: opcodes){
                if(opcode.getCategory() == Opcode.Category.TARGETER){
                    forwardedStatus = tableMap.get(((LabelOpcode)opcode).getLabel());
                }
                else{
                    WSPOpcode wspOpcode = new WSPOpcode(opcode, weights.get(opcode.getOpcode()));
                    if(forwardedStatus == null){
                        currentDepth += opcode.getAct();
                    }
                    else{
                        currentDepth = forwardedStatus + opcode.getAct();
                    }
                    forwardedStatus = null;

                    pattern.add(new CurrentDepth(currentDepth, wspOpcode));
                    if(opcode.getCategory() == Opcode.Category.BRANCH){
                        for(Label label: opcode.getLabels()){
                            tableMap.put(label, currentDepth);
                        }
                    }
                }
            }
            return pattern;
        }
    };
}
