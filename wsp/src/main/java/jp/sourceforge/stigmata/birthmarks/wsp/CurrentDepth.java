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
    private WSPOpcode opcode;
    private int depth;

    public CurrentDepth(int depth, WSPOpcode opcode){
        this.depth = depth;
        this.opcode = opcode;
    }

    public int getDepth(){
        return depth;
    }

    public WSPOpcode getOpcode(){
        return opcode;
    }

    public String toString(){
        return String.format("%d:%d:%d:%d", opcode.getOpcode(), depth, opcode.getWeight(), opcode.getAct());
    }
}
