/*************************
 * Authors: taken from Task 2 https://moodle.vut.cz/mod/folder/view.php?id=310208, further edited by Martin Pribylina
 *
 * FieldView
 ************************/
package src.view;

import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;
import src.common.gfx.MiscGfx;
import src.common.lore.MazeObjectDescriptionBuilder;
import src.game.objects.Ghost;
import src.game.objects.Key;
import src.game.objects.PathField;
import src.gui.IGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * FieldView is class displaying Fields and handling updates, general game actions
 *
 * @author      Martin Pribylina
 */
public class FieldView extends JPanel implements Observable.Observer  {
    private final CommonField model;
    private final List<ComponentView> objects;

    private final IGame game;

    JPanel thisReference;

    /**
     *
     * @param model
     * @param game
     */
    public FieldView(CommonField model, IGame game) {
        this.model = model;
        this.game = game;
        this.objects = new ArrayList<>();
        this.privUpdate();
        model.addObserver(this);
        thisReference = this;

        /**
         * Mouse Listener for Pacman mouse moving and displaying Descriptions
         */
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                game.setPacmanGoalDestinationClick(model);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (objects.isEmpty())
                    return;

                if (model.isEmpty())
                    return;

                Rectangle bounds = getBounds();
                double w = bounds.getWidth();
                double h = bounds.getHeight();
                int diameter = (int)(Math.min(h, w) - Math.min(h, w) / 100 * 10);

                CommonMazeObject o = model.get();

                if (o == null)
                    return;

                MazeObjectDescriptionBuilder builder = new MazeObjectDescriptionBuilder();
                builder = builder.setPosition(getX() + diameter + 100,thisReference.getY() + diameter + 50);
                if (o.isPacman()){
                    builder = builder.pacman();
                    builder = builder.setDimension(new Dimension(150, 130));
                } else if (o.isGhost())
                {
                    builder = builder.ghostType(((Ghost)o).ghostType());
                    builder = builder.setDimension(new Dimension(150, 70));
                } else if (o.isKey()) {
                    builder = builder.key();
                    builder = builder.setDimension(new Dimension(150, 80));
                }
                game.setObjectDescription(builder.build());
                game.refresh();
            }


            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                game.setObjectDescription(null);
                game.refresh();
            }
        });
    }

    /**
     * Adding fancy image to win Field
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.model.canMove()){
            // If this field is target (win field) then Draw some fancy target image
            if (!((PathField)this.model).isTarget())
                return;
            Graphics2D g2 = (Graphics2D)g;
            Rectangle bounds = this.getBounds();
            double w = bounds.getWidth();
            double h = bounds.getHeight();
            double diameter = Math.min(h, w) - Math.min(h, w) / 100 * 10;
            double x = (w - diameter) / 2.0;
            double y = (h - diameter) / 2.0;
            g2.drawImage(MiscGfx.GetTarget((int)diameter), (int) x, (int)y, null);
        }
    }

    /**
     * Paint objects which are on this field
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < this.objects.size(); i++){
            objects.get(i).paintComponent(g);
        }
    }

    /**
     * Set which object should be displayed on this field and update field Gfx
     */
    private void privUpdate() {
        if (this.model.canMove()) {
            this.setBackground(Color.lightGray);
            this.objects.clear();
            if (!this.model.isEmpty()) {
                var objects = this.model.getAll();

                for (int i = 0; i < objects.size(); i++){
                    ComponentView view = null;
                    if (objects.get(i).isPacman()){
                        view = new PacmanView(this, objects.get(i));
                    }else if (objects.get(i).isGhost()){
                        view = new GhostView(this, objects.get(i));
                    }else if (objects.get(i).isKey()){
                        view = new KeyView(this, objects.get(i));
                    }

                    this.objects.add(view);
                }

            }

        } else {
            this.setBackground(Color.BLACK);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
    }

    public final void update(Observable field) {
        this.privUpdate();
    }

}

