/*************************
 * Authors: Martin Pribylina
 *
 * Interface for game
 ************************/
package src.gui;

import src.common.CommonField;
import src.view.MazeObjectDescription;

/**
 * IGame is interface to enable Common functionality between GamePlay and GameReplay
 */
public interface IGame {
    void setObjectDescription(MazeObjectDescription objectDescription);
    void refresh();
    void setPacmanGoalDestinationClick(CommonField field);
    void endGame(boolean isVictory);
}
