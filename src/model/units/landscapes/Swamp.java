package model.units.landscapes;

import model.gamefield.Direction;
import model.gamefield.Unit;
import model.units.Landscape;
import model.units.Robot;
import model.units.SmartRobot;
import model.units.StupidRobot;

/**
 * Класс - Болото
 */
public class Swamp extends Landscape {


    // ------------------------- Влияние ---------------------------------
    public void affect(Direction directionMove){
        Unit unit = position().place().getUnit(1);
        int skip = 0;
        if(!(unit instanceof Robot))
            return;

        if(unit instanceof StupidRobot)
            skip = 3;
        else if(unit instanceof SmartRobot)
            skip = (int) Double.POSITIVE_INFINITY;
        ((Robot)unit).setSkip(skip);
    }
}
