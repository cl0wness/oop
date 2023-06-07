package model.gamefield;

import model.units.UnitPosition;

import java.awt.*;
import java.util.*;

/**
 * Класс - Ячейка. Располагается на поле, имеет несколько слоёв.
 */
public class Cell {

    final int MAX_LAYER = 2; // максимальное количество слоёв в вчейке

    // ----------------------- Позиция --------------------------

    private final Point _pos;

    public Point position() {
        return _pos;
    }


    // ------------------------- Поле ----------------------------

    private GameField _field;

    public GameField field() {
        return _field;
    }

    // --------------------- Порождение --------------------------

    public Cell(GameField field, Point position) {
        _pos = position;
        _field = field;
    }

    // ---------------------- Соседи -----------------------
    private final Map<Direction, Cell> _neighbors = new HashMap<>();

    public Cell neighbor(Direction direct) {

        if(_neighbors.containsKey(direct)) {
            return _neighbors.get(direct);
        }
        return null;
    }

    public Map<Direction, Cell> neighbors() {
        return Collections.unmodifiableMap(_neighbors);
    }

    void setNeighbor(Direction direct, Cell neighbor) {
        if(neighbor != this && !isNeighbor(neighbor)) {
            _neighbors.put(direct, neighbor);
            neighbor.setNeighbor(direct.opposite(), this);
        }
    }

    public boolean isNeighbor(Cell other) {
        return _neighbors.containsValue(other);
    }

    // ---------------------- Стены -----------------------
    private final Map<Direction, Wall> _walls = new HashMap<>();

    public Wall wall(Direction direct) {

        if(_walls.containsKey(direct)) {
            return _walls.get(direct);
        }
        return null;
    }

    public Map<Direction, Wall> walls() {
        return Collections.unmodifiableMap(_walls);
    }

    boolean setWall(Direction direct, Wall wall) {
        boolean ok = false;

        if(wall != null && wall(direct) == null) {
            ok = wall.setPosition(new PositionBetween(this, direct));
            if( ok ) {
                _walls.put(direct, wall);

                if(neighbor(direct) != null)
                    neighbor(direct).setWall(direct.opposite(), new Wall());
            }
        }
        return ok;
    }

    // Определение направление к соседней ячейке
    public Direction directionToNeighbor(Cell neighbor) {
        if(isNeighbor(neighbor)){
            Object[] dirs = neighbors().keySet().toArray();
            for (Object dir : dirs) {
                if (neighbors().get((Direction) dir) == neighbor)
                    return (Direction) dir;
            }
        }
        return null;
    }

    // Поиск ячеек в заданном радиусе
    public ArrayList<Cell> cellsInRadius(int radius) {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        for(int i = this._pos.x - radius; i <= this._pos.x + radius; i++) {
            for(int j = this._pos.y - radius; j <= this._pos.y + radius; j++){
                if(_field.cell(i,j) != null && _field.cell(i,j) != this)
                    cells.add(_field.cell(i,j));
            }
        }
        return cells;
    }

    // ------------------------------- Владение юнитом ---------------------------------
    private Unit[] units = {null, null};

    public Unit getUnit(int layer) {
        return units[layer];
    }

    public boolean isEmpty(int layer) {
        return units[layer] == null;
    }

    public boolean putUnit(Unit unit, int layer) {

        boolean ok = false;

        if(layer < MAX_LAYER && isEmpty(layer) && unit != null) {
            ok = unit.setPosition(new UnitPosition(this, layer));
            if( ok ) {
                units[layer] = unit;
            }
        }
        return ok;
    }

    public void extractUnit(int layer) {
        if(!isEmpty(layer)) {
            Unit unit = getUnit(layer);
            units[layer] = null;
            unit.removePosition();
        }
    }

    // ---------------------- Статус выхода ----------------------
    private boolean _exit = false;

    public boolean isExit() {
        return _exit;
    }

    protected void becomeExit() {
        _exit = true;
    }
}
