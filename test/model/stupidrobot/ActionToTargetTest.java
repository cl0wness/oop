package model.stupidrobot;

import model.gamefield.*;
import model.units.SmartRobot;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionToTargetTest {

    @Test
    void runActionWithoutTarget() {
        Direction dir = new ActionToTarget().runAction();

        assertNull(dir);
    }

    @Test
    void runActionWithoutNavigator() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Direction dir = new ActionToTarget().runAction(field.cell(0,0), field.cell(1,0), null);

        assertNull(dir);
    }

    @Test
    void pathLength1() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        WaveNavigator nav = new WaveNavigator(field);
        Cell start = field.cell(0,0);
        Cell target = field.cell(0,0);

        Direction dir = new ActionToTarget().runAction(start, target, nav);

        assertNull(dir);
    }
    @Test
    void noPath() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        WaveNavigator nav = new WaveNavigator(field);
        TestLabyrinth lab = new TestLabyrinth(field);
        lab.seedField();
        Cell start = field.cell(0,0);
        Cell target = field.cell(0,1);

        Direction dir = new ActionToTarget().runAction(start, target, nav);

        assertNull(dir);
    }
    @Test
    void pathLengthMore1() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        WaveNavigator nav = new WaveNavigator(field);
        TestLabyrinth lab = new TestLabyrinth(field);
        lab.seedField();
        Cell start = field.cell(0,2);
        Cell target = field.cell(2,2);

        Direction dir = new ActionToTarget().runAction(start, target, nav);

        assertEquals(Direction.south(), dir);
    }
}