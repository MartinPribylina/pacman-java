/*************************
 * Authors: Martin Pribylina
 *
 * Class for ghost display
 ************************/
package src.view;

import src.common.CommonMazeObject;
import src.common.gfx.GhostGfx;
import src.game.objects.Ghost;

import java.awt.*;

/**
 * GhostView is class inheriting MazeObjectView and overriding GetImage in order to draw different types of Ghosts
 *
 * @author      Martin Pribylina
 */
public class GhostView extends MazeObjectView {

    /**
     * @see MazeObjectView
     * @param parent
     * @param mazeObject
     */
    public GhostView(FieldView parent, CommonMazeObject mazeObject) {
        super(parent, mazeObject);
    }

    /**
     *
     * @param size Width and height of image
     * @return
     */
    @Override
    public Image GetImage(double size) {
        return GhostGfx.GetImage(mazeObject.lastMove(), (int) size, ((Ghost)mazeObject).ghostType());
    }
}

