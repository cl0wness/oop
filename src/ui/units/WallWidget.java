package ui.units;

import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallWidget extends BlockWidget {
    public WallWidget(Orientation orientation) {
        super(orientation);
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("wall.png"));
            Dimension dimension = getDimensionByOrientation();
            image = ImageUtils.resizeImage(image, dimension.width, dimension.height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
