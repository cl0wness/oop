package model.stupidrobot;

import model.gamefield.Direction;
import model.gamefield.GameField;
import model.gamefield.WaveNavigator;
import model.units.SmartRobot;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomActionTest {

    @Test
    void rightObjectClass() {
        HorizontalAction rnd = new HorizontalAction();
        assertEquals(rnd.runAction().getClass(), Direction.class);
    }

    @Test
    void gotTarget() {
        GameField field = new GameField(2,2, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        HorizontalAction rnd = new HorizontalAction();
        assertEquals(rnd.runAction(field.cell(0,0), field.cell(1,1), new WaveNavigator(field)).getClass(), Direction.class);
    }
}