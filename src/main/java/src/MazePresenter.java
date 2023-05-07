/*************************
 * Authors: taken from Task 2 https://moodle.vut.cz/mod/folder/view.php?id=310208, further edited by Martin Pribylina
 *
 * MazePresenter is class for game display, initiating FieldViews in Grid Layout
 ************************/
package src;

import src.common.CommonMaze;
import src.gui.IGame;
import src.view.FieldView;

import java.awt.*;
import javax.swing.*;

/**
 * MazePresenter is class for game display, initiating FieldViews in Grid Layout
 * @author      Martin Pribylina
 */
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

