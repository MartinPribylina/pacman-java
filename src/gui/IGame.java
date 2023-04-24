package src.gui;

import src.common.CommonField;

public interface IGame {
    void setObjectDescription(MazeObjectDescription objectDescription);
    void refresh();
    void setPlacmanGoalDestinationClick(CommonField field);
}
