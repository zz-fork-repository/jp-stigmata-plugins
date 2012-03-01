package jp.sourceforge.stigmata.birthmarks.osb.hungarian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * ハンガリアン法により，最小和の組み合わせを選択する計算機．
 * ハンガリアン法は以下の手順により行われる．
 * </p>
 * <ul>
 *   <li>与えられた2次元配列の各列から，その列の最小値を引く．</li>
 *   <li>与えられた2次元配列の各行から，その行の最小値を引く．</li>
 *   <li>各行各列から，1つずつ0を選択できれば，その部分が最適な組み合わせであるため，そこで終了する．</li>
 *   <li>そうでない場合は，0の生成作業へと移る
 *    <ul>
 *      <li>1度に多くの0が消えるよう線を入れる．</li>
 *      <li>線で消されなかったセルの最小値<em>min</em>を求める．</li>
 *      <li>線で消されなかった全てのセルから，求めた最小値<em>min</em>を減算する．</li>
 *    </ul>
 *   </li>
 *   <li>最適な組み合わせを選択する．選択できれば終了．選択できなければ，もう一度0の生成作業を行う．</li>
 * </ul>
 */
public class HungarianMethod{
    private CostMatrix matrix;
    private List<Cell> solutions = new ArrayList<Cell>();

    public HungarianMethod(CostMatrix matrix){
        this.matrix = matrix;
    }

    public synchronized Cell[] getSolutions(){
        Collections.sort(solutions, new Comparator<Cell>(){
            @Override
            public int compare(Cell arg0, Cell arg1){
                if(arg0.getX() < arg1.getX()){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        });
        return solutions.toArray(new Cell[solutions.size()]);
    }

    public synchronized Line[] getLines(){
        boolean[][] linedMatrix = new boolean[matrix.getSize()][matrix.getSize()];
        List<Line> lines = getLines(matrix, linedMatrix);
    
        return lines.toArray(new Line[lines.size()]);
    }

    /**
     * ハンガリアン法を解く．
     * @return 最大和もしくは最小和．
     * @see CostMatrix#isMax
     */
    public double solve(){
        // step.1  
        matrix.minimize();

        boolean[][] linedMatrix = new boolean[matrix.getSize()][matrix.getSize()];
        List<Line> lines = getLines(matrix, linedMatrix);
        while(lines.size() < matrix.getSize()){
            moreMinimize(matrix, lines, linedMatrix);
            resetBooleanMatrix(linedMatrix);
            lines = getLines(matrix, linedMatrix);
        }
        double solution = parseSolutions(matrix, lines);

        return solution;
    }

    private void resetBooleanMatrix(boolean[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = false;
            }
        }
    }

    private double parseSolutions(CostMatrix matrix, List<Line> lines){
        double solution = 0d;

        boolean[][] solutionMatrix = new boolean[matrix.getSize()][matrix.getSize()];
        solutions.clear();
        while(solutions.size() < matrix.getSize()){
            Cell cell = getSolution(matrix, solutionMatrix);
            for(int i = 0; i < matrix.getSize(); i++){
                solutionMatrix[cell.getX()][i] = true;
                solutionMatrix[i][cell.getY()] = true;
            }
            if(cell.isAvailable()){
                solution = solution + cell.getValue();
            }
            solutions.add(cell);
        }
        for(int i = solutions.size() - 1; i >= 0; i--){
            if(!solutions.get(i).isAvailable()){
                solutions.remove(i);
            }
        }
        return solution;
    }

    private Cell getSolution(CostMatrix matrix, boolean[][] solutionMatrix){
        for(int i = 0; i < matrix.getSize(); i++){
            int count = 0;
            int lastIndex = -1;
            for(int j = 0; j < matrix.getSize(); j++){
                if(matrix.isZero(i, j) && !solutionMatrix[i][j]){
                    count++;
                    lastIndex = j;
                }
            }
            if(count == 1){
                Cell cell;
                if(matrix.isValidOriginal(i, lastIndex)){
                    cell = new Cell(matrix.getOriginal(i, lastIndex), i, lastIndex);
                }
                else{
                    cell = new Cell.EmptyCell(i, lastIndex);
                }
                return cell;
            }
        }
        for(int j = 0; j < matrix.getSize(); j++){
            int count = 0;
            int lastIndex = -1;
            for(int i = 0; i < matrix.getSize(); i++){
                if(matrix.isZero(i, j) && !solutionMatrix[i][j]){
                    count++;
                    lastIndex = i;
                }
            }
            if(count == 1){
                Cell cell;
                if(matrix.isValidOriginal(lastIndex, j)){
                    cell = new Cell(matrix.getOriginal(lastIndex, j), lastIndex, j);
                }
                else{
                    cell = new Cell.EmptyCell(lastIndex, j);
                }
                return cell;
            }
        }
        for(int i = 0; i < matrix.getSize(); i++){
            for(int j = 0; j < matrix.getSize(); j++){
                if(matrix.isZero(i, j) && !solutionMatrix[i][j]){
                    Cell cell;
                    if(matrix.isValidOriginal(i, j)){
                        cell = new Cell(matrix.getOriginal(i, j), i, j);
                    }
                    else{
                        cell = new Cell.EmptyCell(i, j);
                    }
                    return cell;
                }
            }
        }
        return null;
    }

    private List<Line> getLines(CostMatrix matrix, boolean[][] linedMatrix){
        List<Line> lines = new ArrayList<Line>();
        List<Line> candidate;

        while((candidate = getCandidateLines(matrix, linedMatrix)).size() > 0){
            Line line = candidate.get(candidate.size() - 1);
            lineOut(matrix, linedMatrix, line);
            lines.add(line);
        }
        return lines;
    }

    /**
     * 対象の２次元配列から，全ての線を取り出す．
     * ただし，線には必ず0を含み，その0は既に引かれた線で選択されていないものとする．
     * また，選択した線は，線に含まれる0の数，交差する線の数によりソートされる．
     */
    private List<Line> getCandidateLines(CostMatrix matrix, boolean[][] linedMatrix){
        List<Line> candidateLines = new ArrayList<Line>();
        for(int i = 0; i < matrix.getSize(); i++){
            int count = 0;
            int crossCount = 0;
            for(int j = 0; j < matrix.getSize(); j++){
                if(matrix.isZero(i, j) && !linedMatrix[i][j]){
                    count++;
                }
                if(linedMatrix[i][j]){
                    crossCount++;
                }
            }
            if(count > 0){
                candidateLines.add(new Line(i, count, crossCount, Line.Direction.ROW));
            }
        }
        for(int j = 0; j < matrix.getSize(); j++){
            int count = 0;
            int crossCount = 0;
            for(int i = 0; i < matrix.getSize(); i++){
                if(matrix.isZero(i, j) && !linedMatrix[i][j]){
                    count++;
                }
                if(linedMatrix[i][j]){
                    crossCount++;
                }
            }
            if(count > 0){
                candidateLines.add(new Line(j, count, crossCount, Line.Direction.COLUMN));
            }
        }
        Collections.sort(candidateLines);

        return candidateLines;
    }

    private void lineOut(CostMatrix matrix, boolean[][] linedMatrix, Line line){
        if(line.getDirection() == Line.Direction.COLUMN){
            for(int i = 0; i < linedMatrix.length; i++){
                linedMatrix[i][line.getIndex()] = true;
            }
        }
        else{
            for(int i = 0; i < linedMatrix[0].length; i++){
                linedMatrix[line.getIndex()][i] = true;
            }
        }
    }

    private List<Cell> getCrossedLineCell(CostMatrix matrix, List<Line> lines){
        List<Cell> cross = new ArrayList<Cell>();
        for(int i = 0; i < lines.size() - 1; i++){
            Line line1 = lines.get(i);
            for(int j = 1; j < lines.size(); j++){
                Line line2 = lines.get(j);
                if(line1.getDirection() != line2.getDirection()){
                    int row = line1.getIndex();
                    int column = line2.getIndex();
                    if(line1.getDirection() == Line.Direction.COLUMN){
                        row = line2.getIndex();
                        column = line1.getIndex();
                    }
                    cross.add(matrix.getCell(row, column));
                }
            }
        }
        return cross;
    }

    public synchronized void reduceMatrix(Line[] lineArray){
        List<Line> lines = new ArrayList<Line>();
        boolean[][] linedMatrix = new boolean[matrix.getSize()][matrix.getSize()];
        for(Line line: lineArray){
            lines.add(line);
            for(int i = 0; i < linedMatrix.length; i++){
                if(line.getDirection() == Line.Direction.COLUMN){
                    linedMatrix[i][line.getIndex()] = true;
                }
                else{
                    linedMatrix[line.getIndex()][i] = true;
                }
            }
        }
        moreMinimize(matrix, lines, linedMatrix);
    }

    /**
     * ハンガリアン法のステップ3, 4に相当する．
     * 線の引かれていない箇所から，最小値を引く．
     */
    private void moreMinimize(CostMatrix matrix, List<Line> lines, boolean[][] linedMatrix){
        List<Cell> cross = getCrossedLineCell(matrix, lines);

        // 線が引かれていない部分から最小値を選択する．
        double min = 1;
        for(int i = 0; i < matrix.getSize(); i++){
            for(int j = 0; j < matrix.getSize(); j++){
                if(!linedMatrix[i][j] && min > matrix.getValue(i, j)){
                    min = matrix.getValue(i, j);
                }
            }
        }

        // 線が引かれていない箇所から，求めた最小値を減算する．
        for(int i = 0; i < matrix.getSize(); i++){
            for(int j = 0; j < matrix.getSize(); j++){
                if(!linedMatrix[i][j]){
                    matrix.setValue(matrix.getValue(i, j) - min, i, j);
                }
            }
        }
        // 線が交わっている箇所は減算した値を足す．
        for(Cell cell: cross){
            matrix.setValue(
                matrix.getValue(cell.getX(), cell.getY()) + min,
                cell.getX(), cell.getY()
            );
        }
    }
}