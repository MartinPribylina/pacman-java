package src.game.objects;

import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

public class Key extends MazeObject implements CommonMazeObject{
    public Key(CommonField field) {
        super(field);
    }

    @Override
    public void update(Observable observable) {
        if (!((PathField) observable).isEmpty()){
            Pacman pacman = ((PathField) observable).getPacman();
            if(pacman != null){
                pacman.setHasKey(true);
                field.removeObserver(this);
            }
        }
    }
    @Override
    public boolean isKey() {
        return true;
    }
}
