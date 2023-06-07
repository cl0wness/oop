package ui.field;

import ui.ImageUtils;
import ui.field.CellWidget;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExitWidget extends CellWidget {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage image = ImageIO.read(new File("exit.png"));
            image = ImageUtils.resizeImage(image, 110, 110);
            g.drawImage(image, 5, 5, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}