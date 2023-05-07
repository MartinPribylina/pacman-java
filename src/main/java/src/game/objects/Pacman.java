/*************************
 * Authors: Martin Pribylina
 *
 * Class for pacman object, that can be placed on a field
 ************************/
package src.game.objects;


import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

/**
 * Pacman is class for pacman object, that can be placed on a field
 *
 * @author      Martin Pribylina
 */
public class Pacman extends AbstractMazeObject implements CommonMazeObject {

    private int lives;
    private int stepCounter;

    boolean add = true;

    private boolean hasKey;

    private boolean won = false;

    public Pacman(CommonField field) {
        super(field);
        lives = 3;
    }


    @Override
    public boolean isPacman() {
        return true;
    }

    @Override
    public int getLives() {
        return this.lives;
    }


    @Override
    public void update(Observable observable) {
        this.field = (PathField)observable;

        if (((PathField)this.field).isTarget() && hasKey)
        {
            won = true;
        }

        if (!((PathField) observable).isEmpty()){
            Ghost ghost = new Ghost(field);
            if(((PathField) observable).contains(ghost)){
                lives--;
            }
        }

        if (!((PathField) observable).isEmpty()){
            var objects = ((PathField) observable).getAll();

            for (CommonMazeObject mazeObject :
                    objects) {
                if(mazeObject instanceof Key)
                {
                    hasKey = true;
                    ((Key) mazeObject).field.removeObserver((Observable.Observer) mazeObject);
                }
            }
        }
    }

    @Override
    public boolean move(CommonField.Direction direction)
    {
        boolean result = super.move(direction);
        if (!result)
            return false;

        if (add)
            animStep++;
        else animStep--;
        if (animStep > 2 || animStep < 0){
            animStep = 1;
            add = !add;
        }

        stepCounter++;

        return true;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public boolean isWon() {
        return won;
    }
}
