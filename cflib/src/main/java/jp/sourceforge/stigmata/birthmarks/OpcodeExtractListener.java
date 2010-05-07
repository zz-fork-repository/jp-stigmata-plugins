package jp.sourceforge.stigmata.birthmarks;

/*
 * $Id$
 */

import java.util.List;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public interface OpcodeExtractListener{
    public void extractOpcodesFinished(List<Opcode> opcodes);
}
