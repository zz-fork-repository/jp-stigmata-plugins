package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.Label;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class Opcode implements Serializable{
    private static final long serialVersionUID = -2349834745416345564L;

    public static enum Category{
        NORMAL, BRANCH, OBJECT, INVOKE, TARGETER,
    };
    private int opcode;
    private String name;
    private int argumentCount;
    private int act;
    private int weight;
    private Category category;
    private List<Label> labels = new ArrayList<Label>();

    public Opcode(Opcode opcode){
        this(opcode.getOpcode(), opcode.getName(), opcode.getArgumentCount(), opcode.getAct(), opcode.getCategory());
    }

    public Opcode(int opcode, String name, int argumentCount, int act, String category){
        this(opcode, name, argumentCount, act, Category.valueOf(category));
    }

    public Opcode(int opcode, String name, int argumentCount, int act, Category category){
        this.opcode = opcode;
        this.name = name;
        this.argumentCount = argumentCount;
        this.act = act;
        this.category = category;
    }

    Opcode(int opcode, String name, int argumentCount, int act, Category category, int weight){
        this(opcode, name, argumentCount, act, category);
        setWeight(weight);
    }

    public int getOpcode(){
        return opcode;
    }

    public String getName(){
        return name;
    }

    public int getArgumentCount(){
        return argumentCount;
    }

    public void addLabel(Label label){
        if(category != Category.BRANCH){
            throw new IllegalStateException("this method allows only branch category");
        }
        labels.add(label);
    }

    public void setLabels(Label[] labelArray){
        if(category != Category.BRANCH){
            throw new IllegalStateException("this method allows only branch category");
        }
        for(Label label: labelArray){
            labels.add(label);
        }
    }

    public Iterator<Label> labels(){
        return Collections.unmodifiableList(labels).iterator();
    }

    public void setAct(int act){
        if(category != Category.OBJECT && category != Category.INVOKE){
            throw new IllegalStateException("setAct can be called only object and invoke category.");
        }
        this.act = act;
    }

    public int getAct(){
        return act;
    }

    public Category getCategory(){
        return category;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }
}
