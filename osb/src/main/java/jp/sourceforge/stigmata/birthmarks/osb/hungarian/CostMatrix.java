package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

/**
 * ハンガリアン法により，最適解を見つけるためのコスト行列を表すクラス．
 * 
 * @author tamada
 */
public class CostMatrix{
    private static final double THRESHOLD = 1E-6;

    private double[][] original;
    private double[][] matrix;
    private int size;
    private boolean maxFlag;

    /**
     * 与えられた２次元配列の最小和の組み合わせを求めるためのオブジェクトを作成する．
     * @param initMatrix
     */
    public CostMatrix(double[][] initMatrix){
        this(initMatrix, false);
    }

    /**
     * 与えられた２次元配列の最小和もしくは，最大和の組み合わせを求めるためのオブジェクトを作成する．
     * 
     * @param initMatrix
     * @param maxFlag trueの場合，最大和を求める．falseの場合，最小和を求める．
     */
    public CostMatrix(double[][] initMatrix, boolean maxFlag){
        size = getSize(initMatrix);
        initializeMatrix(size, initMatrix, maxFlag);
        this.maxFlag = maxFlag;

        if(maxFlag){
            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix[i].length; j++){
                    matrix[i][j] = 1d - matrix[i][j];
                }
            }
        }
    }

    /**
     * このオブジェクトが持つ２次元配列の組み合わせの最大和を求める場合，trueを返す．
     * 最小和を求める場合，falseを返す．
     * @return 最大和を求める場合，trueを返す．
     */
    public boolean isMax(){
        return maxFlag;
    }

    /**
     * このコスト配列の大きさを返す．
     * もし，与えられた２次元配列の縦と横の長さが異なっていれば，
     * 大きい方の値を返す．
     * @return このコスト配列の大きさ
     */
    public int getSize(){
        return size;
    }

    /**
     * ハンガリアン法のステップ2に相当する．
     * 与えられた行列を最小化する．
     * 
     * 各行の要素から，その行の最小値を引き，
     * その後，各列の各要素から，その列の最小値を引く．
     */
    public void minimize(){
        for(int i = 0; i < matrix.length; i++){
            double min = 1;
            for(int j = 0; j < matrix[i].length; j++){
                if(min > matrix[i][j]){
                    min = matrix[i][j];
                }
            }
            for(int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = matrix[i][j] - min;
            }
        }

        for(int i = 0; i < matrix[0].length; i++){
            double min = 1;
            for(int j = 0; j < matrix.length; j++){
                if(min > matrix[j][i]){
                    min = matrix[j][i];
                }
            }
            for(int j = 0; j < matrix.length; j++){
                matrix[j][i] = matrix[j][i] - min;
            }
        }
    }

    /**
     * 与えられた場所のセルを作成し，返す．
     */
    public Cell getCell(int i, int j){
        return new Cell(getValue(i, j), i, j);
    }

    /**
     * 与えられた場所の現在の値を返す．
     */
    public double getValue(int i, int j){
        return matrix[i][j];
    }

    public boolean isZero(int i, int j){
        return Math.abs(getValue(i, j)) < THRESHOLD;
    }

    public boolean isValidOriginal(int i, int j){
        return i < original.length && j < original[0].length;
    }

    /**
     * 与えられた場所の元の値を返す．
     */
    public double getOriginal(int i, int j){
        return original[i][j];
    }

    /**
     * 指定された場所の値を指定された値に設定する．
     */
    public void setValue(double value, int i, int j){
        matrix[i][j] = value;
    }

    /**
     * ２次元配列の初期化を行う．
     * 縦と横の長さが異なる場合，大きな方に大きさを揃える．
     */
    private void initializeMatrix(int size, double[][] initMatrix, boolean maxFlag){
        matrix = new double[size][size];
        double initValue = 1d;
        if(maxFlag){
            initValue = 0d;
        }
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = initValue;
            }
        }

        for(int i = 0; i < initMatrix.length; i++){
            System.arraycopy(initMatrix[i], 0, matrix[i], 0, initMatrix[i].length);
        }
        original = new double[initMatrix.length][initMatrix[0].length];
        for(int i = 0; i < initMatrix.length; i++){
            System.arraycopy(initMatrix[i], 0, original[i], 0, initMatrix[i].length);
        }
    }

    /**
     * ２次元配列の大きさを計算し，返す．
     * @param initMatrix
     * @return
     */
    private int getSize(double[][] initMatrix){
        int sizeX = initMatrix.length;
        int sizeY = initMatrix[0].length;
        int size = sizeX;
        if(size < sizeY){
            size = sizeY;
        }
        return size;
    }

    public synchronized String toString(boolean[] rows, boolean[] cols){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                sb.append(String.format("%.3f ", matrix[i][j]));
            }
            sb.append(rows[i]);
            sb.append(System.getProperty("line.separator"));
        }
        for(int j = 0; j < cols.length; j++){
            sb.append(String.format("%5s ", cols[j]));
        }
        return new String(sb);
    }

    public synchronized String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                sb.append(String.format("%.3f ", matrix[i][j]));
            }
            sb.append(System.getProperty("line.separator"));
        }
        return new String(sb);
    }
}
