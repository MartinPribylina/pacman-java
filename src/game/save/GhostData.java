package src.game.save;

import src.common.CommonField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GhostData implements Serializable {

    public List<CommonField.Direction> path = new ArrayList<>();
    public int type;
    public CommonField startPos;
    public GhostData(int type, CommonField startPos) {
        this.type = type;
        this.startPos = startPos;
    }
}
