/*************************
 * Authors: Martin Pribylina
 *
 * Class for ghost object, that can be placed on a field
 ************************/
package src.game.objects;


import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ghost is class for ghost object, that can be placed on a field
 *
 * @author      Martin Pribylina
 */
public class Ghost extends AbstractMazeObject implements CommonMazeObject {

    int ghostType;
    private List<CommonField.Direction> ghostPath = new ArrayList<>();
    public CommonField start;
    public Ghost(CommonField field) {
        super(field);
        start = field;
        Random rand = new Random();
        ghostType = rand.nextInt(4);
    }
    public Ghost(CommonField field, int ghostType, List<CommonField.Direction> ghostPath) {
        super(field);
        this.ghostType = ghostType;
        this.ghostPath = ghostPath;
    }

    @Override
    public void update(Observable observable) {
        this.field = (PathField)observable;
    }

    @Override
    public boolean isPacman() {
        return false;
    }

    @Override
    public int getStepCounter() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        boolean rowsMatch = ((PathField) ((AbstractMazeObject) obj).field).getRow() == ((PathField) field).getRow();
        boolean colsMatch = ((PathField) ((AbstractMazeObject) obj).field).getCol() == ((PathField) field).getCol();
        return rowsMatch && colsMatch;
    }

    @Override
    public boolean move(CommonField.Direction direction)
    {
        ghostPath.add(direction);
        boolean result = super.move(direction);
        if (!result)
            return false;

        if (animStep == 1)
        {
            animStep = 0;
        } else if (animStep == 0) {
            animStep = 1;
        }

        return true;
    }

    public int ghostType() {
        return ghostType;
    }

    @Override
    public boolean isGhost() {
        return true;
    }

    public List<CommonField.Direction> getGhostPath() {
        return ghostPath;
    }
}
