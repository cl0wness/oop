package model.units.robotprog;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.Navigator;

import java.util.Random;

/**
 * Класс - Горизонтальное действие
 */
public class HorizontalAction extends Algorithm {
    @Override
    public Direction runAction() {
        Direction[] dirs = new Direction[] {Direction.east(), Direction.west()};

        int rnd = new Random().nextInt(dirs.length);

        return dirs[rnd];
    }

    @Override
    public Direction runAction(Cell start, Cell target, Navigator nav) {
        return runAction();
    }
}
