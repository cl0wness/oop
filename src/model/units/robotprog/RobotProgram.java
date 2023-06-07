package model.units.robotprog;

/**
 * Класс - Программа робота.
 */
public class RobotProgram {
    Algorithm rationalAction;      // разумное поведение
    Algorithm irrationalAction;    // неразумное поведение

    public RobotProgram(Algorithm rational, Algorithm irrational) {
        rationalAction = rational;
        irrationalAction = irrational;
    }

    public Algorithm rationalAction() { return rationalAction; }

    public Algorithm irrationalAction() { return irrationalAction; }
}
