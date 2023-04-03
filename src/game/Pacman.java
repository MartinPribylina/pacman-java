package src.game;


import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

public class Pacman extends MazeObject implements CommonMazeObject {

    private int lives;

    boolean add = true;


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
        if (!((PathField) observable).isEmpty()){
            Ghost ghost = new Ghost(field);
            if(((PathField) observable).contains(ghost)){
                lives--;
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

        return true;
    }
}
