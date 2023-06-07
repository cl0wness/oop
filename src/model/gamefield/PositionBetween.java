package model.gamefield;

/**
 * Класс - Позиция между ячейками (для стен)
 */
public class PositionBetween {

    // -------------------- Ячейка ----------------------
    private Cell _cell;

    public Cell cell() {
        return _cell;
    }

    // -------------------- Направление ----------------------
    private Direction _dir;

    public Direction dir() {
        return _dir;
    }
    // -------------------- Порождение ----------------------
    PositionBetween(Cell cell, Direction dir) {
        _cell = cell;
        _dir = dir;
    }
}
