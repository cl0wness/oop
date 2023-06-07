package model.gamefield;

import model.units.landscapes.ChangeableSwamp;
import model.units.Landscape;
import model.units.landscapes.Sand;
import model.units.StupidRobot;
import model.units.Robot;
import model.units.SmartRobot;
import model.units.landscapes.Swamp;

import java.util.HashMap;
import java.util.Random;

/**
 * Класс - Простой лабиринт, состоящий из 2 роботов, 6 стен и 3 болот. Объекты располагаются случайно.
 */
public class SimpleLabyrinth extends Labyrinth {

    public SimpleLabyrinth(GameField field) {
        super(field);
    }
    @Override
    protected Cell setExit() {
        Cell exit = randomCell();
        exit.becomeExit();

        return exit;
    }

    @Override
    protected HashMap<Robot, Cell> seedRobots() {
        HashMap<Robot, Cell> results = new HashMap<>();

        SmartRobot smart = _field.smartRobot();
        Cell cellSm = randomCell();
        while(cellSm.isExit())
            cellSm = randomCell();
        cellSm.putUnit(smart,1);
        results.put(smart, cellSm);

        StupidRobot stupid = _field.stupidRobot();
        Cell cellSt = randomCell();
        while(cellSt.isExit() || !cellSt.isEmpty(1))
            cellSt = randomCell();
        cellSt.putUnit(stupid,1);
        results.put(stupid, cellSt);

        return results;
    }

    @Override
    protected HashMap<Wall, PositionBetween> seedWallsInside() {
        HashMap<Wall, PositionBetween> results = new HashMap<>();

        while(results.size() < 6) {
           Cell cell = randomCell();
           if(cell.walls().size() < 4) {
               Direction dir = randomDirection();
               if(cell.setWall(dir, new Wall()))
                   results.put(cell.wall(dir), cell.wall(dir).position());
           }
        }
        return results;
    }

    @Override
    protected HashMap<Unit, Cell> seedUnits() {
        HashMap<Unit, Cell> results = new HashMap<>();

        while(results.size() < 3){
            Cell cell = randomCell();
            if(cell.isEmpty(1) && !cell.isExit()){
                cell.putUnit(randomLandscape(), 0);
                results.put(cell.getUnit(0), cell);
            }
        }
        return results;
    }

    private Cell randomCell(){
        int rndX = new Random().nextInt(_field.height());
        int rndY = new Random().nextInt(_field.width());
        return _field.cell(rndX, rndY);
    }

    private Direction randomDirection(){
        Direction[] dirs = new Direction[] {Direction.north(), Direction.east(), Direction.south(), Direction.west()};

        int rnd = new Random().nextInt(dirs.length);

        return dirs[rnd];
    }

    private Landscape randomLandscape() {
        Landscape[] lands = {new Swamp(), new Sand(), new ChangeableSwamp(true)};

        int rnd = new Random().nextInt(lands.length);

        return lands[rnd];
    }
}
