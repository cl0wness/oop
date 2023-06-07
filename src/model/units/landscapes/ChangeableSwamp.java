package model.units.landscapes;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.Season;
import model.gamefield.Unit;
import model.units.Robot;
import model.units.SmartRobot;
import model.units.StupidRobot;

import java.util.Set;

/**
 * Класс - Изменяемое болото.
 */
public class ChangeableSwamp extends ChangeableLandscape {

    private boolean _isFrozen;

    public boolean isFrozen() {
        return _isFrozen;
    }

    public ChangeableSwamp(boolean _isMother) {
        isMother = _isMother;
    }
    @Override
    public void affect(Direction directionMove) {
        Unit unit = position().place().getUnit(1);
        if(!(unit instanceof Robot))
            return;

        Robot robot = (Robot)unit;
        if(_isFrozen) {
            changeRobotCell(robot, directionMove);
        } else {
            int skip = 0;
            if(robot instanceof StupidRobot)
                skip = 3;
            else if(robot instanceof SmartRobot)
                skip = (int) Double.POSITIVE_INFINITY;
            robot.setSkip(skip);
        }
    }

    @Override
    public void changeConditionTo(Season season) {
        switch(season) {
            case WINTER -> {
                decrease();
                _isFrozen = true;
            }
            case SPRING -> {
                _isFrozen = false;
                increase();
            }
            case SUMMER -> decrease();
            case AUTUMN -> increase();
        }
    }

    private void increase(){
        if(!isMother)
            return;

        Cell currentCell = position().place();
        Set<Direction> neighborsDirect = currentCell.neighbors().keySet();

        for (Direction dir: neighborsDirect) {
            Cell neighbor = currentCell.neighbor(dir);
            if(currentCell.wall(dir) == null && neighbor.isEmpty(0)) {
                neighbor.putUnit(new ChangeableSwamp(false), 0);
            }
        }
    }

    private void decrease(){
        if(!isMother)
            return;

        Cell currentCell = position().place();
        Set<Direction> neighborsDirect = currentCell.neighbors().keySet();

        for (Direction dir: neighborsDirect) {
            Cell neighbor = currentCell.neighbor(dir);
            if(currentCell.wall(dir) != null)
                break;

            if(neighbor.getUnit(0) instanceof ChangeableSwamp && !((ChangeableSwamp) neighbor.getUnit(0)).isMother) {
                neighbor.extractUnit(0);
            }
        }
    }
}
