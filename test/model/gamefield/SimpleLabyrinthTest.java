package model.gamefield;

import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.Robot;
import model.units.SmartRobot;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleLabyrinthTest {

    @Test
    void setExit() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        SimpleLabyrinth labyrinth = new SimpleLabyrinth(field);

        Cell exit = labyrinth.setExit();

        assertTrue(exit.isExit());
    }

    @Test
    void seedRobot() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        SimpleLabyrinth labyrinth = new SimpleLabyrinth(field);

        HashMap<Robot, Cell> results =  labyrinth.seedRobots();

        assertEquals(field.smartRobot().position().place(), results.get(field.smartRobot()));
        assertEquals(field.stupidRobot().position().place(), results.get(field.stupidRobot()));
    }

    @Test
    void seedWalls() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        SimpleLabyrinth labyrinth = new SimpleLabyrinth(field);

        HashMap<Wall, PositionBetween> results = labyrinth.seedWalls();

        if(results != null) {
            Wall[] walls = new Wall[results.size()];
            walls = results.keySet().toArray(walls);
            for (int i = 0; i < results.size(); i++) {
                assertEquals(walls[i], results.get(walls[i]).cell().wall(results.get(walls[i]).dir()));
            }
        }
    }

    @Test
    void seedUnits() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        SimpleLabyrinth labyrinth = new SimpleLabyrinth(field);

        HashMap<Unit, Cell> results = labyrinth.seedUnits();

        if(results != null) {
            Unit[] units = new Unit[results.size()];
            units = results.keySet().toArray(units);
            for (int i = 0; i < results.size(); i++) {
                assertEquals(units[i], results.get(units[i]).getUnit(units[i].needLayer));
            }
        }
    }
}