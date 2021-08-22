package spring.project.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleField {
    private List<BattleFieldRaw> field;
    private int rows;
    private int columns;

    public BattleField(){}
    public BattleField(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        field = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            field.add(new BattleFieldRaw());
            for (int j = 0; j < columns; j++) {
                field.get(i).add(CellType.VOID);
            }
        }
    }
    public CellType getCell(int row, int column) {
        return field.get(row).get(column);
    }
    public boolean checkBorder(int x, int y) {
        if (x >= 0 && x < getColumns() && y >= 0 && y < getRows()) {
            return true;
        }
        return false;
    }
    public CellType getCellWithoutBorder(int row, int column) {
        if(checkBorder(column,row)) {
            return field.get(row).get(column);
        }
        return CellType.INFINITY;
    }
    public void setCell(int row, int column, CellType cell) {
        field.get(row).set(column, cell);
    }
    public List<Integer> getListIndexesByCellType(CellType cellType) {
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
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                CellType cellType = getCell(i, j);
                buff.append(cellType.toString());
            }
            buff.append("\n");
        }
        return buff.toString();
    }
}
