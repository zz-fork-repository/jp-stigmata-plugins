package jp.sourceforge.stigmata.birthmarks.osb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.cflib.Opcode;

/**
 * Operand Stack Behavior バースマークの要素を表すクラス．
 *
 * @author Fumiya Iwama
 * @author Ryouta Obatake
 * @author Akinori Kataoka
 * @author Takayuki Kitano
 */
public class OperandStackBehaviorsBirthmarkElement extends BirthmarkElement implements Iterable<Opcode>{
    private static final long serialVersionUID = 7965456413167854L;

    private static String getStringRepresentation(Opcode[] opcodes){//出力部分
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < opcodes.length; i++){
            if(i != 0){
                builder.append(", ");
            }
            builder.append(opcodes[i].getOpcode());
        }
        return new String(builder);
    }

    private LCSCalculator<Opcode.Category> calculator = new LCSCalculator<Opcode.Category>();
    private transient Opcode.Category[] elements;

    private List<Opcode> list = new ArrayList<Opcode>();

    public OperandStackBehaviorsBirthmarkElement(Opcode[] opcodes){
        super(getStringRepresentation(opcodes));
        for(Opcode opcode: opcodes){
            list.add(opcode);
        }
        elements = null;
    }

    private Opcode.Category[] getCategories(){
        if(elements == null){
            Opcode.Category[] categories = new Opcode.Category[getLength()];

            int index = 0;
            for(Opcode opcode: this){
                categories[index] = opcode.getCategory();
                index++;
            }
            this.elements = categories;
        }
        return elements;
    }
    
    public Opcode.Category getCategory(int index){
        return getOpcode(index).getCategory();
    }

    public int getLength(){
        return list.size();
    }

    public Opcode getOpcode(int index){
        return list.get(index);
    }

    /**
     * calculate similarity between two birthmark elements.
     * 
     */
    public double getSimilarity(OperandStackBehaviorsBirthmarkElement element){
        Opcode.Category[] elementA = getCategories();
        Opcode.Category[] elementB = element.getCategories();

        double lcs = calculator.calculate(elementA, elementB);
        return lcs / (elementA.length + elementB.length - lcs);                
    }

    @Override
    public Iterator<Opcode> iterator(){
        return Collections.unmodifiableList(list).iterator();
    }
}