/*************************
 * Authors: Martin Pribylina
 *
 * ----------
 ************************/
package src.gui;

import src.common.CommonField;
import src.view.MazeObjectDescription;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * Play is abstract base class holding common functionality for GamePlay and GameReplay
 *
 * @author      Martin Pribylina
 */
public abstract class Game extends JPanel implements IGame {
    public MazeObjectDescription objectDescription;

    /**
     * Sets object description which should be displayed
     * @param objectDescription
     */
    @Override
    public void setObjectDescription(MazeObjectDescription objectDescription) {
        this.objectDescription = objectDescription;
    }

    /**
     * Repaints window
     */
    public void refresh(){
        repaint();
    }

    @Override
    public void setPacmanGoalDestinationClick(CommonField field) {

    }

    @Override
    public void endGame(boolean isVictory) {

    }

    /**
     * Renders object description
     * @param g  the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g){
        super.paint(g);

        if (objectDescription == null)
            return;

        Graphics2D g2 = (Graphics2D)g;

        Rectangle2D rect = new Rectangle2D.Double(objectDescription.x, objectDescription.y,
                objectDescription.size.width, objectDescription.size.height);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(rect);
        g2.setFont(new Font("Serif", 1, 20));
        g2.setColor(Color.WHITE);
        g2.drawString(objectDescription.title, objectDescription.x + 5, objectDescription.y + 20);
        g2.setFont(new Font("Serif", 1, 10));

        AttributedString as = new AttributedString(objectDescription.description);
        AttributedCharacterIterator aci = as.getIterator();
        FontRenderContext frc = g2.getFontRenderContext();
        LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);

        int x = objectDescription.x + 5;
        int y = objectDescription.y + 40;

        while (lbm.getPosition() < aci.getEndIndex()) {
            TextLayout tl = lbm.nextLayout(objectDescription.size.width);
            tl.draw(g2, x, y + tl.getAscent());
            y += tl.getDescent() + tl.getLeading() + tl.getAscent();
        }


    }
}
