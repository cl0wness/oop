package model.units;

import model.gamefield.Cell;
import model.gamefield.Direction;
import model.gamefield.Unit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - Робот (абстрактный) - должен быть на 1 уровне ячейке
 */
abstract public class Robot extends Unit {

    // ------------------------ Активность ----------------------
    private boolean activity = true;

    public boolean isActive() {
        return activity;
    }

    public void deactivate() {
        activity = false;
    }

    // ------------------------------ Пропуски ходов ---------------------------
    private int _skip = 0;

    public int skip(){
        return _skip;
    }

    public void setSkip(int skip) {
        _skip = skip;
    }


    // --------------------------- Порождение ---------------------------------
    protected Robot() {
        needLayer = 1;
    }

     boolean changeCellTo(Direction dir) {
        if (position().place().wall(dir) != null)
            return false;

        Cell cell = position().place().neighbor(dir);
        if(cell.getUnit(1) == null) {
            Cell oldCell = position().place();
            oldCell.extractUnit(1);
            cell.putUnit(this, 1);
            fireRobotIsMoved(oldCell, cell);

            if(cell.getUnit(0) != null && cell.getUnit(0) instanceof Landscape)
                ((Landscape)cell.getUnit(0)).affect(dir);

            return true;
        } else if(this instanceof StupidRobot && cell.getUnit(1) instanceof SmartRobot) {
            fireSmartIsCaught(cell);
        } else if (this instanceof SmartRobot && cell.getUnit(1) instanceof StupidRobot) {
            fireSmartIsCaught(position().place());
        }
        return false;
    }

    protected boolean canMove() {
        if(!isActive())
            return false;

        if(skip() > 0 ) {
            setSkip(skip()-1);
            fireRobotEndMove();
            return false;
        }
        return true;
    }
    // ---------------------------- Cлушатели и события ------------------------
    private final List<RobotListener> _listeners = new ArrayList<>();

    public void addListener(RobotListener listener) {
        _listeners.add(listener);
    }

    protected void fireSmartIsCaught(Cell smartCell) {
        for (RobotListener listener : _listeners) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setToCell(smartCell);
            listener.smartIsCaught(event);
        }
    }
    protected void fireSmartIsInSwamp() {
        for (RobotListener listener : _listeners) {
            RobotActionEvent event = new RobotActionEvent(listener);
            listener.smartIsInSwamp(event);
        }
    }
    protected void fireSmartIsOut(Cell exit) {
        for (RobotListener listener : _listeners) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setToCell(exit);
            listener.smartIsOut(event);
        }
    }

    protected void fireRobotIsMoved(@NotNull Cell oldPosition, @NotNull Cell newPosition){
        for (RobotListener listener : _listeners) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.robotIsMoved(event);
        }
    }

    protected void fireRobotEndMove(){
        for (RobotListener listener : _listeners) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotEndMove(event);
        }
    }
}
