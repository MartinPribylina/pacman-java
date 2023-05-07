/*************************
 * Authors: Samuel Gall
 *
 * Class that holds data of ghosts
 ************************/
package src.game.replay;

import src.common.CommonField;

import java.util.ArrayList;
import java.util.List;

/**
 * ReplayDetails is class that holds data of ghosts
 *
 * @author      Samuel Gall
 */
public class GhostData {

    public List<CommonField.Direction> path = new ArrayList<>();
    public int type;
    public int startX;
    public int startY;
    public GhostData(int type, int startX, int startY) {
        this.type = type;
        this.startX = startX;
        this.startY = startY;
    }
    public void setPath(List<CommonField.Direction> path) {
        this.path = path;
    }
}
