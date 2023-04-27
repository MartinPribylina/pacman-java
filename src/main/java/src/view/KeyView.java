package src.main.java.src.view;

import src.main.java.src.common.CommonMazeObject;
import src.main.java.src.common.gfx.MiscGfx;

import java.awt.*;
import java.io.Serializable;

public class KeyView implements ComponentView, Serializable {
    private final CommonMazeObject model;
    private final FieldView parent;


    public KeyView(FieldView parent, CommonMazeObject m) {
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
        g2.drawImage(MiscGfx.GetKey((int)diameter), (int) x, (int)y, null);
    }
}
