package model.units;

import model.gamefield.Direction;
import model.gamefield.Unit;

/**
 * Класс - Ландшафт (абстрактный) - находится на 0 слое ячейки.
 */
public abstract class Landscape extends Unit {
    // ------------------------ Порождение ------------------------------
    protected Landscape() {
        needLayer = 0;
    }

    public abstract void affect(Direction directionMove);

    protected void changeRobotCell(Robot robot, Direction dir) {
        robot.changeCellTo(dir);
    }
}
