package src;

import src.common.CommonMaze;
import src.view.FieldView;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class MazePresenter extends JPanel{

    public MazePresenter(CommonMaze maze) {
        int rows = maze.numRows();
        int cols = maze.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        this.setLayout(layout);

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(maze.getField(i, j));
                this.add(field);
            }
        }
    }
}

