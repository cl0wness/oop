package ui.field;

import model.gamefield.Cell;
import model.gamefield.Wall;
import model.units.Landscape;
import model.units.StupidRobot;
import model.units.Robot;
import model.units.SmartRobot;
import model.units.landscapes.ChangeableSwamp;
import model.units.landscapes.Sand;
import model.units.landscapes.Swamp;
import org.jetbrains.annotations.NotNull;
import ui.units.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();
    private final Map<Robot, RobotWidget> robots = new HashMap<>();
    private final Map<Landscape, LandscapeWidget> landscapes = new HashMap<>();
    private final Map<Wall, WallWidget> walls = new HashMap<>();
    public CellWidget create(@NotNull Cell cell) {
        if(cells.containsKey(cell)) return cells.get(cell);

        CellWidget item = (cell.isExit()) ? new ExitWidget() : new CellWidget();

        Robot robot = (Robot)cell.getUnit(1);
        if(robot != null) {
            RobotWidget robotWidget;
            if(robot instanceof SmartRobot)
                robotWidget = create((SmartRobot)robot);
            else robotWidget = create((StupidRobot) robot);
            item.addItem(robotWidget);
        }
        Landscape landscape = (Landscape)cell.getUnit(0);
        if(landscape != null) {
            LandscapeWidget landscapeWidget = create(landscape);
            item.addLandscape(landscapeWidget);
        }
        cells.put(cell, item);
        return item;
    }

    public CellWidget getWidget(@NotNull Cell cell) {
        return cells.get(cell);
    }

    public RobotWidget create(@NotNull SmartRobot robot) {
        if(robots.containsKey(robot)) return robots.get(robot);

        SmartRobotWidget smart = new SmartRobotWidget(robot);
        robots.put(robot, smart);
        return smart;
    }
    public RobotWidget create(@NotNull StupidRobot robot) {
        if(robots.containsKey(robot)) return robots.get(robot);

        StupidRobotWidget stupid = new StupidRobotWidget(robot);
        robots.put(robot, stupid);
        return stupid;
    }

    public RobotWidget getWidget(@NotNull Robot robot) {
        return robots.get(robot);
    }

    public LandscapeWidget create(@NotNull Landscape landscape) {
        if(landscapes.containsKey(landscape)) return landscapes.get(landscape);

        LandscapeWidget item = null;
        if(landscape instanceof Swamp) {
            item = new SwampWidget((Swamp) landscape);
        } else if(landscape instanceof Sand) {
            item = new SandWidget((Sand) landscape);
        } else if(landscape instanceof ChangeableSwamp) {
            item = new ChangeableSwampWidget((ChangeableSwamp) landscape);
        }
        landscapes.put(landscape, item);
        return item;
    }

    void remove(LandscapeWidget landscapeWidget) {
        if(landscapeWidget != null) {
            landscapes.remove(landscapeWidget.landscape());
        }
    }
    public LandscapeWidget getWidget(@NotNull Landscape landscape) {
        return landscapes.get(landscape);
    }

    public WallWidget create(@NotNull Wall wall, Orientation orientation) {
        if(walls.containsKey(wall)) return walls.get(wall);

        WallWidget item = new WallWidget(orientation);
        walls.put(wall, item);
        return item;
    }

    public WallWidget getWidget(@NotNull Wall wall) {
        return walls.get(wall);
    }
}

