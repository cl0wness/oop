package model;

import model.gamefield.GameField;
import model.gamefield.Labyrinth;
import model.units.Robot;
import model.units.RobotActionEvent;
import model.units.RobotListener;
import model.units.SmartRobot;
import model.units.StupidRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - Игра
 */
public class Game {

    private final GameField _field;

    private final Labyrinth _labyrinth;

    public Game(GameField field, Labyrinth labyrinth){
        _field = field;
        _labyrinth = labyrinth;
    }

    public void start(){
        _labyrinth.seedField();

        RobotStatusListener listener = new RobotStatusListener();
        _field.smartRobot().addListener(listener);
        _field.stupidRobot().addListener(listener);
    }

    private int daysLeftInSeason = 10;

    public int daysLeftInSeason() { return daysLeftInSeason; }

    private void nextDay() {
        daysLeftInSeason--;
        changeSeason();
    }
    private void changeSeason() {
        if(daysLeftInSeason == 0) {
            _field.changeSeason();
            fireSeasonChanged();
            daysLeftInSeason = 10;
        }
    }

    public SmartRobot smartRobot(){
        return _field.smartRobot();
    }

    public StupidRobot stupidRobot(){
        return _field.stupidRobot();
    }

    public GameField field(){
        return _field;
    }

    // --------------- События - действия роботов ----------------

    private final List<RobotListener> robotListeners = new ArrayList<>();

    public void addListener(RobotListener listener) {
        robotListeners.add(listener);
    }

    protected void fireSmartIsCaught(RobotActionEvent event) {
        for (RobotListener listener : robotListeners) {
            listener.smartIsCaught(event);
        }
    }
    protected void fireSmartIsInSwamp() {
        for (RobotListener listener : robotListeners) {
            listener.smartIsInSwamp( new RobotActionEvent(listener));
        }
    }
    protected void fireSmartIsOut(RobotActionEvent event) {
        for (RobotListener listener : robotListeners) {
            listener.smartIsOut(event);
        }
    }
    protected void fireRobotIsMoved(RobotActionEvent event){
        for (RobotListener listener : robotListeners) {
            listener.robotIsMoved(event);
        }
    }
    protected void fireRobotEndMove(RobotActionEvent event){
        for (RobotListener listener : robotListeners) {
            listener.robotEndMove(event);
        }
    }

    protected class RobotStatusListener implements RobotListener {

        @Override
        public void smartIsCaught(RobotActionEvent e) {
            fireSmartIsCaught(e);

            smartRobot().deactivate();
            stupidRobot().deactivate();

        }

        @Override
        public void smartIsInSwamp(RobotActionEvent e) {
            fireSmartIsInSwamp();

            smartRobot().deactivate();
            stupidRobot().deactivate();

        }

        @Override
        public void smartIsOut(RobotActionEvent e) {
            fireSmartIsOut(e);

            smartRobot().deactivate();
            stupidRobot().deactivate();
        }

        @Override
        public void robotIsMoved(RobotActionEvent e){
            fireRobotIsMoved(e);
        }

        @Override
        public void robotEndMove(RobotActionEvent e) {
            nextDay();
            if(e.getRobot() instanceof SmartRobot)
                stupidRobot().move();

            fireRobotEndMove(e);
        }
    }

    // ------------------- События игры ---------------------
    private final List<GameEventListener> gameListeners = new ArrayList<>();

    public void addListener(GameEventListener listener) {
        gameListeners.add(listener);
    }

    protected void fireSeasonChanged() {
        for (GameEventListener listener : gameListeners) {
            listener.seasonChanged(new GameEvent(this.field().currentSeason()));
        }
    }

}
