package ui.units;

import model.gamefield.Direction;
import model.units.Robot;
import model.units.SmartRobot;
import org.jetbrains.annotations.NotNull;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SmartRobotWidget extends RobotWidget {
    public SmartRobotWidget(Robot robot) {
        super(robot);
        setFocusable(true);
        addKeyListener(new KeyController());
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getSmartFileByTrapped(robot.skip() > 0));
            image = ImageUtils.resizeImage(image, 100, 117);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static File getSmartFileByTrapped(boolean trapped) {
        File file = null;
        file = trapped ? new File("sm_trap.png") : new File("smart.png");

        return file;
    }

    // Внутренний класс-обработчик событий. Придает специфицеское поведение виджету
    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();

            moveAction(keyCode);
            setFocusable(true);
            requestFocus();

        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        private void moveAction(@NotNull int keyCode) {
            Direction direction = directionByKeyCode(keyCode);
            if (direction != null) {
                SmartRobot smart = (SmartRobot) robot;
                smart.move(direction);
            }
        }

        private Direction directionByKeyCode(@NotNull int keyCode) {
            Direction direction = null;
            switch (keyCode) {
                case KeyEvent.VK_W:
                    direction = Direction.north();
                    break;
                case KeyEvent.VK_S:
                    direction = Direction.south();
                    break;
                case KeyEvent.VK_A:
                    direction = Direction.west();
                    break;
                case KeyEvent.VK_D:
                    direction = Direction.east();
                    break;
            }
            return direction;
        }
    }
}
