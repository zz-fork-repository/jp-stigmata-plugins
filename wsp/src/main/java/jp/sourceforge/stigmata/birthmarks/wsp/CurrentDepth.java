package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class CurrentDepth{
    private Opcode opcode;
    private int depth;

    public CurrentDepth(int depth, Opcode opcode){
        this.depth = depth;
        this.opcode = opcode;
    }

    public int getDepth(){
        return depth;
    }

    public Opcode getOpcode(){
        return opcode;
    }
}
