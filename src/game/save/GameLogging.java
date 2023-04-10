package src.game.save;

import src.common.CommonField;
import src.common.CommonMaze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameLogging implements Serializable {
    private String filepath;
    private CommonMaze maze;
    private int time;
    private List<CommonField.Direction> pacmanDirection = new ArrayList<>();

    public GameLogging(String filepath){
        this.filepath = filepath;
        this.time = 0;
    }

    public void frameTick() {
        this.pacmanDirection.add(maze.pacman().lastMove());
        this.time++;
    }

    public CommonMaze getMaze() {
        return maze;
    }

    public void setMaze(CommonMaze maze) {
        this.maze = maze;
    }

    public int getTime() {
        return time;
    }

    public List<CommonField.Direction> getPacmanDirection() {
        return pacmanDirection;
    }

    public String getFilepath() {
        return filepath;
    }
}
