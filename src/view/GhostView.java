package src.view;

import src.common.CommonMazeObject;
import src.common.IGhost;
import src.common.gfx.GhostGfx;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class GhostView implements ComponentView, Serializable {
    private final CommonMazeObject model;
    private final FieldView parent;

    public GhostView(FieldView parent, CommonMazeObject m) {
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

        g2.drawImage(GhostGfx.GetImage(model.lastMove(), (int)diameter, ((IGhost)model).ghostType()), (int) x, (int)y, null);
    }
}

