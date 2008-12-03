package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import jp.sourceforge.stigmata.BirthmarkElement;

/**
 * 
 * @author Haruaki TAMADA
 * @version $Revision$ 
 */
public class StackPatternBasedBirthmarkElement extends BirthmarkElement{
    private static final long serialVersionUID = 7965456413167854L;

    private StackPattern pattern;

    public StackPatternBasedBirthmarkElement(StackPattern pattern){
        super(pattern.toString());
        this.pattern = pattern;
    }

    public StackPattern getPattern(){
        return pattern;
    }

    public int getWeight(){
        return pattern.getWeight();
    }
}
