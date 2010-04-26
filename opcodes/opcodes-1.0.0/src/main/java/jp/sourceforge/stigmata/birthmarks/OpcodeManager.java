package jp.sourceforge.stigmata.birthmarks;

/*
 * $Id$
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import jp.sourceforge.talisman.csvio.CsvLine;
import jp.sourceforge.talisman.csvio.CsvParser;

/** 
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class OpcodeManager{
    private Map<Integer, Opcode> opcodeMap = new HashMap<Integer, Opcode>();
    private static OpcodeManager manager = new OpcodeManager();

    /**
     * private constructor for singleton pattern.
     */
    private OpcodeManager(){
        try{
            URL location = OpcodeManager.class.getResource("/META-INF/bytecode.def");
            BufferedReader in = new BufferedReader(new InputStreamReader(location.openStream()));
            CsvParser parser = new CsvParser(in);
            while(parser.hasNext()){
                CsvLine line = parser.next();
                String[] values = line.getValues();
                if(values.length == 5){
                    Opcode def = new Opcode(
                        Integer.parseInt(values[0]), values[1],
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]), values[4]
                    );
                    opcodeMap.put(def.getOpcode(), def);
                }
            }
        } catch(Exception e){
            throw new InternalError(e.getMessage());
        }
    }

    public static OpcodeManager getInstance(){
        return manager;
    }

    public Opcode getOpcode(int opcode){
        return opcodeMap.get(opcode);
    }
}
