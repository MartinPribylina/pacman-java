/*************************
 * Authors: Martin Pribylina
 *
 * Class for ghost display
 ************************/
package src.view;

import src.common.CommonMazeObject;
import src.common.gfx.MiscGfx;

import java.awt.*;

/**
 * KeyView is class inheriting MazeObjectView and overriding GetImage in order to draw Key
 *
 * @author      Martin Pribylina
 */
public class KeyView extends MazeObjectView {

    /**
     * @see MazeObjectView
     * @param parent
     * @param mazeObject
     */
    public KeyView(FieldView parent, CommonMazeObject mazeObject) {
        super(parent, mazeObject);
    }

    /**
     *
     * @param size Width and height of image
     * @return
     */
    @Override
    public Image GetImage(double size) {
        return MiscGfx.GetKey((int) size);
    }
}
