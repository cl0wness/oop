package ui.field;

import model.Game;
import model.GameEvent;
import model.GameEventListener;
import model.gamefield.*;
import model.units.Landscape;
import model.units.RobotActionEvent;
import model.units.RobotListener;
import org.jetbrains.annotations.NotNull;
import ui.units.LandscapeWidget;
import ui.units.Orientation;
import ui.units.RobotWidget;
import ui.units.WallWidget;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FieldWidget extends JPanel {

    private final GameField field;
    private final WidgetFactory widgetFactory;

    public FieldWidget(@NotNull Game game, @NotNull GameField field, @NotNull  WidgetFactory widgetFactory) {
        this.field = field;
        this.widgetFactory = widgetFactory;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
        game.addListener(new RobotController());
    }

    private void fillField() {
        for (int i = 0; i < field.height(); ++i) {
            JPanel wallsTop = createRowWalls(i, Direction.north());
            add(wallsTop);
            JPanel row = createRow(i);
            add(row);
            JPanel wallsBottom = createRowWalls(i, Direction.south());
            add(wallsBottom);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.width(); ++i) {
            Cell cell = field.cell(rowIndex, i);
            CellWidget cellWidget = widgetFactory.create(cell);

            BetweenCellsWidget westWidget = new BetweenCellsWidget(Orientation.VERTICAL);
            Wall westWall = cell.wall(Direction.west());
            if(westWall != null)
                westWidget.setItem(widgetFactory.create(westWall, Orientation.VERTICAL));
            row.add(westWidget);

            row.add(cellWidget);

            BetweenCellsWidget eastWidget = new BetweenCellsWidget(Orientation.VERTICAL);
            Wall eastWall = cell.wall(Direction.east());
            if(eastWall != null)
                eastWidget.setItem(widgetFactory.create(eastWall, Orientation.VERTICAL));
            row.add(eastWidget);
        }
        return row;
    }


    private JPanel createRowWalls(int rowIndex, Direction direction) {
        if(direction == Direction.east() || direction == Direction.west()) throw new IllegalArgumentException();
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.width(); ++i) {
            Cell cell = field.cell(rowIndex, i);

            BetweenCellsWidget widget = new BetweenCellsWidget(Orientation.HORIZONTAL);
            Wall wall = cell.wall(direction);
            if(wall != null) {
                WallWidget wallWidget = widgetFactory.create(wall, Orientation.HORIZONTAL);
                widget.setItem(wallWidget);
            }
            row.add(widget);
        }
        return row;
    }

    private class RobotController implements RobotListener {

        @Override
        public void smartIsCaught(RobotActionEvent e) {
            widgetFactory.getWidget(e.getToCell()).removeItem();
            String str = "Вы были пойманы!";

            JOptionPane.showMessageDialog(null, str, "Поражение.", JOptionPane.INFORMATION_MESSAGE);

            fireGameOver();
        }

        @Override
        public void smartIsInSwamp(RobotActionEvent e) {
            String str = "Вы попали в болото!";

            JOptionPane.showMessageDialog(null, str, "Поражение.", JOptionPane.INFORMATION_MESSAGE);

            fireGameOver();
        }

        @Override
        public void smartIsOut(RobotActionEvent e) {
            widgetFactory.getWidget(e.getToCell()).removeItem();
            String str = "Вы достигли выхода! Ура!";

            JOptionPane.showMessageDialog(null, str, "Победа!", JOptionPane.INFORMATION_MESSAGE);

            fireGameOver();
        }

        @Override
        public void robotIsMoved(RobotActionEvent e) {
            RobotWidget robotWidget = widgetFactory.getWidget(e.getRobot());
            CellWidget from = widgetFactory.getWidget(e.getFromCell());
            CellWidget to = widgetFactory.getWidget(e.getToCell());
            from.removeItem();
            to.addItem(robotWidget);
            repaint();
        }
        @Override
        public void robotEndMove(RobotActionEvent e) {
            repaint();
            fireDayChanged();
        }
    }

    // ------------------- События игры ---------------------
    private final List<GameEventListener> gameListeners = new ArrayList<>();

    public void addListener(GameEventListener listener) {
        gameListeners.add(listener);
    }

    protected void fireGameOver() {
        for (GameEventListener listener : gameListeners) {
            listener.gameOver(new GameEvent(this));
        }
    }
    protected void fireDayChanged() {
        for (GameEventListener listener : gameListeners) {
            listener.dayChanged(new GameEvent(this));
        }
    }

    public void paintNewWidgets() {

        for (int i = 0; i < field.height(); ++i) {
            for(int j=0; j < field.width(); j++) {
                Cell cell = field.cell(i,j);
                CellWidget cellWidget = widgetFactory.getWidget(cell);

                Unit unit = cell.getUnit(0);
                if(unit instanceof Landscape) {
                    LandscapeWidget landscapeWidget = widgetFactory.create((Landscape) unit);
                    cellWidget.addLandscape(landscapeWidget);
                } else if(unit == null && cellWidget.landscape() != null) {
                    widgetFactory.remove(cellWidget.landscape());
                    cellWidget.removeLandscape();
                }
            }
        }
    }
}
