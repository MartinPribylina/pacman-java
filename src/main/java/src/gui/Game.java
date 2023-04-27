package src.main.java.src.gui;

import src.main.java.src.common.CommonField;

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
    public void setPacmanGoalDestinationClick(CommonField field) {

    }

    @Override
    public void endGame(boolean isVictory) {

    }
}
