package ui.field;

import org.jetbrains.annotations.NotNull;
import ui.units.Orientation;
import ui.units.BlockWidget;

import javax.swing.*;
import java.awt.*;

public class BetweenCellsWidget extends JPanel {

    private final Orientation orientation;

    public BetweenCellsWidget(@NotNull Orientation orientation) {
        super(new BorderLayout());
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
        setBackground(Color.gray);
    }

    public void setItem(@NotNull BlockWidget blockWidget) {
        if(blockWidget.getOrientation() != orientation) throw new IllegalArgumentException();
        add(blockWidget);
    }

    private Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(120, 5);
    }
}
