package model.gamefield;

import java.util.ArrayList;

/**
 * Класс - Навигатор (абстрактный), использующийся для нахождения пути от одной ячейки до другой.
 */
public abstract class Navigator {

    // -------------------- Поле ----------------------
    protected GameField _field;

    // -------------------- Порождение ----------------------
    Navigator(GameField field){
        _field = field;
    }

    // -------------------- Поиск пути ----------------------
    public abstract ArrayList<Cell> findPath(Cell from, Cell to);
}
