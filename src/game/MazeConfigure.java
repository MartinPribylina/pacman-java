package src.game;


import src.common.CommonMazeObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MazeConfigure implements Serializable {

    private int rows, cols;
    private int currentRow;
    private boolean readingSuccess, error;

    private Maze maze = null;
    private List<CommonMazeObject> ghosts = new ArrayList<>();

    public MazeConfigure()
    {

    }
    public MazeConfigure(List<CommonMazeObject> ghosts)
    {
        this.ghosts = ghosts;
    }

    public Maze createMaze()
    {
        if(readingSuccess && !error)
            return maze;
        return null;
    }

    public void startReading(int rows, int cols){
        this.rows = rows + 2;
        this.cols = cols + 2;
        this.currentRow = 0;
        this.readingSuccess = false;
        maze = new Maze(this.rows, this.cols);
        makeWallRow(0);
    }

    public boolean processLine(String line){
        if (line.length() != cols - 2)
        {
            error = true;
            return false;
        }
        maze.setField(currentRow, 0, new WallField(currentRow, 0, maze));
        for(int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            int col = i + 1;

            PathField pathField = new PathField(currentRow, col, maze);

            switch (c) {
                case 'X' -> maze.setField(currentRow, col, new WallField(currentRow, col, maze));
                case '.' -> maze.setField(currentRow, col, pathField);
                case 'S' -> {
                    maze.setField(currentRow, col, pathField);
                    maze.setMazeObject(currentRow, col, new Pacman(pathField));
                }
                case 'G' -> {
                    maze.setField(currentRow, col, pathField);

                    if (ghosts.isEmpty()) {
                        maze.setMazeObject(currentRow, col, new Ghost(pathField));
                    }else {
                        for (CommonMazeObject ghost : ghosts){
                            if (((Ghost)ghost).getStart().equals(pathField)) {
                                maze.setMazeObject(currentRow, col, new Ghost(pathField, ((Ghost) ghost).ghostType, ((Ghost) ghost).getGhostDirections()));
                            }
                        }
                    }

                }
                default -> {
                }
            }
        }
        maze.setField(currentRow, cols - 1, new WallField(currentRow, cols - 1, maze));
        currentRow++;
        return true;
    }

    public boolean stopReading()
    {
        makeWallRow(currentRow);
        if(currentRow == rows){
            readingSuccess = true;
            return true;
        }
        return false;
    }

    private void makeWallRow(int row){
        for(int col = 0; col < cols; col++){
            maze.setField(row, col, new WallField(row, col, maze));
        }
        currentRow++;
    }

    public boolean isReadingSuccess() {
        return readingSuccess;
    }
}
