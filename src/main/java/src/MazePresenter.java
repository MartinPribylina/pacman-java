package src.main.java.src;

import src.main.java.src.common.CommonMaze;
import src.main.java.src.gui.GamePlay;
import src.main.java.src.gui.IGame;
import src.main.java.src.view.FieldView;

import java.awt.*;
import javax.swing.*;

public class MazePresenter extends JPanel{

    public MazePresenter(CommonMaze maze, IGame game) {
        int rows = maze.numRows();
        int cols = maze.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        this.setLayout(layout);
        this.setBorder(BorderFactory.createLineBorder(Color.white, 2));

        this.setBackground(Color.BLACK);

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(maze.getField(i, j), game);
                this.add(field);
            }
        }
    }
}

