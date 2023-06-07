package model.gamefield;

import model.units.landscapes.ChangeableSwamp;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.SmartRobot;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void getSize() {
        GameField field = new GameField(4,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));

        assertEquals(5, field.width());
        assertEquals(4, field.height());
    }

    @Test
    void getCell() {
        GameField field = new GameField(3, 3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Point point = new Point(1, 1);

        Cell cell = field.cell(1, 1);
        Cell sameCell = field.cell(point);

        assertEquals(cell, sameCell);
    }

    @Test
    void changeSeasonOneSwamp() {
        GameField field = new GameField(3, 3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.changeSeason();
        ChangeableSwamp swamp = new ChangeableSwamp(true);

        field.cell(0,0).putUnit(swamp, 0);
        field.changeSeason();

        assertEquals(Season.WINTER, field.currentSeason());
        assertTrue(swamp.isFrozen());
    }

    @Test
    void changeSeasonTwoSwamp() {
        GameField field = new GameField(3, 3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.changeSeason();
        ChangeableSwamp swamp1 = new ChangeableSwamp(true);
        ChangeableSwamp swamp2 = new ChangeableSwamp(false);

        field.cell(0,0).putUnit(swamp1, 0);
        field.cell(1,1). putUnit(swamp2, 0);
        field.changeSeason();

        assertEquals(Season.WINTER, field.currentSeason());
        assertTrue(swamp1.isFrozen());
        assertTrue(swamp2.isFrozen());
    }

    @Test
    void currentSeason() {
        GameField field = new GameField(3, 3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));

        Season season = field.currentSeason();

        assertEquals(Season.SUMMER, season);
    }
}