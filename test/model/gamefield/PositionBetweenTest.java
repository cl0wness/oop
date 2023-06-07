package model.gamefield;

import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.SmartRobot;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PositionBetweenTest {

    @Test
    void cell() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = new Cell(field, new Point(0,0));
        Direction dir = Direction.north();
        PositionBetween position = new PositionBetween(cell, dir);

        assertEquals(cell, position.cell());
    }

    @Test
    void dir() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = new Cell(field, new Point(0,0));
        Direction dir = Direction.north();
        PositionBetween position = new PositionBetween(cell, dir);

        assertEquals(dir, position.dir());
    }


}