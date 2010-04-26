package jp.sourceforge.stigmata.birthmarks.wsp;

/*
 * $Id$
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.birthmarks.AbstractBirthmarkPreprocessor;
import jp.sourceforge.stigmata.birthmarks.Opcode;
import jp.sourceforge.stigmata.birthmarks.OpcodeExtractMethodVisitor;
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

        int classCount = 0;
        for(ClassFileArchive archive: targets){
            classCount += readOpcodes(archive, targetMap);
        }

        Map<Integer, Integer> weights = new HashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer> entry: targetMap.entrySet()){
            int opcode = entry.getKey();
            Integer count = entry.getValue();
            int c = 0;
            if(count != null){
                c = count;
            }

            weights.put(opcode, (int)Math.round(Math.log(classCount / c)));
        }

        context.putProperty("birthmarks.wsp.weights", weights);
    }

    private int readOpcodes(ClassFileArchive archive, Map<Integer, Integer> targetMap){
        int count = 0;
        for(ClassFileEntry entry: archive){
            count++;
            final List<Opcode> opcodes = new ArrayList<Opcode>();
            try{
                InputStream in = entry.getLocation().openStream();

                ClassReader reader = new ClassReader(in);
                ClassWriter writer = new ClassWriter(0);
                ClassAdapter opcodeExtractVisitor = new ClassAdapter(writer){
                    @Override
                    public MethodVisitor visitMethod(int arg0, String arg1, String arg2, String arg3, String[] arg4){
                        OpcodeExtractMethodVisitor visitor =
                            new OpcodeExtractMethodVisitor(super.visitMethod(arg0, arg1, arg2, arg3, arg4), opcodes);
                        return visitor;
                    }
                };
                reader.accept(opcodeExtractVisitor, 0);

                Set<Integer> set = new HashSet<Integer>();
                for(Opcode opcode: opcodes){
                    if(opcode.getCategory() != Opcode.Category.TARGETER){
                        set.add(opcode.getOpcode());
                    }
                }

                for(Integer i: set){
                    Integer v = targetMap.get(i);
                    if(v == null){
                        v = 0;
                    }
                    v = v + 1;
                    targetMap.put(i, v);
                }
            } catch(IOException e){
            }
        }
        return count;
    }
}
