package src.game;


import src.game.objects.*;
import src.game.save.GhostData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class MazeConfigure implements Serializable {

    private int rows, cols;
    private int currentRow;
    private boolean readingSuccess, error;

    private Maze maze = null;
    private Dictionary<PathField, GhostData> ghostsData;

    public MazeConfigure()
    {

    }
    public MazeConfigure(Dictionary<PathField, GhostData> ghostsData)
    {
        this.ghostsData = ghostsData;
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

                    if (ghostsData == null) {
                        maze.setMazeObject(currentRow, col, new Ghost(pathField));
                    }else {
                        List<PathField> fields = new ArrayList<>();
                        ghostsData.keys().asIterator().forEachRemaining((field ->
                                fields.add(field)));
                        GhostData ghost = null;
                        for (int j = 0; j < fields.size(); j++){
                            if(pathField.equals(fields.get(j)))
                                ghost = ghostsData.get(fields.get(j));
                        }
                        maze.setMazeObject(currentRow, col, new Ghost(pathField, ghost.type, ghost.path));
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
