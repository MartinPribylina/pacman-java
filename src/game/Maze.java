package src.game;


import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.common.Observable;

import java.util.ArrayList;
import java.util.List;

public class Maze implements CommonMaze {

    private final int cols;
    private final int rows;
    private final CommonField[][] fields;

    private final List<CommonMazeObject> ghosts;

    private CommonMazeObject pacman;

    public Maze(int rows, int cols){
        this.cols = cols;
        this.rows = rows;
        this.fields = new CommonField[rows][cols];

        ghosts = new ArrayList<>();
    }

    public void setField(int row, int col, CommonField field){
        fields[row][col] = field;
    }

    public void setMazeObject(int row, int col, CommonMazeObject mazeObject){
        fields[row][col].addObserver((Observable.Observer) mazeObject);
        if(!mazeObject.isPacman())
            ghosts.add(mazeObject);
        if(mazeObject.isPacman())
            pacman = mazeObject;

    }

    @Override
    public CommonField getField(int row, int col) {
        if (row > rows || row < 0 || col > cols || col < 0)
            return null;
        else
            return fields[row][col];
    }

    @Override
    public int numCols() {
        return cols;
    }

    @Override
    public List<CommonMazeObject> ghosts() {
        return new ArrayList<>(ghosts);
    }

    @Override
    public CommonMazeObject pacman() {
        return pacman;
    }

    @Override
    public int numRows() {
        return rows;
    }
}
