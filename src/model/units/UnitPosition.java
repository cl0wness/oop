package model.units;

import model.gamefield.Cell;

/**
 * Класс - Позиция игровой единицы - ячейка и слой.
 */
public class UnitPosition {

    // --------------------- Место ---------------------
    private Cell _place;

    public Cell place() {
        return _place;
    }

    // --------------------- Слой ---------------------
    private int _layer;

    public int layer(){
        return _layer;
    }

    // --------------------- Порождение ---------------------
    public UnitPosition(Cell place, int layer){
        _place = place;
        _layer = layer;
    }
}

