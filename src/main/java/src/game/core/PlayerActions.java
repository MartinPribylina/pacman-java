/*************************
 * Authors: Martin Pribylina
 *
 * Contains methods for changing objects move direction through controller
 ************************/
package src.game.core;

import src.common.CommonField;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Ghost is class which contains actions which bind to keys for changing objects move direction through controller
 *
 * @author      Martin Pribylina
 */
public class PlayerActions {

    private final Action upAction, downAction, leftAction, rightAction;

    private final GameController gameController;
    public PlayerActions(GameController gameController){
        upAction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();

        this.gameController = gameController;
    }

    public class UpAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {

            gameController.ChangePacmanDirectionKeyboardInput(CommonField.Direction.UP);
        }
    }

    public class DownAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {

            gameController.ChangePacmanDirectionKeyboardInput(CommonField.Direction.DOWN);
        }
    }

    public class LeftAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {

            gameController.ChangePacmanDirectionKeyboardInput(CommonField.Direction.LEFT);
        }
    }

    public class RightAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            gameController.ChangePacmanDirectionKeyboardInput(CommonField.Direction.RIGHT);
        }
    }

    public Action getUpAction() {
        return upAction;
    }

    public Action getDownAction() {
        return downAction;
    }

    public Action getLeftAction() {
        return leftAction;
    }

    public Action getRightAction() {
        return rightAction;
    }
}
