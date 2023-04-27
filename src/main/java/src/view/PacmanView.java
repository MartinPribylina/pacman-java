/*************************
 * Authors: Martin Pribylina
 *
 * Class for ghost display
 ************************/
package src.view;

import src.common.CommonMazeObject;
import src.common.gfx.PacmanGfx;

import java.awt.*;
import java.io.Serializable;

public class PacmanView implements ComponentView, Serializable {
    private final CommonMazeObject model;
    private final FieldView parent;


    public PacmanView(FieldView parent, CommonMazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.min(h, w) - Math.min(h, w) / 100 * 10;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        g2.drawImage(PacmanGfx.GetImage(model.lastMove(), (int)diameter, model.animStep()), (int) x, (int)y, null);
    }
}
