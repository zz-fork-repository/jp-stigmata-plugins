package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import jp.sourceforge.stigmata.plugins.Opcode;

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

    public String toString(){
        return String.format("%d:%d:%d:%d", opcode.getOpcode(), depth, opcode.getWeight(), opcode.getAct());
    }
}
