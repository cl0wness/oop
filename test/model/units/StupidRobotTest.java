package model.units;

import model.Game;
import model.gamefield.Direction;
import model.gamefield.GameField;
import model.gamefield.TestLabyrinth;
import model.gamefield.WaveNavigator;
import model.units.landscapes.Swamp;
import model.units.robotprog.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StupidRobotTest {
    @Test
    void range() {
        StupidRobot robot = new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction()));

        int range = robot.range();

        assertEquals(1, range);
    }

    @Test
    void skip() {
        StupidRobot robot = new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction()));

        int skip = robot.skip();

        assertEquals(0, skip);
    }

    @Test
    void setSkip() {
        StupidRobot robot = new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction()));

        robot.setSkip(3);
        int skip = robot.skip();

        assertEquals(3, skip);
    }

    @Test
    void moveIrrational() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        field.cell(1,1).extractUnit(1);     // убираем умного с поля, чтобы глупый действова нерационально

        StupidRobot robot = game.field().stupidRobot();
        UnitPosition robotPos = robot.position();

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertTrue(ok);
        assertNotEquals(robotPos.place(), newPos.place());
    }

    @Test
    void moveRational() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();

        StupidRobot robot = game.field().stupidRobot();
        UnitPosition robotPos = robot.position();
        robotPos.place().extractUnit(1);
        robotPos.place().neighbor(Direction.east()).putUnit(robot, 1);

        UnitPosition needPos = new UnitPosition(field.cell(1,2), 1);

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertTrue(ok);
        assertEquals(needPos.place(), newPos.place());
        assertEquals(needPos.layer(), newPos.layer());
    }

    @Test
    void moveToSwamp() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        StupidRobot robot = game.field().stupidRobot();
        UnitPosition robotPos = robot.position();

        robotPos.place().neighbor(Direction.north()).extractUnit(1);                                           // обеспечиваем точное попадание в болото
        robotPos.place().neighbor(Direction.north()).putUnit(new Swamp(), 0);
        robotPos.place().neighbor(Direction.east()).neighbor(Direction.north()).putUnit(field.smartRobot(), 1);
        robotPos.place().neighbor(Direction.east()).putUnit(new Swamp(), 0);

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertTrue(ok);
        assertEquals(3,robot.skip());
        assertNotEquals(robotPos.place(), newPos.place());
    }

    @Test
    void moveToSmartCell() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        StupidRobot robot = game.field().stupidRobot();
        UnitPosition robotPos = robot.position();

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertFalse(ok);
        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(),newPos.layer());
        assertFalse(robot.isActive());
    }

    @Test
    void moveToWall() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        field.cell(1,1).extractUnit(1);

        StupidRobot robot = game.field().stupidRobot();
        UnitPosition robotPos = robot.position();
        robotPos.place().extractUnit(1);
        field.cell(0,0).putUnit(robot,1);       // обеспечиваем точно попытку переместится в стену
        robotPos = robot.position();

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertFalse(ok);
        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(), newPos.layer());
    }

    @Test
    void wayIsNotFound() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();

        StupidRobot robot = game.field().stupidRobot();
        UnitPosition robotPos = robot.position();
        robotPos.place().extractUnit(1);
        field.cell(0,0).putUnit(robot,1);
        robotPos = robot.position();

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertFalse(ok);
        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(),newPos.layer());
    }

    @Test
    void notActiveToMove() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        StupidRobot robot = game.field().stupidRobot();
        robot.deactivate();
        UnitPosition robotPos = robot.position();

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertFalse(ok);
        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(),newPos.layer());
    }

    @Test
    void needSkip() {
        GameField field = new GameField(4,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        StupidRobot robot = game.field().stupidRobot();
        robot.setSkip(3);
        UnitPosition robotPos = robot.position();

        boolean ok = robot.move();
        UnitPosition newPos = robot.position();

        assertFalse(ok);
        assertEquals(2, robot.skip());
        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(),newPos.layer());
    }
}