package ui.units;

import model.units.landscapes.Sand;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SandWidget extends LandscapeWidget{

    public SandWidget(Sand sand) {
        super();
        landscape = sand;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("sand.png"));
            image = ImageUtils.resizeImage(image, 120, 120);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
