package ui;

import model.Game;
import model.GameEvent;
import model.GameEventListener;
import model.gamefield.GameField;
import model.gamefield.SimpleLabyrinth;
import model.gamefield.WaveNavigator;
import model.units.robotprog.ActionToTarget;
import model.units.robotprog.HorizontalAction;
import model.units.robotprog.RobotProgram;
import model.units.StupidRobot;
import model.units.SmartRobot;
import ui.field.FieldWidget;
import ui.field.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {

    private Game game;
    private WidgetFactory widgetFactory;

    private FieldWidget fieldWidget;

    private JPanel infoPanel = new JPanel();
    private JLabel seasonInfo = new JLabel();
    private JLabel daysInfo = new JLabel();

    public GamePanel() throws HeadlessException {
        setVisible(true);

        widgetFactory = new WidgetFactory();
        GameField field = new GameField(5,5, new SmartRobot(), new StupidRobot(new RobotProgram(new ActionToTarget(), new HorizontalAction())));
        field.stupidRobot().setNavigator(new WaveNavigator(field));
        game = new Game(field, new SimpleLabyrinth(field));
        game.start();
        game.addListener(new GameObserver());

        JPanel content = (JPanel) this.getContentPane();
        Box mainBox = Box.createVerticalBox();

        // Информационная панель
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(createInfoPanel());

        // Игровое поле
        mainBox.add(Box.createVerticalStrut(10));
        fieldWidget = new FieldWidget(game, game.field(), widgetFactory);
        fieldWidget.addListener(new GameObserver());
        mainBox.add(fieldWidget);

        content.add(mainBox);
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // ---------------------- Создаем информационную панель -----------------------

    private Box createInfoPanel() {

        Box box = Box.createHorizontalBox();

        box.add(Box.createHorizontalStrut(10));

        box.add(new JLabel("Season :"));
        seasonInfo.setText(game.field().currentSeason().toString());
        box.add(Box.createHorizontalStrut(10));
        box.add(seasonInfo);

        box.add(Box.createHorizontalStrut(20));

        box.add(new JLabel("Days left :"));
        daysInfo.setText(Integer.toString(game.daysLeftInSeason()));
        box.add(Box.createHorizontalStrut(10));
        box.add(daysInfo);

        box.add(Box.createHorizontalStrut(10));

        return box;
    }
    protected class GameObserver implements GameEventListener {
        @Override
        public void dayChanged(GameEvent event) {
            daysInfo.setText(Integer.toString(game.daysLeftInSeason()));
        }

        @Override
        public void gameOver(GameEvent event) {
            fieldWidget.setEnabled(false);
        }

        @Override
        public void seasonChanged(GameEvent event) {
            fieldWidget.paintNewWidgets();
            seasonInfo.setText(game.field().currentSeason().toString());
            repaint();
        }
    }
}
