package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

/**
 * ２次元配列の特定のセルを表すクラス．
 * このクラスのオブジェクトは，セルの行番号，列番号と，その値を保持する．
 * 
 * @author Haruaki Tamada
 */
public class Cell{
    public static final class EmptyCell extends Cell{
        public EmptyCell(int i, int j){
            super(Double.NaN, i, j);
        }

        public double getValue(){
            throw new IllegalStateException();
        }

        public boolean isAvailable(){
            return false;
        }

        public String toString(){
            return String.format("(%d, %d): N/A", getX(), getY());
        }
    };
    private int x, y;
    private double value;

    /**
     * 値と場所を指定してオブジェクトを作成する．
     * @param value 値
     * @param i 行番号
     * @param j 列番号
     */
    Cell(double value, int i, int j){
        this.x = i;
        this.y = j;
        this.value = value;
    }

    public boolean isAvailable(){
        return true;
    }

    /**
     * 値を返す．
     */
    public double getValue(){
        return value;
    }

    /**
     * 行番号を返す．
     */
    public int getX(){
        return x;
    }

    /**
     * 列番号を返す．
     */
    public int getY(){
        return y;
    }

    /**
     * このオブジェクトの文字列表現を返す．
     */
    public String toString(){
        return String.format("(%d, %d): %1.4g", getX(), getY(), getValue());
    }
}
