package ui.units;

import model.units.landscapes.Swamp;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwampWidget extends LandscapeWidget {

    public SwampWidget(Swamp swamp) {
        super();
        landscape = swamp;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("swamp.png"));
            image = ImageUtils.resizeImage(image, 120, 120);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
