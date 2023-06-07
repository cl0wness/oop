package ui.units;

import model.units.Landscape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class LandscapeWidget extends JPanel {

    protected Landscape landscape;

    public Landscape landscape() { return landscape;}

    public LandscapeWidget(){
        setOpaque(false);
        setPreferredSize(new Dimension(120,120));
    }
    protected abstract BufferedImage getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image = getImage();
        g.drawImage(image, (120-image.getWidth())/2, (120-image.getWidth())/2, null);

    }
}
