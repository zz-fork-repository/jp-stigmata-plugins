package jp.sourceforge.stigmata.birthmarks.osb;

/**
 * LCS (Longest Common Subsequence; 最長共通部分列)の計算機．
 * 与えられた型 T の配列2つから最長共通部分列を見つけ，長さを返す．
 * 
 * @author Haruaki Tamada
 */
public class LCSCalculator<T>{
    /**
     * elementA と elementB の最長共通部分列の長さを返す．
     * 配列の要素はnullを許容する．両方の要素がnullの場合は一致するとみなし，
     * 片一方の要素のみがnullの場合は，一致しないとする．
     * @param elementA 最長共通部分列の長さを計算する集合1
     * @param elementB 
     * @return 最長共通部分列の長さ
     * @exception NullPointerException 与えらえた配列のどちらか，もしくは両方が null の場合．
     */
    public int calculate(T[] elementA, T[] elementB){
        if(elementA == null || elementB == null){
            throw new NullPointerException();
        }
        int[][] table = new int[elementA.length + 1][elementB.length + 1];
        for(int i = 0; i <= elementA.length; i++){
            for(int j = 0; j <= elementB.length; j++){
                if(i == 0 || j == 0){
                    table[i][j] = 0;
                }
                else if((elementA[i - 1] == null && elementB[j - 1] == null) ||
                        elementA[i - 1] != null && elementA[i - 1].equals(elementB[j - 1])){
                    table[i][j] = max(table[i - 1][j - 1] + 1, table[i - 1][j], table[i][j - 1]);
                }
                else{
                    table[i][j] = max(table[i - 1][j - 1], table[i - 1][j], table[i][j - 1]);
                }
            }
        }
        return table[elementA.length][elementB.length];
    }

    /**
     * 与えられた3つの整数値のうち，一番大きな値を返す．
     * 全て正の数が与えらえるものとする．
     */
    private int max(int value1, int value2, int value3){
        assert value1 >= 0;
        assert value2 >= 0;
        assert value3 >= 0;

        int max = value1;
        if(max < value2){
            max = value2;
        }
        if(max < value3){
            max = value3;
        }
        return max;
    }
}
