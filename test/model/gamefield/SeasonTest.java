package model.gamefield;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeasonTest {

    @Test
    void nextSummer() {
        Season current = Season.SPRING;

        Season next = current.next();

        assertEquals(Season.SUMMER, next);
    }
    @Test
    void nextAutumn() {
        Season current = Season.SUMMER;

        Season next = current.next();

        assertEquals(Season.AUTUMN, next);
    }
    @Test
    void nextWinter() {
        Season current = Season.AUTUMN;

        Season next = current.next();

        assertEquals(Season.WINTER, next);
    }
    @Test
    void nextSpring() {
        Season current = Season.WINTER;

        Season next = current.next();

        assertEquals(Season.SPRING, next);
    }
}