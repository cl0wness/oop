package model.gamefield;

import model.units.Robot;

import java.util.HashMap;

/**
 * Класс - Лабиринт - абстрактный заполнитель поля всеми объектами для игры.
 */
abstract public class Labyrinth {

    // -------------------- Поле ---------------------------
    protected GameField _field;

    public Labyrinth(GameField field){
        _field = field;
    }

    // Заселение поля
    public void seedField() {
        setExit();
        seedRobots();
        seedUnits();
        seedWalls();
    }

    abstract protected Cell setExit();                        // установка выхода
    abstract protected HashMap<Robot, Cell> seedRobots();     // заселение роботов

    // Заселение стен
    protected HashMap<Wall, PositionBetween> seedWalls(){
        HashMap<Wall, PositionBetween> results;
        results = seedBorders();
        results.putAll(seedWallsInside());
        return results;
    }

    // Заселение граничных стен
    protected HashMap<Wall, PositionBetween> seedBorders(){
        HashMap<Wall, PositionBetween> results = new HashMap<>();
        for (Cell cell : _field) {
            if (cell.position().x == 0) {
                cell.setWall(Direction.north(), new Wall());
                results.put(cell.wall(Direction.north()), new PositionBetween(cell, Direction.north()));
            }
            if (cell.position().x == _field.height() - 1) {
                cell.setWall(Direction.south(), new Wall());
                results.put(cell.wall(Direction.south()), new PositionBetween(cell, Direction.south()));
            }
            if (cell.position().y == 0) {
                cell.setWall(Direction.west(), new Wall());
                results.put(cell.wall(Direction.west()), new PositionBetween(cell, Direction.west()));
            }
            if (cell.position().y == _field.width() - 1) {
                cell.setWall(Direction.east(), new Wall());
                results.put(cell.wall(Direction.east()), new PositionBetween(cell, Direction.east()));
            }
        }
        return results;
    }
    abstract protected HashMap<Wall, PositionBetween> seedWallsInside();   // заселение внутренних стен

    abstract protected HashMap<Unit, Cell> seedUnits();                    // заселение остальных юнитов
}
