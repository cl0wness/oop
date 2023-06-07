package model.units;

import java.util.EventObject;

public interface RobotListener {

    void smartIsCaught(RobotActionEvent e);    // умный пойман

    void smartIsInSwamp(RobotActionEvent e);   // умный в болоте

    void smartIsOut(RobotActionEvent e);       // умный добрался до выхода

    void robotIsMoved(RobotActionEvent e);     // робот переместился

    void robotEndMove(RobotActionEvent e);     // робот закончил ход
}
