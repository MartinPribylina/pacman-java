package src.gui;

import src.common.CommonField;

import javax.swing.*;

public class Game extends JPanel implements IGame {
    public MazeObjectDescription objectDescription;
    @Override
    public void setObjectDescription(MazeObjectDescription objectDescription) {
        this.objectDescription = objectDescription;
    }

    public void refresh(){
        repaint();
    }

    @Override
    public void setPlacmanGoalDestinationClick(CommonField field) {

    }
}
