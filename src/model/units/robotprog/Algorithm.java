package model.units.robotprog;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.Navigator;
/**
 * Класс - Поведение робота (абстрактный)
 */
public abstract class Algorithm {

    // Действие не требующее дополнительных знаний
    public abstract Direction runAction();

    // Дейтсвие, которому нужен старт и цель
    public abstract Direction runAction(Cell start, Cell target, Navigator nav);
}
