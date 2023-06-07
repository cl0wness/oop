package ui.units;

import model.units.Robot;
import ui.units.CellItemWidget;

import java.awt.image.BufferedImage;

public abstract class RobotWidget extends CellItemWidget {
    protected final Robot robot;

    public RobotWidget(Robot robot) {
        super();
        this.robot = robot;
    }

    protected abstract BufferedImage getImage();
}
