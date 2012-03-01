package jp.sourceforge.stigmata.birthmarks.osb;

import jp.sourceforge.stigmata.cflib.Opcode;

/**
 * "opcode"と"そのopcode実行時のオペランドスタックの深さ"を対応付けするクラス
 *
 * @author Fumiya Iwama
 * @author Ryouta Obatake
 * @author Akinori Kataoka
 * @author Takayuki Kitano
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

    @Override
    public String toString(){
        return String.format(opcode.getName() + "(" + depth+ ")" );//出力部分に影響
    }
}
