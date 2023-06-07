package model.gamefield;

import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.SmartRobot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    @Test
    void position() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();
        cell.setWall(Direction.east(), wall);

        assertEquals(cell, wall.position().cell());
        assertEquals(Direction.east(), wall.position().dir());
    }

    @Test
    void canTakePosition() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();

        boolean ok = wall.canTakePosition(new PositionBetween(cell,Direction.east()));

        assertTrue(ok);
    }

    @Test
    void alreadyHavePosition() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();
        cell.setWall(Direction.east(), wall);

        boolean ok = wall.canTakePosition(new PositionBetween(cell,Direction.west()));

        assertFalse(ok);
    }

    @Test
    void canNotTakeOccupiedCellSide() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        cell.setWall(Direction.east(), new Wall());
        Wall wall = new Wall();

        boolean ok = wall.canTakePosition(new PositionBetween(cell,Direction.east()));

        assertFalse(ok);
    }
}