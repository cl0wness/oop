package model.units;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.Navigator;
import model.units.robotprog.RobotProgram;

import java.util.ArrayList;

/**
 * Класс - "Глупый" робот - не управляется пользователем.
 */
public class StupidRobot extends Robot {

    // -------------------- Радиус поля действия робота -----------------
    private final int _range = 1;

    public int range() {
        return _range;
    }

    // -------------------- Программа робота -----------------
    private RobotProgram _program;

    private Navigator _navigator;

    public void setNavigator(Navigator navigator) {
        _navigator = navigator;
    }

    public StupidRobot(RobotProgram program) {
        super();
        _program = program;
    }

    // Совершение своего хода
    public boolean move(){
        if(canMove()) {
            Direction dir = chooseDirection();
            if(dir == null)
                return false;

            boolean ok = changeCellTo(dir);
            fireRobotEndMove();
            return ok;
        } else return false;
    }

    private Direction chooseDirection() {
        Cell target = detectSmart();
        if (target == null)
            return _program.irrationalAction().runAction();
        else return _program.rationalAction().runAction(position().place(), target, _navigator);
    }

    // Обнаружение умного робота
    private Cell detectSmart() {
        ArrayList<Cell> cells = position().place().cellsInRadius(range());

        for(Cell cell : cells) {
            if(cell.getUnit(1) != null && cell.getUnit(1).getClass() == SmartRobot.class)
                return cell;
        }
        return null;
    }
}
