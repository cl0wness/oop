package ui.units;

import model.units.Robot;
import ui.ImageUtils;
import ui.units.RobotWidget;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StupidRobotWidget extends RobotWidget {
    public StupidRobotWidget(Robot robot) {
        super(robot);
    }
    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getStupidFileByTrapped(robot.skip() > 0));
            image = ImageUtils.resizeImage(image, 100, 116);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static File getStupidFileByTrapped(boolean trapped) {
        File file = null;
        file = trapped ? new File("st_trap.png") : new File("stupid.png");

        return file;
    }
}
