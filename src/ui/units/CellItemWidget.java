package ui.units;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel {
    public CellItemWidget() {
        setOpaque(false);
        setPreferredSize(new Dimension(120,120));
    }
    protected abstract BufferedImage getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(),10,0, null);
    }
}
