package model.units;

import model.Game;
import model.gamefield.*;
import model.units.robotprog.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartRobotTest {
    @Test
    void create() {
        SmartRobot robot = new SmartRobot();

        assertTrue(robot.isActive());
    }

    @Test
    void deactivate() {
        SmartRobot robot = new SmartRobot();

        robot.deactivate();

        assertFalse(robot.isActive());
    }

    @Test
    void move() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();
        UnitPosition robotPos = robot.position();
        Direction east = Direction.east();

        UnitPosition needPos = new UnitPosition(robotPos.place().neighbor(east), 1);

        robot.move(east);
        UnitPosition newPos = robot.position();

        assertEquals(needPos.place(), newPos.place());
        assertEquals(needPos.layer(),newPos.layer());
    }

    @Test
    void moveToWall() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();
        UnitPosition robotPos = robot.position();
        Direction north = Direction.north();

        boolean ok = robot.move(north);
        UnitPosition newPos = robot.position();

        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(),newPos.layer());
        assertFalse(ok);
    }

    @Test
    void moveToSwamp() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();
        UnitPosition robotPos = robot.position();
        Direction west = Direction.west();

        UnitPosition needPos = new UnitPosition(robotPos.place().neighbor(west), 1);

        boolean ok = robot.move(west);
        UnitPosition newPos = robot.position();

        assertEquals(needPos.place(), newPos.place());
        assertEquals(needPos.layer(),newPos.layer());
        assertFalse(robot.isActive());
        assertTrue(ok);
    }

    @Test
    void moveToStupid() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();
        UnitPosition robotPos = robot.position();
        Direction south = Direction.south();

        boolean ok = robot.move(south);
        UnitPosition newPos = robot.position();

        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(), newPos.layer());
        assertFalse(robot.isActive());
        assertFalse(ok);
    }

    @Test
    void moveToExit() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();
        UnitPosition robotPos = robot.position();
        Direction east = Direction.east();
        Direction north = Direction.north();

        UnitPosition needPos = new UnitPosition(robotPos.place().neighbor(east).neighbor(north), 1);

        robot.move(east);
        robot.move(north);
        UnitPosition newPos = robot.position();

        assertEquals(needPos.place(), newPos.place());
        assertEquals(needPos.layer(),newPos.layer());
        assertFalse(robot.isActive());
    }

    @Test
    void notActiveToMove() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();
        UnitPosition robotPos = robot.position();
        Direction east = Direction.east();

        robot.deactivate();
        boolean ok = robot.move(east);
        UnitPosition newPos = robot.position();

        assertEquals(robotPos.place(), newPos.place());
        assertEquals(robotPos.layer(),newPos.layer());
        assertFalse(ok);
    }

    @Test
    void canTakePosition() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();

        Cell cell = robot.position().place().neighbor(Direction.east());

        boolean ok = robot.canTakePosition(new UnitPosition(cell, 1));

        assertTrue(ok);
    }

    @Test
    void canNotTakeOccupiedCell() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();

        Cell cell = robot.position().place().neighbor(Direction.east());
        cell.putUnit(new SmartRobot(), 1);

        boolean ok = robot.canTakePosition(new UnitPosition(cell, 1));

        assertFalse(ok);
    }

    @Test
    void canNotTakeWrongLayer() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        SmartRobot robot = game.field().smartRobot();

        Cell cell = robot.position().place().neighbor(Direction.east());

        boolean ok = robot.canTakePosition(new UnitPosition(cell, 0));

        assertFalse(ok);
    }
}