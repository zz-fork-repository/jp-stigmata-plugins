package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.birthmarks.AbstractBirthmarkPreprocessor;
import jp.sourceforge.stigmata.digger.ClassFileArchive;
import jp.sourceforge.stigmata.digger.ClassFileEntry;
import jp.sourceforge.stigmata.spi.BirthmarkSpi;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * 
 * @author Haruaki Tamada
 * @version $Revision$
 */
public class OpcodeWeightCalculatePreprocessor extends AbstractBirthmarkPreprocessor{
    public OpcodeWeightCalculatePreprocessor(BirthmarkSpi spi){
        super(spi);
    }

    @Override
    public void preprocess(ClassFileArchive[] targets, BirthmarkContext context){
        Map<Integer, Integer> targetMap = new HashMap<Integer, Integer>();

        int classCount = readOpcodes(targets, targetMap);

        Map<Integer, Integer> weights = new HashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer> entry: targetMap.entrySet()){
            int opcode = entry.getKey();
            Integer count = entry.getValue();
            int c = 0;
            if(count != null){
                c = count;
            }

            weights.put(opcode, (int)Math.log(classCount / c));
        }

        context.putProperty("birthmarks.wsp.weights", weights);
    }

    private int readOpcodes(ClassFileArchive[] targets, Map<Integer, Integer> targetMap){
        int count = 0;
        for(ClassFileArchive archive: targets){
            for(ClassFileEntry entry: archive){
                count++;
                final List<Opcode> opcodes = new ArrayList<Opcode>();
                try{
                    InputStream in = entry.getLocation().openStream();

                    ClassReader reader = new ClassReader(in);
                    ClassWriter writer = new ClassWriter(false);
                    ClassAdapter opcodeExtractVisitor = new ClassAdapter(writer){
                        @Override
                        public MethodVisitor visitMethod(int arg0, String arg1, String arg2, String arg3, String[] arg4){
                            OpcodeExtractionMethodVisitor visitor =
                                new OpcodeExtractionMethodVisitor(super.visitMethod(arg0, arg1, arg2, arg3, arg4), opcodes);
                            return visitor;
                        }
                    };
                    reader.accept(opcodeExtractVisitor, false);

                    for(Opcode opcode: opcodes){
                        if(opcode.getCategory() != Opcode.Category.TARGETER){
                            Integer i = targetMap.get(opcode.getOpcode());
                            if(i == null){
                                i = 0;
                            }
                            i = i + 1;
                            targetMap.put(opcode.getOpcode(), i);
                        }
                    }

                } catch(IOException e){
                }
            }
        }
        return count;
    }
}
