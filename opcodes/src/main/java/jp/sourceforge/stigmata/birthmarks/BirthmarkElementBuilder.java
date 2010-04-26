package jp.sourceforge.stigmata.birthmarks;

/*
 * $Id$
 */

import java.util.List;

import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;

/**
 *
 *
 * @author tamada
 * @version $Revision$
 */
public interface BirthmarkElementBuilder {
    public BirthmarkElement[] buildElements(List<Opcode> opcodes, BirthmarkContext context);
}

