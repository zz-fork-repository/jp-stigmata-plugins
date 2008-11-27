package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import org.objectweb.asm.Label;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class LabelOpcode extends Opcode{
    private static final long serialVersionUID = -346783431316464L;

    private Label label;

    public LabelOpcode(Label label){
        super(-1, "targeter", 0, 0, Category.TARGETER);
        this.label = label;
    }

    public Label getLabel(){
        return label;
    }
}
