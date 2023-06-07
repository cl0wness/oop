package model.units;

import model.gamefield.Direction;
import model.units.Robot;

/**
 * Класс - "Умный" робот - управляется пользователем
 */
public class SmartRobot extends Robot {

    // Перемещение
    public boolean move(Direction dir){
        if(canMove())
            return changeCellTo(dir);
        else return false;
    }

    boolean changeCellTo(Direction dir) {
        boolean ok = super.changeCellTo(dir);
        if(ok)
        {
            if(skip() == (int)Double.POSITIVE_INFINITY)
                fireSmartIsInSwamp();
            else if (position().place().isExit()) {
                fireSmartIsOut(position().place());
            }
            fireRobotEndMove();
        }
        return ok;
    }
}
