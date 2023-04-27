/*************************
 * Authors: Samuel Gall
 *
 * Class that holds data of the game, used for replay
 ************************/
package src.game.replay;

import src.common.CommonField;
import src.common.CommonMaze;

import java.util.ArrayList;
import java.util.List;

public class ReplayDetails {
    private CommonMaze maze;
    private int time;
    private List<CommonField.Direction> pacmanPath = new ArrayList<>();
    private List<GhostData> ghostData = new ArrayList<>();
    public ReplayDetails(){

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

    public void setTime(int time) {
        this.time = time;
    }

    public List<CommonField.Direction> getPacmanPath() {
        return pacmanPath;
    }

    public void setPacmanPath(List<CommonField.Direction> pacmanPath) {
        this.pacmanPath = pacmanPath;
    }

    public List<GhostData> getGhostData() {
        return ghostData;
    }

    public void setGhostData(List<GhostData> ghostData) {
        this.ghostData = ghostData;
    }
}
