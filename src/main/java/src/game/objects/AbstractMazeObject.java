/*************************
 * Authors: Martin Pribylina, Samuel Gall
 *
 * Abstract class for general maze object, that can be placed on a field
 ************************/
package src.game.objects;


import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

/**
 * AbstractMazeObject is abstract class for general maze object, that can be placed on a field
 *
 * @author      Martin Pribylina
 */
public abstract class AbstractMazeObject implements CommonMazeObject, Observable.Observer {

    public CommonField field;

    public AbstractMazeObject(CommonField field){
        this.field = field;
    }

    @Override
    public boolean canMove(CommonField.Direction direction) {
        return this.field.nextField(direction).canMove();
    }

    private CommonField.Direction lastMove;

    int animStep = 0;

    @Override
    public boolean move(CommonField.Direction direction) {
        CommonField nextField = this.field.nextField(direction);
        if(!nextField.canMove()) return false;
        field.removeObserver(this);
        field.notifyObservers();
        nextField.addObserver(this);
        field = nextField;
        nextField.notifyObservers();
        lastMove = direction;
        return true;
    }

    @Override
    public CommonField getField() {
       return this.field;
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public CommonField.Direction lastMove(){
        return lastMove;
    }
    public void setLastMove(CommonField.Direction dir) {lastMove = dir;}
    @Override
    public int animStep(){
        return animStep;
    }

    @Override
    public boolean isPacman() {
        return false;
    }

    @Override
    public boolean isGhost() {
        return false;
    }

    @Override
    public int getStepCounter() {
        return 0;
    }
}
