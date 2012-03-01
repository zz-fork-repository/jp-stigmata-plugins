package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

/**
 * ハンガリアン法で，２次元配列の特定個所に引く線を表すクラス．
 * 
 * @author Haruaki Tamada
 */
public class Line implements Comparable<Line>{
    public static enum Direction{
        ROW, COLUMN;
    };

    private int index;
    private int count;
    private int crossCount;
    private Direction direction = Direction.ROW;

    /**
     * オブジェクトを作成する．
     * @param index 行もしくは列の番号を表す．．
     * @param count その行もしくは列に含まれる0の数．
     * @param crossCount その行，もしくは列に既に引かれている線の数．
     * @param direction 方向．
     */
    public Line(int index, int count, int crossCount, Direction direction){
        this.index = index;
        this.count = count;
        this.crossCount = crossCount;
        this.direction = direction;
    }

    /**
     * 位置を返す．行か，列かは，{@link #getDirection() <code>getDirection()</code>}メソッドで決定される．
     */
    public int getIndex(){
        return index;
    }

    /**
     * 0の数を返す．
     */
    public int getCount(){
        return count;
    }

    /**
     * 方向を返す．
     */
    public Direction getDirection(){
        return direction;
    }

    /**
     * このオブジェクトを比較する．
     * 0の数で比較する．ただし，0の数が同数であれば，交わっている線の数でも比較する．
     * 0が多いほど，また，交わっている線が多いほど大きいとする．
     */
    @Override
    public int compareTo(Line line){
        if(getCount() < line.getCount()){
            return -1;
        }
        else if(getCount() > line.getCount()){
            return 1;
        }
        else{
            if(crossCount < line.crossCount){
                return -1;
            }
            if(crossCount > line.crossCount){
                return 1;
            }
            return 0;
        }
    }

    /**
     * このオブジェクトの文字列表現を返す．
     */
    public String toString(){
        return String.format("[index: %d, count: %d(%d) (%s)]", getIndex(), getCount(), crossCount, getDirection());
    }
}
