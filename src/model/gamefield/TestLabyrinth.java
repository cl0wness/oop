package model.gamefield;

import model.units.Robot;
import model.units.SmartRobot;
import model.units.StupidRobot;
import model.units.landscapes.Swamp;

import java.util.HashMap;


// Лабиринт для тестов классов, не используется в игре. В нём воплощены самые типичные тестовые ситуации. Выглядит, как(и верхняя и нижняя границы):
// |__|__   |
// |Sw Sm   |
// |Sw St   |
// Sw - болото, Sm - умный робот, St - глупый робот, __ или | - стена
public class TestLabyrinth extends Labyrinth {

    public TestLabyrinth(GameField field) {
        super(field);
    }
    @Override
    protected Cell setExit() {
        Cell exit = _field.cell(0,2);
        exit.becomeExit();

        return exit;
    }

    @Override
    protected HashMap<Robot, Cell> seedRobots() {
        HashMap<Robot, Cell> results = new HashMap<>();

        SmartRobot smart = _field.smartRobot();
        Cell cellSm = _field.cell(1,1);
        cellSm.putUnit(smart,1);
        results.put(smart, cellSm);

        StupidRobot stupid = _field.stupidRobot();
        Cell cellSt = _field.cell(2,1);
        cellSt.putUnit(stupid,1);
        results.put(stupid, cellSt);

        return results;
    }

    @Override
    protected HashMap<Wall, PositionBetween> seedWallsInside() {
        HashMap<Wall, PositionBetween> results = new HashMap<>();

        _field.cell(0,1).setWall(Direction.south(), new Wall());
        results.put(_field.cell(0,1).wall(Direction.south()),  _field.cell(0,1).wall(Direction.south()).position());

        _field.cell(0,0).setWall(Direction.south(), new Wall());
        results.put(_field.cell(0,0).wall(Direction.south()), _field.cell(0,0).wall(Direction.south()).position());

        _field.cell(0,0).setWall(Direction.east(), new Wall());
        results.put(_field.cell(0,0).wall(Direction.east()), _field.cell(0,0).wall(Direction.east()).position());

        return results;
    }

    @Override
    protected HashMap<Unit, Cell> seedUnits() {
        HashMap<Unit, Cell> results = new HashMap<>();

        _field.cell(1,0).putUnit(new Swamp(), 0);
        results.put(_field.cell(1,0).getUnit(0), _field.cell(1,0));

        _field.cell(2,0).putUnit(new Swamp(), 0);
        results.put(_field.cell(2,0).getUnit(0), _field.cell(2,0));

        return results;
    }
}
