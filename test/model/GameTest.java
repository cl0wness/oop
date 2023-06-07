package model;

import model.gamefield.*;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.RobotActionEvent;
import model.units.RobotListener;
import model.units.SmartRobot;
import model.units.UnitPosition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void makeMove() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        TestListener listener = new TestListener();
        game.addListener(listener);
        game.start();

        SmartRobot smart = game.smartRobot();
        UnitPosition smartPos = smart.position();

        game.smartRobot().move(Direction.east());

        assertNotEquals(smartPos, smart.position());
        assertEquals(9, game.daysLeftInSeason());
        assertTrue(listener.events.contains(TestListener.EventType.MOVED));
    }
    @Test
    void reactToCatch() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        SmartRobot smart = game.smartRobot();
        TestListener listener = new TestListener();
        game.addListener(listener);
        game.stupidRobot().setNavigator(new WaveNavigator(field));

        game.start();
        smart.move(Direction.south());

        assertFalse(smart.isActive());
        assertFalse(game.stupidRobot().isActive());
        assertTrue(listener.events.contains(TestListener.EventType.CAUGHT));
    }
    @Test
    void reactToOut() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        SmartRobot smart = game.smartRobot();
        TestListener listener = new TestListener();
        game.addListener(listener);

        game.start();
        smart.move(Direction.east());
        smart.move(Direction.north());

        assertFalse(smart.isActive());
        assertFalse(game.stupidRobot().isActive());
        assertTrue(listener.events.contains(TestListener.EventType.OUT));
    }
    @Test
    void reactToSwamp() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        SmartRobot smart = game.smartRobot();
        TestListener listener = new TestListener();
        game.addListener(listener);

        game.start();
        smart.move(Direction.west());

        assertFalse(smart.isActive());
        assertFalse(game.stupidRobot().isActive());
        assertTrue(listener.events.contains(TestListener.EventType.IN_SWAMP));
    }

    @Test
    void reactToSkip() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        SmartRobot smart = game.smartRobot();
        smart.setSkip(2);
        TestListener listener = new TestListener();
        game.addListener(listener);

        game.start();
        UnitPosition smartPos = smart.position();
        smart.move(Direction.west());

        assertEquals(smartPos, smart.position());
        assertTrue(listener.events.contains(TestListener.EventType.END));
    }

    @Test
    void smartRobot() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new SimpleLabyrinth(field));

        SmartRobot robotGame = game.smartRobot();
        SmartRobot robotField = field.smartRobot();

        assertEquals(robotGame, robotField);
    }

    @Test
    void stupidRobot() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new SimpleLabyrinth(field));

        StupidRobot robotGame = game.stupidRobot();
        StupidRobot robotField = field.stupidRobot();
        assertEquals(robotGame, robotField);
    }

    @Test
    void field() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new SimpleLabyrinth(field));

        assertEquals(field, game.field());
    }

    @Test
    void nextDay() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new SimpleLabyrinth(field));
        GameListener gameListener = new GameListener();
        game.addListener(gameListener);
        game.start();
        game.stupidRobot().deactivate();

        game.smartRobot().move(Direction.east());

        assertEquals(9, game.daysLeftInSeason());
        assertFalse(gameListener.events.contains(GameListener.EventType.SEASON));
    }

    @Test
    void nextSeason() {
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new SimpleLabyrinth(field));
        GameListener gameListener = new GameListener();
        game.addListener(gameListener);
        game.start();

        game.stupidRobot().setSkip(10);
        game.smartRobot().setSkip(10);

        for(int i = 0; i < 10; i++)
            game.smartRobot().move(Direction.north());

        assertTrue(gameListener.events.contains(GameListener.EventType.SEASON));
    }

    private class TestListener implements RobotListener {

        public ArrayList<EventType> events = new ArrayList<>();

        public enum EventType {
            CAUGHT,
            IN_SWAMP,
            OUT,
            MOVED,
            END
        }
        @Override
        public void smartIsCaught(RobotActionEvent e) {
            events.add(EventType.CAUGHT);
        }

        @Override
        public void smartIsInSwamp(RobotActionEvent e) {
            events.add(EventType.IN_SWAMP);
        }

        @Override
        public void smartIsOut(RobotActionEvent e) {
            events.add(EventType.OUT);
        }

        @Override
        public void robotIsMoved(RobotActionEvent e) {
            events.add(EventType.MOVED);
        }

        @Override
        public void robotEndMove(RobotActionEvent e) { events.add(EventType.END); }
    }
    protected class GameListener implements GameEventListener {

        public ArrayList<EventType> events = new ArrayList<>();

        public enum EventType {
            OVER,
            SEASON,
            DAY
        }
        @Override
        public void dayChanged(GameEvent event){
            events.add(EventType.DAY);
        }

        @Override
        public void gameOver(GameEvent event) {
            events.add(EventType.OVER);
        }

        @Override
        public void seasonChanged(GameEvent event) {
            events.add(EventType.SEASON);
        }
    }
}