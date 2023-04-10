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

        //Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
        //g2.setColor(Color.green);
        //g2.fill(ellipse);
        //g2.setColor(Color.black);
        //g2.setFont(new Font("Serif", 1, 20));
        //g2.drawString("(" + this.model.getLives() + ")", (int)(x + diameter) / 2, (int)(y + diameter + 10.0) / 2 + 5);
    }
}
