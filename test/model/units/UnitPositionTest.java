package model.units;

import model.gamefield.Cell;
import model.gamefield.GameField;
import model.units.robotprog.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitPositionTest {

    @Test
    void place() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        UnitPosition pos = new UnitPosition(cell, 0);

        assertEquals(cell, pos.place());
    }

    @Test
    void layer() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        int layer = 0;
        UnitPosition pos = new UnitPosition(cell, layer);

        assertEquals(layer, pos.layer());
    }
}