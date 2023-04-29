/**
 * @author Martin Pribylina, Samuel Gall
 *
 * Class for building a maze
 */
package src.game;


import src.game.objects.*;
import src.game.replay.GhostData;

import java.io.Serializable;
import java.util.List;

public class MazeConfigure implements Serializable {

    private int rows, cols;
    private int currentRow;
    private boolean readingSuccess, error;

    private Maze maze = null;
    private List<GhostData> ghostsData;

    boolean mazeHasKey = false;
    /**
     * Class constructor.
     */
    public MazeConfigure()
    {

    }
    public MazeConfigure(List<GhostData> ghostsData)
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
                case 'T' -> {
                    pathField.setTarget(true);
                    maze.setField(currentRow, col, pathField);
                }
                case 'K' -> {
                    maze.setField(currentRow, col, pathField);
                    maze.setKey(currentRow, col, new Key(pathField));
                    mazeHasKey = true;
                }
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
                        for (GhostData ghost : ghostsData){
                            int x = pathField.getRow();
                            int y = pathField.getCol();
                            if (ghost.startX == x && ghost.startY == y)
                            {
                                maze.setMazeObject(currentRow, col, new Ghost(pathField, ghost.type, ghost.path));
                                System.out.println(ghost);
                                break;
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
            if (!mazeHasKey){
                maze.pacman().setHasKey(true);
            }
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
