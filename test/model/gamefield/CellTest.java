package model.gamefield;

import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.SmartRobot;
import model.units.landscapes.Swamp;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void position() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Point pos = new Point(3,3);
        Cell cell = new Cell(field, pos);

        assertEquals(pos, cell.position());
    }

    @Test
    void field() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Point pos = new Point(3,3);
        Cell cell = new Cell(field, pos);

        assertEquals(field, cell.field());
    }

    @Test
    void neighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);
        Cell neighborEast = field.cell(1,2);

        assertEquals(neighborEast, cell.neighbor(Direction.east()));
    }

    @Test
    void neighbors() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Map<Direction, Cell> needNeighbors = new HashMap<>();
        needNeighbors.put(Direction.east(), field.cell(0,1));
        needNeighbors.put(Direction.south(), field.cell(1,0));

        Map<Direction, Cell> actualNeighbors = cell.neighbors();


        assertEquals(needNeighbors.get(Direction.east()), actualNeighbors.get(Direction.east()));
        assertEquals(needNeighbors.get(Direction.south()), actualNeighbors.get(Direction.south()));
    }

    @Test
    void setNeighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Cell newNeigh = new Cell(field, new Point(4,4));
        cell.setNeighbor(Direction.north(), newNeigh);

        assertEquals(newNeigh, cell.neighbor(Direction.north()));
        assertEquals(cell, newNeigh.neighbor(Direction.south()));
    }

    @Test
    void isNeighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Cell neighbor = field.cell(0,1);

        assertTrue(cell.isNeighbor(neighbor));
    }

    @Test
    void notNeighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Cell neighbor = field.cell(0,2);

        assertFalse(cell.isNeighbor(neighbor));
    }

    @Test
    void wall() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();
        cell.setWall(Direction.north(), wall);

        assertEquals(wall, cell.wall(Direction.north()));
    }

    @Test
    void walls() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wallN = new Wall();
        cell.setWall(Direction.north(), wallN);
        Wall wallS = new Wall();
        cell.setWall(Direction.south(), wallS);

        Map<Direction, Wall> needWalls = new HashMap<>();
        needWalls.put(Direction.north(), wallN);
        needWalls.put(Direction.south(), wallS);

        Map<Direction, Wall> actualWalls = cell.walls();

        assertEquals(needWalls.get(Direction.north()), actualWalls.get(Direction.north()));
        assertEquals(needWalls.get(Direction.south()), actualWalls.get(Direction.south()));
    }

    @Test
    void setWall() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();
        boolean ok = cell.setWall(Direction.north(), wall);

        assertTrue(ok);
        assertEquals(wall, cell.wall(Direction.north()));
        assertEquals(cell, wall.position().cell());
        assertEquals(Direction.north(), wall.position().dir());
    }

    @Test
    void setWallOnOccupiedSide() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();
        cell.setWall(Direction.north(), wall);

        Wall anotherWall = new Wall();
        boolean ok = cell.setWall(Direction.north(), anotherWall);

        assertFalse(ok);
        assertEquals(wall, cell.wall(Direction.north()));
        assertEquals(cell, wall.position().cell());
        assertNull(anotherWall.position());
    }

    @Test
    void setWallToNeighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Wall wall = new Wall();
        boolean ok = cell.setWall(Direction.east(), wall);

        assertTrue(ok);
        assertEquals(wall, cell.wall(Direction.east()));
        assertNotNull(cell.neighbor(Direction.east()).wall(Direction.west()));
        assertEquals(cell, wall.position().cell());
        assertEquals(Direction.east(), wall.position().dir());
    }

    @Test
    void directionToNeighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Cell neighbor = cell.neighbor(Direction.east());

        assertEquals(Direction.east(), cell.directionToNeighbor(neighbor));
    }

    @Test
    void directionToNotNeighbor() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Cell notNeighbor = field.cell(1,1);

        assertNull(cell.directionToNeighbor(notNeighbor));
    }

    @Test
    void cellsInRadius() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(1,1);

        ArrayList<Cell> needCells = new ArrayList<Cell>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++){
                if(field.cell(i,j) != cell)
                    needCells.add(field.cell(i,j));
            }
        }
        ArrayList<Cell> actualCells = cell.cellsInRadius(1);

        for(Cell curCell : needCells) {
            assertEquals(curCell, actualCells.get(needCells.indexOf(curCell)));
        }
    }

    @Test
    void cellsInRadiusInCorner() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        ArrayList<Cell> needCells = new ArrayList<Cell>();
        needCells.add(field.cell(0,1));
        needCells.add(field.cell(1,0));
        needCells.add(field.cell(1,1));

        ArrayList<Cell> actualCells = cell.cellsInRadius(1);

        for(Cell curCell : needCells) {
            assertEquals(curCell, actualCells.get(needCells.indexOf(curCell)));
        }
    }

    @Test
    void getUnit() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        Swamp putOne = new Swamp();
        cell.putUnit(putOne,0);

        Unit getOne = cell.getUnit(0);

        assertEquals(putOne.getClass(), getOne.getClass());
        assertEquals(putOne, getOne);
    }

    @Test
    void isEmpty() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        assertTrue(cell.isEmpty(0));
    }

    @Test
    void notEmpty() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        cell.putUnit(new Swamp(),0);

        assertFalse(cell.isEmpty(0));
    }

    @Test
    void putUnit() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        Swamp unit = new Swamp();
        boolean ok = cell.putUnit(unit, 0);

        assertTrue(ok);
        assertEquals(unit, cell.getUnit(0));
        assertEquals(cell, unit.position().place());
        assertEquals(0, unit.position().layer());
    }

    @Test
    void putUnitToWrongLayer() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        Swamp unit = new Swamp();
        boolean ok = cell.putUnit(unit, 1);

        assertFalse(ok);
        assertNull(cell.getUnit(1));
        assertNull(unit.position());
    }

    @Test
    void putUnitToOccupiedLayer() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        Swamp unit = new Swamp();
        cell.putUnit(unit, 0);

        Swamp swamp = new Swamp();
        boolean ok = cell.putUnit(swamp,0);

        assertFalse(ok);
        assertNotEquals(swamp, cell.getUnit(0));
        assertNull(swamp.position());
    }

    @Test
    void extractUnit() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        Swamp unit = new Swamp();
        cell.putUnit(unit, 0);
        cell.extractUnit(0);

        assertNull(cell.getUnit(0));
        assertNull(unit.position());
    }

    @Test
    void notExit() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);

        assertFalse(cell.isExit());
    }

    @Test
    void becomeExit() {
        GameField field = new GameField(3,3, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        Cell cell = field.cell(0,0);
        cell.becomeExit();

        assertTrue(cell.isExit());
    }
}