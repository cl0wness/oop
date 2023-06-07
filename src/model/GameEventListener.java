package model;

public interface GameEventListener {

    void dayChanged(GameEvent event);

    void gameOver(GameEvent event);

    void seasonChanged(GameEvent event);
}
