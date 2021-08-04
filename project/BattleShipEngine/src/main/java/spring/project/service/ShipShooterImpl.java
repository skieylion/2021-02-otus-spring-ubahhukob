package spring.project.service;

import org.springframework.stereotype.Service;
import spring.project.model.BattleField;
import spring.project.model.CellType;
import spring.project.model.FireResult;
import spring.project.model.Point;

@Service
public class ShipShooterImpl implements ShipShooter{


    @Override
    public FireResult fire(BattleField battleField, Point point) {
        int x=point.getX();
        int y=point.getY();
        int rowSize=battleField.getRows();
        int colSize=battleField.getRows();

        if(x>=0&&x<colSize&&y>=0&&y<rowSize){
            CellType cell=battleField.getCell(y,x);
            if (cell == CellType.FULL) {
                battleField.setCell(y,x,CellType.DAMAGE);
                return FireResult.HIT;
            } else if(cell == CellType.VOID) {
                battleField.setCell(y,x,CellType.MISS);
            }
        }

        return FireResult.MISSED;
    }
}
