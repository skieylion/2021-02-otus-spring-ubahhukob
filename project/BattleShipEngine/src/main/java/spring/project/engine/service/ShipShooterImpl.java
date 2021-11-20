package spring.project.engine.service;

import org.springframework.stereotype.Service;
import spring.project.common.model.BattleField;
import spring.project.common.model.CellType;
import spring.project.common.model.FireResult;
import spring.project.common.model.Point;
import spring.project.engine.model.*;

import java.util.Arrays;
import java.util.List;

@Service
public class ShipShooterImpl implements ShipShooter {

    private DirectionType findDirectionShipByPoint(BattleField battleField, int x, int y) {
        DirectionType directionType = DirectionType.ZERO;
        List<CellType> cells = Arrays.asList(CellType.FULL, CellType.DAMAGE);
        if (cells.contains(battleField.getCellWithoutBorder(y - 1, x))) {
            directionType = DirectionType.UP;
        } else if (cells.contains(battleField.getCellWithoutBorder(y + 1, x))) {
            directionType = DirectionType.DOWN;
        } else if (cells.contains(battleField.getCellWithoutBorder(y, x - 1))) {
            directionType = DirectionType.LEFT;
        } else if (cells.contains(battleField.getCellWithoutBorder(y, x + 1))) {
            directionType = DirectionType.RIGHT;
        }
        return directionType;
    }

    private Point getLastPoint(BattleField battleField, Point point, DirectionType directionType) {
        List<CellType> cells = Arrays.asList(CellType.FULL, CellType.DAMAGE);
        int size = directionType == DirectionType.DOWN || directionType == DirectionType.UP ? battleField.getRows() : battleField.getColumns();
        int oldX = point.getX();
        int oldY = point.getY();
        for (int z = 1; z < size; z++) {
            int bufferX = point.getX() + directionType.getX() * z;
            int bufferY = point.getY() + directionType.getY() * z;
            if (cells.contains(battleField.getCellWithoutBorder(bufferY, bufferX))) {
                oldX = bufferX;
                oldY = bufferY;
            } else {
                break;
            }
        }
        return new Point(oldX, oldY);
    }

    private int getSizeShipByLastPointAndDirection(BattleField battleField, Point lastPoint, DirectionType directionType) {
        List<CellType> cells = Arrays.asList(CellType.FULL, CellType.DAMAGE);
        int size = directionType == DirectionType.DOWN || directionType == DirectionType.UP ? battleField.getRows() : battleField.getColumns();
        int sizeShip = 0;
        for (int z = 0; z < size; z++) {
            int bufferX = lastPoint.getX() + directionType.getX() * z;
            int bufferY = lastPoint.getY() + directionType.getY() * z;
            if (cells.contains(battleField.getCellWithoutBorder(bufferY, bufferX))) {
                sizeShip++;
            } else {
                break;
            }
        }
        return sizeShip;
    }

    private boolean isKilledShip(BattleField battleField, Ship ship) {
        for (int z = 0; z < ship.getSize(); z++) {
            int bufferX = ship.getPoint().getX() + ship.getAlign().getX() * z;
            int bufferY = ship.getPoint().getY() + ship.getAlign().getY() * z;
            if (battleField.getCellWithoutBorder(bufferY, bufferX) == CellType.FULL) {
                return false;
            }
        }
        return true;
    }

    private FireResult isWinOrKilled(BattleField battleField){
        int size = battleField.getListIndexesByCellType(CellType.FULL).size();
        if (size > 0) return FireResult.KILLED;
        else return FireResult.WIN;
    }

    private boolean checkWinOrKilled(BattleField battleField,Point point,DirectionType directionType){
        Point lastPoint = getLastPoint(battleField, point, directionType);
        int sizeShip = getSizeShipByLastPointAndDirection(battleField, lastPoint, directionType.getReverseDirection());
        return isKilledShip(battleField, new Ship(sizeShip, lastPoint, directionType.getReverseDirection()));
    }

    @Override
    public FireResult fire(BattleField battleField, Point point) {
        int x = point.getX();
        int y = point.getY();
        if (battleField.checkBorder(x, y)) {
            CellType cell = battleField.getCell(y, x);
            if (cell == CellType.FULL) {
                battleField.setCell(y, x, CellType.DAMAGE);
                DirectionType directionType = findDirectionShipByPoint(battleField, x, y);

                if (directionType == DirectionType.ZERO||checkWinOrKilled(battleField,point,directionType)) {
                    return isWinOrKilled(battleField);
                }

                return FireResult.HIT;

            } else if (cell == CellType.VOID) {
                battleField.setCell(y, x, CellType.MISS);
            }
        }

        return FireResult.MISSED;
    }
}
