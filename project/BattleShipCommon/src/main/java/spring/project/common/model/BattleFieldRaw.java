package spring.project.common.model;

import java.util.ArrayList;
import java.util.List;

public class BattleFieldRaw {
    private final List<CellType> cellTypes = new ArrayList<>();

    public void add(CellType cellType){
        cellTypes.add(cellType);
    }
    public CellType get(int index){
        return cellTypes.get(index);
    }
    public void set(int index,CellType cellType){
        cellTypes.set(index,cellType);
    }

}
