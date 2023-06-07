package model.units.landscapes;

import model.gamefield.Season;
import model.units.Landscape;

/**
 * Класс - Изменяемый ландшафт (абстрактный) - меняет своё состояние в зависимости от сезона.
 */
public abstract class ChangeableLandscape extends Landscape {

    boolean isMother;

    public boolean isMother() { return isMother; }
    abstract public void changeConditionTo(Season season);
}
