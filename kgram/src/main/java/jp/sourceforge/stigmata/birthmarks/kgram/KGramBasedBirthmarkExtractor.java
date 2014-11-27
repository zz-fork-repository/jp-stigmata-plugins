package jp.sourceforge.stigmata.birthmarks.kgram;

import java.util.Iterator;

import jp.sourceforge.stigmata.Birthmark;
import jp.sourceforge.stigmata.BirthmarkContext;
import jp.sourceforge.stigmata.BirthmarkElement;
import jp.sourceforge.stigmata.ExtractionUnit;
import jp.sourceforge.stigmata.birthmarks.ASMBirthmarkExtractor;
import jp.sourceforge.stigmata.birthmarks.BirthmarkExtractVisitor;
import jp.sourceforge.stigmata.spi.BirthmarkService;
import jp.sourceforge.stigmata.utils.ArrayIterator;

import org.objectweb.asm.ClassWriter;

/**
 * @author Haruaki TAMADA
 */
public class KGramBasedBirthmarkExtractor extends ASMBirthmarkExtractor{
    private int kvalue = 4;

    public KGramBasedBirthmarkExtractor(BirthmarkService spi){
        super(spi);
    }

    public KGramBasedBirthmarkExtractor(){
        super();
    }

    @Override
    public Iterator<String> getPropertyKeys(){
        return new ArrayIterator<String>(new String[] { "KValue" });
    }

    @Override
    public void setProperty(String key, Object value){
        if(key.equalsIgnoreCase("kvalue")){
            if(value instanceof Integer){
                kvalue = ((Integer)value).intValue();
            }
            else if(value instanceof String){
                kvalue = Integer.parseInt((String)value);
            }
        }
    }

    public void setKValue(int kvalue){
        this.kvalue = kvalue;
    }

    public int getKValue(){
        return kvalue;
    }

    @Override
    public BirthmarkExtractVisitor createExtractVisitor(ClassWriter writer,
            Birthmark birthmark, BirthmarkContext context){
        KGramBasedBirthmarkExtractVisitor extractor =
            new KGramBasedBirthmarkExtractVisitor(writer, birthmark, context);
        extractor.setKValue(getKValue());
        return extractor;
    }

    @Override
    public ExtractionUnit[] getAcceptableUnits(){
        return new ExtractionUnit[] {
            ExtractionUnit.CLASS, ExtractionUnit.PACKAGE,
            ExtractionUnit.ARCHIVE,
        };
    }


    @Override
    public BirthmarkElement buildElement(String value) {
        value = value.trim();
        String[] param =
            value.substring(1, value.length() - 1).split(" *");
        KGram<Integer> kgram = new KGram<Integer>(param.length);
        for(int i = 0; i < param.length; i++){
            kgram.set(i, new Integer(param[i].trim()));
        }
        return new KGramBasedBirthmarkElement<Integer>(kgram);
    }
}
