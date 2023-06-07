package model.units.landscapes;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.GameField;
import model.units.SmartRobot;
import model.units.UnitPosition;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwampTest {

    @Test
    void canTakePosition(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        UnitPosition pos = new UnitPosition(field.cell(1,1), 0);

        Swamp swamp = new Swamp();

        assertTrue(swamp.canTakePosition(pos));
    }
    @Test
    void takePosWrongLayer(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        UnitPosition pos = new UnitPosition(field.cell(1,1), 1);

        Swamp swamp = new Swamp();

        assertFalse(swamp.canTakePosition(pos));
    }

    @Test
    void takePosOccupiedCell(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        cell.putUnit(new Swamp(), 0);
        UnitPosition pos = new UnitPosition(cell, 0);

        Swamp swamp = new Swamp();

        assertFalse(swamp.canTakePosition(pos));
    }

    @Test
    void affectAtStupid(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Swamp swamp = new Swamp();
        cell.putUnit(swamp, 0);
        StupidRobot robot = field.stupidRobot();
        cell.putUnit(robot, 1);

        swamp.affect(Direction.north());

        assertEquals(3, robot.skip());
    }
    @Test
    void affectAtSmart(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Swamp swamp = new Swamp();
        cell.putUnit(swamp, 0);
        SmartRobot robot = field.smartRobot();
        cell.putUnit(robot, 1);

        swamp.affect(Direction.north());

        assertEquals((int)Double.POSITIVE_INFINITY, robot.skip());
    }

    @Test
    void noAffect(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Swamp swamp = new Swamp();
        cell.putUnit(swamp, 0);
        StupidRobot robot = field.stupidRobot();

        swamp.affect(Direction.north());

        assertEquals(0, robot.skip());
    }

}