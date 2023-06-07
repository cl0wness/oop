package model.units.robotprog;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.Navigator;

import java.util.ArrayList;

/**
 * Класс - Поведение робота, совершающее действие к цели.
 */
public class ActionToTarget extends Algorithm {
    @Override
    public Direction runAction() {
        return null;
    }

    @Override
    public Direction runAction(Cell start, Cell target, Navigator nav) {
        if(nav == null)
            return null;
        ArrayList<Cell> path = nav.findPath(start, target);
        if(!path.isEmpty() && path.size() > 1)
            return start.directionToNeighbor(path.get(1));
        return null;
    }
}
