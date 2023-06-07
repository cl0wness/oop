package model.gamefield;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void clockwise() {
        Direction dir = Direction.north();
        Direction newDir = dir.clockwise();

        assertEquals(Direction.east(), newDir);
    }

    @Test
    void anticlockwise() {
        Direction dir = Direction.north();
        Direction newDir = dir.anticlockwise();

        assertEquals(Direction.west(), newDir);
    }

    @Test
    void opposite() {
        Direction dir = Direction.north();
        Direction newDir = dir.opposite();

        assertEquals(Direction.south(), newDir);
    }

    @Test
    void onRight() {
        Direction dir = Direction.north();
        Direction newDir = dir.onRight();

        assertEquals(Direction.east(), newDir);
    }

    @Test
    void onLeft() {
        Direction dir = Direction.north();
        Direction newDir = dir.onLeft();

        assertEquals(Direction.west(), newDir);
    }

    @Test
    void isOpposite() {
        Direction north = Direction.north();
        Direction south = Direction.south();

        assertTrue(north.isOpposite(south));
    }
}