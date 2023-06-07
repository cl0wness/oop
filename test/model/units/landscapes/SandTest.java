package model.units.landscapes;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.GameField;
import model.units.SmartRobot;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SandTest {

    @Test
    void affectAtRobot(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Sand sand = new Sand();
        cell.putUnit(sand, 0);
        StupidRobot robot = field.stupidRobot();
        cell.putUnit(robot, 1);

        sand.affect(Direction.north());

        assertEquals(1, robot.skip());
    }
    @Test
    void noAffect(){
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Sand sand = new Sand();
        cell.putUnit(sand, 0);
        StupidRobot robot = field.stupidRobot();

        sand.affect(Direction.north());

        assertEquals(0, robot.skip());
    }

}