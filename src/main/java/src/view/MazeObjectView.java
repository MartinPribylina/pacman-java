/*************************
 * Authors: taken from Task 2 https://moodle.vut.cz/mod/folder/view.php?id=310208, further edited by Martin Pribylina
 *
 * MazeObjectView is abstract base class for all Maze Objects which we want to display on Fields
 ************************/
package src.view;

import src.common.CommonMazeObject;
import src.common.gfx.GhostGfx;
import src.game.objects.Ghost;

import java.awt.*;

/**
 * MazeObjectView is abstract base class for all Maze Objects which we want to display on Fields
 *
 * @author      Martin Pribylina
 */
public abstract class MazeObjectView implements ComponentView {
    protected final CommonMazeObject mazeObject;
    private final FieldView parent;

    /**
     *
     * @param parent        Referencing where is the tile and how big is the tile to adjust position and size of Object Image
     * @param mazeObject    Referencing additional details to draw correct image
     */
    public MazeObjectView(FieldView parent, CommonMazeObject mazeObject) {
        this.mazeObject = mazeObject;
        this.parent = parent;
    }

    /**
     * Calculates size and position of image to draw and draws image
     * @param g Gfx context
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.min(h, w) - Math.min(h, w) / 100 * 10;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(GetImage(diameter), (int) x, (int)y, null);
    }

    /**
     * 
     * @param size Width and height of image
     * @return Image to draw
     */
    public Image GetImage(double size){
        return GhostGfx.GetImage(mazeObject.lastMove(), (int) size, ((Ghost) mazeObject).ghostType());
    }
}


