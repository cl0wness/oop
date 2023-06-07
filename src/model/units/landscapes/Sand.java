package model.units.landscapes;

import model.gamefield.Direction;
import model.gamefield.Unit;
import model.units.Robot;
import model.units.Landscape;

/**
 * Класс - Песок
 */
public class Sand extends Landscape {

    @Override
    public void affect(Direction directionMove) {
        Unit unit = position().place().getUnit(1);
        if(unit instanceof Robot) {
            ((Robot) unit).setSkip(1);
        }
    }
}
