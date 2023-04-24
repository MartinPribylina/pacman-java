package src.game.save;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.Ghost;
import src.game.PathField;

import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GameLogging implements Serializable {
    private String filepath;
    private CommonMaze maze;
    private int time;
    private List<CommonField.Direction> pacmanPath = new ArrayList<>();
    private Dictionary<PathField, GhostData> ghostsData = new Hashtable<>();

    public GameLogging(String filepath){
        this.filepath = filepath;
        this.time = 0;
    }

    public void frameTick() {
        pacmanMove();
        ghostsMove();
        this.time++;
    }

    public void pacmanMove(){
        pacmanPath.add(maze.pacman().lastMove());
    }
    public void ghostsMove(){
        for (CommonMazeObject CMOGhost : maze.ghosts())
        {
            Ghost ghost = (Ghost) CMOGhost;

            if (ghostsData.get(ghost.start) == null) {
                ghostsData.put((PathField) ghost.start, new GhostData(ghost.ghostType(), ghost.start));
            }
            ghostsData.get(ghost.start).path.add(ghost.lastMove());
        }
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

    public List<CommonField.Direction> getPacmanPath() {
        return pacmanPath;
    }

    public String getFilepath() {
        return filepath;
    }

    public Dictionary<PathField, GhostData> getGhostsData() {
        return ghostsData;
    }
}
