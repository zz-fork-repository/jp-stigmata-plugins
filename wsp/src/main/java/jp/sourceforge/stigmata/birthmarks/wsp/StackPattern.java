package jp.sourceforge.stigmata.birthmarks.wsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StackPattern implements Iterable<CurrentDepth>{
    private List<CurrentDepth> depthList = new ArrayList<CurrentDepth>();

    public StackPattern(){
    }

    public int getLength(){
        return depthList.size();
    }

    public void update(int depth, Opcode opcode){
        update(new CurrentDepth(depth, opcode));
    }

    public void update(CurrentDepth depth){
        depthList.add(depth);
    }

    public Iterator<CurrentDepth> iterator(){
        return Collections.unmodifiableList(depthList).iterator();
    }
}
