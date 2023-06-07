package ui.field;

import ui.ImageUtils;
import ui.units.CellItemWidget;
import ui.units.LandscapeWidget;

import javax.swing.*;
import java.awt.*;

public class CellWidget extends JPanel {

    private CellItemWidget _item;

    public CellItemWidget item() { return _item;}
    private LandscapeWidget _landscape;

    public LandscapeWidget landscape() { return _landscape; }

    private static final int CELL_SIZE = 120;

    public CellWidget() {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(ImageUtils.BACKGROUND_COLOR);
    }

    public void addItem(CellItemWidget item) {

        if(_item == null) {
            setLayout(new OverlayLayout(this));
            _item = item;
            add(item, 0);
        }
    }

    public void removeItem() {
        if (_item != null) {
            setLayout(null);
            remove(_item);
            _item = null;
            repaint();
        }
    }

    public void addLandscape(LandscapeWidget landscape) {

        if(_landscape == null) {
            setLayout(new OverlayLayout(this));
            _landscape = landscape;
            add(_landscape, -1);
        }
    }

    public void removeLandscape() {
        if (_landscape != null) {
            setLayout(null);
            remove(_landscape);
            _landscape = null;
            repaint();
        }
    }
}
