package src.game.replay;

import src.common.CommonField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GhostData implements Serializable {

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
