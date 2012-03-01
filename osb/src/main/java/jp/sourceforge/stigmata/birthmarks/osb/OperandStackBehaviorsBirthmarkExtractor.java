package jp.sourceforge.stigmata.birthmarks.osb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.ExtractionUnit;
import jp.sourceforge.stigmata.birthmarks.ASMBirthmarkExtractor;
import jp.sourceforge.stigmata.birthmarks.BirthmarkExtractVisitor;
import jp.sourceforge.stigmata.cflib.BirthmarkElementBuilder;
import jp.sourceforge.stigmata.cflib.LabelOpcode;
import jp.sourceforge.stigmata.cflib.Opcode;
import jp.sourceforge.stigmata.cflib.OpcodeExtractVisitor;
import jp.sourceforge.stigmata.cflib.OpcodeManager;
import jp.sourceforge.stigmata.spi.BirthmarkService;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

/**
 * Operand Stack Behavior を抽出するためのクラス．
 *
 * @author Fumiya Iwama
 * @author Ryouta Obatake
 * @author Akinori Kataoka
 * @author Takayuki Kitano
 */
public class OperandStackBehaviorsBirthmarkExtractor extends ASMBirthmarkExtractor{
	
    public OperandStackBehaviorsBirthmarkExtractor(BirthmarkService service){
        super(service);
    }

    @Override
    public BirthmarkExtractVisitor createExtractVisitor(
            ClassWriter writer, Birthmark birthmark,
	    BirthmarkContext context){
		
        return new OpcodeExtractVisitor(writer, birthmark, context, new OSBBirthmarkElementBuilder());
    }

    @Override
    public ExtractionUnit[] getAcceptableUnits(){
        return new ExtractionUnit[] {
            ExtractionUnit.CLASS,
        };
    }
    
    private static class OSBBirthmarkElementBuilder implements BirthmarkElementBuilder{
        @Override
        public BirthmarkElement[] buildElements(List<Opcode> opcodes,
                                                BirthmarkContext context){
            List<CurrentDepth> pattern = buildStackPattern(opcodes, context);
            List<BirthmarkElement> elements = new ArrayList<BirthmarkElement>();

            List<Opcode> subPattern = new ArrayList<Opcode>();
            for(CurrentDepth depth: pattern){
                subPattern.add(depth.getOpcode());
                if(depth.getDepth() == 0){//深さが0になるごとに、behaviorを区切る
                    elements.add(new OperandStackBehaviorsBirthmarkElement(
                        subPattern.toArray(new Opcode[subPattern.size()])
                    ));
                    subPattern.clear();
                }
            }

            if(subPattern.size() != 0){//これで無駄な空白を除去
                elements.add(new OperandStackBehaviorsBirthmarkElement(
                    subPattern.toArray(new Opcode[subPattern.size()])
                ));
            }

            return elements.toArray(new BirthmarkElement[elements.size()]);
        }

        private List<CurrentDepth> buildStackPattern(List<Opcode> opcodes, BirthmarkContext context){
            Map<Label, Integer> tableMap = new HashMap<Label, Integer>();
            List<CurrentDepth> pattern = new ArrayList<CurrentDepth>();

            int currentDepth = 0;
            Integer forwardedStatus = null;
            for(Opcode opcode: opcodes){
                if(opcode.getCategory() == Opcode.Category.TARGETER){
                    forwardedStatus =
                        tableMap.get(((LabelOpcode)opcode).getLabel());
                }
                else{
                    if(forwardedStatus == null){
                        currentDepth += opcode.getAct();
                    }
                    else{
                        currentDepth = forwardedStatus + opcode.getAct();
                    }
                    forwardedStatus = null;

                    pattern.add(new CurrentDepth(currentDepth, opcode));
                    if(opcode.getCategory() == Opcode.Category.BRANCH){
                        for(Label label: opcode.getLabels()){
                            tableMap.put(label, currentDepth);
                        }
                    }
                }
            }
            return pattern;
        }
    }
    
    @Override
    public BirthmarkElement buildElement(String value){
        OpcodeManager manager = OpcodeManager.getInstance();
        String[] elements = value.split(", ");
        Opcode[] opcodes = new Opcode[elements.length];
        for(int i = 0; i < opcodes.length; i++){
            opcodes[i] = manager.getOpcode(Integer.parseInt(elements[i]));
        }
        return new OperandStackBehaviorsBirthmarkElement(opcodes);
    }; 
}
