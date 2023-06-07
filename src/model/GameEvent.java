package model;

import model.units.Robot;

import java.util.EventObject;

public class GameEvent extends EventObject {

    private Robot robot;

    public void setRobot(Robot robotSource) {
        robot = robotSource;
    }

    public Robot getRobot() {
        return robot;
    }

    public GameEvent(Object source) {
        super(source);
    }
}
