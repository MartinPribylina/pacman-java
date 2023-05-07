/*************************
 * Authors: Martin Pribylina
 *
 * Class for key object, that can be placed on a field
 ************************/
package src.game.objects;

import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

/**
 * Key is class for key object, that can be placed on a field
 *
 * @author      Martin Pribylina
 */
public class Key extends AbstractMazeObject implements CommonMazeObject{
    public Key(CommonField field) {
        super(field);
    }

    @Override
    public void update(Observable observable) {
    }
    @Override
    public boolean isKey() {
        return true;
    }

}
