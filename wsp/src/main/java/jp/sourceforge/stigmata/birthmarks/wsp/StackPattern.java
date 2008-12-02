package jp.sourceforge.stigmata.birthmarks.wsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StackPattern implements Iterable<CurrentDepth>{
    private List<CurrentDepth> depthList = new ArrayList<CurrentDepth>();
    private int weight = -1;

    public StackPattern(){
    }

    public int getLength(){
        return depthList.size();
    }

    public void update(int depth, Opcode opcode){
        update(new CurrentDepth(depth, opcode));
        weight = -1;
    }

    public void update(CurrentDepth depth){
        depthList.add(depth);
        weight = -1;
    }

    public Iterator<CurrentDepth> iterator(){
        return Collections.unmodifiableList(depthList).iterator();
    }

    public CurrentDepth getDepth(int index){
        return depthList.get(index);
    }

    public int getWeight(){
        if(weight < 0){
            int w = 0;
            for(CurrentDepth depth: this){
                w += depth.getOpcode().getWeight();
            }
            this.weight = w;
        }
        return weight;
    }

    public int calculateWeight(StackPattern pattern){
        int[][] matrix = new int[pattern.getLength() + 1][getLength() + 1];

        for(int i = 0; i <= pattern.getLength(); i++){
            for(int j = 0; j <= getLength(); j++){
                if(i == 0 || j == 0){
                    matrix[i][j] = 0;
                }
                else if(pattern.getDepth(i - 1).getOpcode().getOpcode() == getDepth(j - 1).getOpcode().getOpcode()){
                    matrix[i][j] = (int)(matrix[i - 1][j - 1] + getDepth(j - 1).getOpcode().getWeight());
                }
                else{
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
                }
            }
        }

        int max = 0;
        int last = pattern.getLength();
        for(int i = 0; i < matrix[last].length; i++){
            if(matrix[last][i] > max){
                max = matrix[last][i];
            }
        }
        return max;
    }
}
