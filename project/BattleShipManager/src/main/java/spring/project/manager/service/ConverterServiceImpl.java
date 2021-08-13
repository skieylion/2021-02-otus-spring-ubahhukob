package spring.project.manager.service;

import org.springframework.stereotype.Service;
import spring.project.engine.model.BattleField;
import spring.project.engine.model.CellType;
import spring.project.engine.model.Point;
import spring.project.manager.model.BattleCell;
import spring.project.manager.model.BattleFieldVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ConverterServiceImpl implements ConverterService {
    @Override
    public BattleField convert(BattleFieldVO battleFieldVO) {
        int row=battleFieldVO.getSizeRow();
        int column=battleFieldVO.getSizeColumn();
        BattleField battleField=new BattleField(row,column);
        battleFieldVO.getCells().forEach(battleCell -> {
            int y=battleCell.getPoint().getY();
            int x=battleCell.getPoint().getX();
            CellType cellType=battleCell.getCell();
            battleField.setCell(y,x,cellType);
        });
        return battleField;
    }

    @Override
    public BattleFieldVO convert(BattleField battleField) {
        BattleFieldVO battleFieldVO=new BattleFieldVO();
        battleFieldVO.setSizeRow(battleField.getRows());
        battleFieldVO.setSizeColumn(battleField.getColumns());
        List<BattleCell> cells=new ArrayList<>();
        for(int i=0;i<battleField.getRows();i++){
            for(int j=0;j<battleField.getColumns();j++){
                BattleCell battleCell=new BattleCell();
                CellType cellType=battleField.getCell(i,j);
                battleCell.setPoint(new Point(j,i));
                battleCell.setCell(cellType);
                cells.add(battleCell);
            }
        }
        battleFieldVO.setCells(cells);

        return battleFieldVO;
    }

    @Override
    public BattleField convertEnemyField(BattleField battleField) {
        BattleField battleFieldEnemy=new BattleField(battleField.getRows(),battleField.getColumns());
        battleFieldEnemy.setField(battleField.getField());
        for(int i=0;i<battleFieldEnemy.getRows();i++){
            for (int j=0;j<battleFieldEnemy.getColumns();j++){
                CellType cellType=battleFieldEnemy.getCell(i,j);
                List<CellType> array= Arrays.asList(CellType.DAMAGE,CellType.VOID,CellType.MISS);
                if(!array.contains(cellType)){
                    battleFieldEnemy.setCell(i,j,CellType.VOID);
                }
            }
        }
        return battleFieldEnemy;
    }
}
