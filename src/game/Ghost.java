package src.game;


import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.IGhost;
import src.common.Observable;

import java.util.Random;

public class Ghost extends MazeObject implements CommonMazeObject, IGhost {

    int ghostType;
    public Ghost(CommonField field) {
        super(field);

        Random rand = new Random();
        ghostType = rand.nextInt(4);
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
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        boolean rowsMatch = ((PathField) ((MazeObject) obj).field).getRow() == ((PathField) field).getRow();
        boolean colsMatch = ((PathField) ((MazeObject) obj).field).getCol() == ((PathField) field).getCol();
        return rowsMatch && colsMatch;
    }

    @Override
    public boolean move(CommonField.Direction direction)
    {
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

    @Override
    public int ghostType() {
        return ghostType;
    }
}
