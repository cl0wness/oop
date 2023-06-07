package model.units.landscapes;

import model.Game;
import model.gamefield.*;
import model.units.SmartRobot;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ChangeableSwampTest {

    @Test
    void frozenAffect() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);
        swamp.changeConditionTo(Season.WINTER);

        cell.putUnit(field.smartRobot(), 1);

        swamp.affect(Direction.north());

        assertNull(cell.getUnit(1));
        assertEquals(field.smartRobot(), cell.neighbor(Direction.north()).getUnit(1));
    }
    @Test
    void defaultAffectToStupid() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        cell.putUnit(field.stupidRobot(), 1);

        swamp.affect(Direction.north());

        assertEquals(3, field.stupidRobot().skip());
    }

    @Test
    void defaultAffectToSmart() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        cell.putUnit(field.smartRobot(), 1);

        swamp.affect(Direction.north());

        assertEquals((int)Double.POSITIVE_INFINITY, field.smartRobot().skip());
    }

    @Test
    void changeConditionToWinter() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.AUTUMN);
        swamp.changeConditionTo(Season.WINTER);

        assertTrue(swamp.isFrozen());
        for(Cell neighbor : cell.neighbors().values()) {
            assertNull(neighbor.getUnit(0));
        }
    }

    @Test
    void changeConditionToSpring() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.SPRING);

        assertFalse(swamp.isFrozen());
        for(Cell neighbor : cell.neighbors().values()) {
            assertTrue(neighbor.getUnit(0) instanceof ChangeableSwamp && !((ChangeableSwamp) neighbor.getUnit(0)).isMother);
        }
    }

    @Test
    void changeConditionToSummer() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.SPRING);
        swamp.changeConditionTo(Season.SUMMER);

        for(Cell neighbor : cell.neighbors().values()) {
            assertNull(neighbor.getUnit(0));
        }
    }
    @Test
    void changeConditionToAutumn() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.AUTUMN);

        for(Cell neighbor : cell.neighbors().values()) {
            assertTrue(neighbor.getUnit(0) instanceof ChangeableSwamp && !((ChangeableSwamp) neighbor.getUnit(0)).isMother);
        }
    }

    @Test
    void increaseNotMother() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(false);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.SPRING);

        for(Cell neighbor : cell.neighbors().values()) {
            assertNull(neighbor.getUnit(0));
        }
    }

    @Test
    void increaseAtOccupiedCell() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Cell northCell = cell.neighbor(Direction.north());
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);
        northCell.putUnit(new Swamp(), 0);


        swamp.changeConditionTo(Season.SPRING);

        assertTrue(northCell.getUnit(0) instanceof Swamp);
        for(Cell neighbor : cell.neighbors().values()) {
            if(!Objects.equals(cell.directionToNeighbor(neighbor), Direction.north()))
                assertTrue(neighbor.getUnit(0) instanceof ChangeableSwamp);
            else
                assertTrue(neighbor.getUnit(0) instanceof Swamp);
        }
    }
    @Test
    void increaseAtWall() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        Cell cell = field.cell(0,0);
        cell.neighbor(Direction.south()).extractUnit(0);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.SPRING);

        for(Cell neighbor : cell.neighbors().values()) {
            assertNull(neighbor.getUnit(0));
        }
    }
    @Test
    void decreaseNotMother() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        ChangeableSwamp swamp = new ChangeableSwamp(false);

        cell.putUnit(swamp, 0);
        ChangeableSwamp another = new ChangeableSwamp(false);
        cell.neighbor(Direction.north()).putUnit(another, 0);

        swamp.changeConditionTo(Season.SUMMER);

        assertEquals(cell.neighbor(Direction.north()).getUnit(0), another);
    }

    @Test
    void decreaseFromNoSwamp() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Cell northCell = cell.neighbor(Direction.north());
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);
        northCell.putUnit(new Sand(), 0);


        swamp.changeConditionTo(Season.SPRING);
        swamp.changeConditionTo(Season.SUMMER);

        assertTrue(northCell.getUnit(0) instanceof Sand);
    }
    @Test
    void decreaseFromWall() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Game game = new Game(field, new TestLabyrinth(field));
        game.start();
        Cell cell = field.cell(0,0);
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        cell.putUnit(swamp, 0);

        swamp.changeConditionTo(Season.SPRING);
        swamp.changeConditionTo(Season.SUMMER);

        assertNotNull(cell.neighbor(Direction.south()).getUnit(0));
    }
}