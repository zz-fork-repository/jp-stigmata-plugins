package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import java.util.List;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public interface OpcodeExtractionFinishListener{
    public void finishExtractionOpcodes(List<Opcode> opcodes);
}
