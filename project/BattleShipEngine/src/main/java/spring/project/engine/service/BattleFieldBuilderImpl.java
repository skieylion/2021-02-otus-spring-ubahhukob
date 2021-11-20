package spring.project.engine.service;

import org.springframework.stereotype.Service;
import spring.project.common.model.BattleField;
import spring.project.common.model.CellType;
import spring.project.common.model.Point;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BattleFieldBuilderImpl implements BattleFieldBuilder {

    private List<Integer> getVoidCellsFromBattleField(BattleField battleField) {
        List<Integer> cells = new ArrayList<>();
        int sizeRow = battleField.getRows();
        int sizeColumn = battleField.getColumns();
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeColumn; j++) {
                if (CellType.VOID.equals(battleField.getCell(i, j))) {
                    int index = i * sizeRow + j;
                    cells.add(index);
                }
            }
        }
        return cells;
    }

    private void addShipToBattleField(BattleField battleField, Ship ship) {
        int row = ship.getPoint().getY();
        int column = ship.getPoint().getX();

        int posRow = ship.getAlign().getY();
        int posColumn = ship.getAlign().getX();

        for (int z = 0; z < ship.getSize(); z++) {
            int x = column + posColumn * z;
            int y = row + posRow * z;
            battleField.setCell(y, x, CellType.FULL);
        }
    }

    private Boolean checkArea(BattleField battleField, int x, int y, int oldX, int oldY) {
        int sRow = battleField.getRows();
        int sCol = battleField.getColumns();
        if (x >= 0 && x < sCol && y >= 0 && y < sRow) {
            var ways = Arrays.asList(-1, 0, 1);
            for (int i : ways) {
                for (int j : ways) {
                    int bufferY = y + i;
                    int bufferX = x + j;
                    if (bufferX == oldX && bufferY == oldY) continue;
                    if (!(bufferX >= 0 && bufferX < sCol && bufferY >= 0 && bufferY < sRow)) continue;
                    if (CellType.FULL.equals(battleField.getCell(bufferY, bufferX))) return false;
                }
            }
            return true;
        }
        return false;
    }

    private Boolean checkPositionOfShip(BattleField battleField, Ship ship) {
        int row = ship.getPoint().getY();
        int column = ship.getPoint().getX();

        int posRow = ship.getAlign().getY();
        int posColumn = ship.getAlign().getX();

        int oldX = -2;
        int oldY = -2;


        for (int z = 0; z < ship.getSize(); z++) {
            int x = column + posColumn * z;
            int y = row + posRow * z;
            if (!checkArea(battleField, x, y, oldX, oldY)) return false;
            oldX = x;
            oldY = y;
        }

        return true;
    }

    private DirectionType getRandomDirectionForShipByOneIteration(BattleField battleField, int sizeShip, Point point) {
        List<DirectionType> directionTypes = Arrays.asList(DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT);
        Collections.shuffle(directionTypes);

        for (DirectionType value : directionTypes) {
            Ship ship = new Ship(sizeShip, point, value);
            if (checkPositionOfShip(battleField, ship)) return value;
        }
        return null;
    }

    private Ship getRandomPositionForShip(BattleField battleField, int sizeShip, int sizeRow) throws PositionShipNotFoundException {

        //извлекаем свободные ячейки
        List<Integer> cells = battleField.getListIndexesByCellType(CellType.VOID);

        //перемешиваем коллекцию
        Collections.shuffle(cells);

        //подбираем позицию для корабля
        for (int lastIndex : cells) {
            //извлекаем координаты
            int jIndex = lastIndex % sizeRow;
            int iIndex = (lastIndex - jIndex) / sizeRow;
            Point point = new Point(jIndex, iIndex);
            //рандомно подбираем подходяую позицию для корабля
            DirectionType pos = getRandomDirectionForShipByOneIteration(battleField, sizeShip, point);
            if (pos != null) {
                return new Ship(sizeShip, point, pos);
            }
        }
        throw new PositionShipNotFoundException();
    }

    private void addRandomShipToBattleField(BattleField battleField, int sizeShip, int countShip, int sizeRow) throws PositionShipNotFoundException {
        for (int i = 0; i < countShip; i++) {
            Ship ship = getRandomPositionForShip(battleField, sizeShip, sizeRow);
            addShipToBattleField(battleField, ship);
        }
    }

    @Override
    public BattleField create(int row, int column) {
        return new BattleField(row, column);
    }

    @Override
    public void addShip(BattleField battleField, Ship ship) throws PositionShipNotFoundException {
        if (checkPositionOfShip(battleField, ship)) addShipToBattleField(battleField, ship);
        else throw new PositionShipNotFoundException();
    }

    @Override
    public Ship getRandomShip(BattleField battleField, int sizeShip) throws PositionShipNotFoundException {
        return getRandomPositionForShip(battleField, sizeShip, battleField.getColumns());
    }

}
