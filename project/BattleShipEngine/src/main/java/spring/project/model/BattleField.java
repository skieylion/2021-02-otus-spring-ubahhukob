package spring.project.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class BattleField {
    private List<List<CellType>> field;

    private final int rows;
    private final int columns;

    public BattleField(int sizeRow,int sizeCol){
        rows=sizeRow;
        columns=sizeCol;
        field=new ArrayList<>();
        for(int i=0;i<rows;i++){
            field.add(new ArrayList<>());
            for (int j=0;j<columns;j++){
                field.get(i).add(CellType.VOID);
            }
        }
    }

    public CellType getCell(int row, int column){
        return field.get(row).get(column);
    }
    public void setCell(int row, int column,CellType cell){
        field.get(row).set(column,cell);
    }
    public List<Integer> getListIndexesByCellType(CellType cellType){
        List<Integer> cells = new ArrayList<>();
        int sizeRow = this.getRows();
        int sizeColumn = this.getColumns();
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeColumn; j++) {
                if (cellType.equals(this.getCell(i, j))) {
                    int index = i * sizeRow + j;
                    cells.add(index);
                }
            }
        }
        return cells;
    }


    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        for(int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                CellType cellType=getCell(i,j);
                buff.append(cellType.toString());
            }
            buff.append("\n");
        }
        return buff.toString();
    }
}
