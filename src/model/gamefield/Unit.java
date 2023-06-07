package model.gamefield;

import model.units.UnitPosition;

/**
 * Класс - Игровая единица (абстрактная)
 */
abstract public class Unit {

    // ---------------- Позиция -----------------------
    private UnitPosition _position;

    protected int needLayer; // необходимый для юнита слой

    public UnitPosition position() {
        return _position;
    }

    // Могу ли занимать позицию
    public boolean canTakePosition(UnitPosition position) {
        return position.place().isEmpty(position.layer()) && position.layer() == needLayer;
    }
    // Установить позицию
    boolean setPosition(UnitPosition position) {
        boolean ok = canTakePosition(position);
        if(ok) {
            _position = position;
        }
        return ok;
    }

    // Убрать позицию
    void removePosition() {
        if(position() != null) {
            UnitPosition pos = position();
            _position = null;
            pos.place().extractUnit(pos.layer());
        }
    }
}
