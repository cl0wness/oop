package model.gamefield;

import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.SmartRobot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WaveNavigatorTest {

    @Test
    void findWay() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        TestLabyrinth labyrinth = new TestLabyrinth(field);
        labyrinth.seedField();
        Cell from = field.cell(0,1);
        Cell to = field.cell(2,2);
        WaveNavigator navigator = new WaveNavigator(field);

        ArrayList<Cell> needPath = new ArrayList<>();
        needPath.add(from);
        needPath.add(field.cell(0,2));
        needPath.add(field.cell(1,2));
        needPath.add(to);

        ArrayList<Cell> path = navigator.findPath(from, to);

        assertEquals(needPath.size(), path.size());
        for(int i = 0; i < needPath.size(); i++) {
            assertEquals(needPath.get(i), path.get(i));
        }
    }

    @Test
    void fromEqualTo() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        TestLabyrinth labyrinth = new TestLabyrinth(field);
        labyrinth.seedField();
        Cell fromTo = field.cell(0,1);
        WaveNavigator navigator = new WaveNavigator(field);

        ArrayList<Cell> needPath = new ArrayList<>();
        needPath.add(fromTo);

        ArrayList<Cell> path = navigator.findPath(fromTo, fromTo);

        assertEquals(needPath.size(), path.size());
        for(int i = 0; i < needPath.size(); i++) {
            assertEquals(needPath.get(i), path.get(i));
        }
    }

    @Test
    void noWayTo() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        TestLabyrinth labyrinth = new TestLabyrinth(field);
        labyrinth.seedField();
        Cell from = field.cell(0,0);
        Cell to = field.cell(1,1);
        WaveNavigator navigator = new WaveNavigator(field);

        ArrayList<Cell> path = navigator.findPath(from, to);

        assertTrue(path.isEmpty());
    }
}