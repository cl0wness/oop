package model.gamefield;

/**
 * Класс - Стена - непроходимое препятствие, может располагаться между ячейками.
 * Привязана к одной ячейке, при установке внутри поля должна устанавливаться другая стена на соседней клетке.
 */
public class Wall {

    // -------------------- Позиция ------------------------
    private PositionBetween _position;

    public PositionBetween position() {
        return _position;
    }

    protected boolean setPosition(PositionBetween pos) {
        boolean ok = canTakePosition(pos);
        if(ok) {
            _position = pos;
        }
        return ok;
    }

    protected boolean canTakePosition(PositionBetween pos) {
        return pos != null && position() == null &&
                pos.cell().wall(pos.dir()) == null;
    }
}
