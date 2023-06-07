package ui.units;

import model.units.landscapes.ChangeableSwamp;
import model.units.landscapes.Swamp;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChangeableSwampWidget extends LandscapeWidget{
    public ChangeableSwampWidget(ChangeableSwamp swamp) {
        super();
        landscape = swamp;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            ChangeableSwamp swamp = (ChangeableSwamp) landscape;
            image = ImageIO.read(getChangeSwampFileByFrozen(swamp.isFrozen()));
            image = swamp.isMother() ? ImageUtils.resizeImage(image, 110, 110) : ImageUtils.resizeImage(image, 90, 90);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static File getChangeSwampFileByFrozen(boolean isFrozen) {
        File file = null;
        file = isFrozen ? new File("c_f_swamp.png") : new File("c_swamp.png");

        return file;
    }

}
