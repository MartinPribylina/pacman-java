/*************************
 * Authors: taken from Task 2 https://moodle.vut.cz/mod/folder/view.php?id=310208, further edited by Martin Pribylina
 *
 * PacmanView is class for ghost display
 ************************/

package src.view;

import src.common.CommonMazeObject;
import src.common.gfx.PacmanGfx;

import java.awt.*;

/**
 * PacmanView is class inheriting MazeObjectView and overriding GetImage in order to draw Pacman with animation
 *
 * @author      Martin Pribylina
 */
public class PacmanView extends MazeObjectView {

    /**
     * @see MazeObjectView
     * @param parent
     * @param mazeObject
     */
    public PacmanView(FieldView parent, CommonMazeObject mazeObject){
        super(parent, mazeObject);
    }

    /**
     *
     * @param size Width and height of image
     * @return
     */
    @Override
    public Image GetImage(double size) {
        return PacmanGfx.GetImage(mazeObject.lastMove(), (int) size, mazeObject.animStep());
    }
}
