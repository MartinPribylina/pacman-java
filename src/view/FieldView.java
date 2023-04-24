package src.view;

import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.IGhost;
import src.common.Observable;
import src.common.lore.MazeObjectDescriptionBuilder;
import src.gui.Game;
import src.gui.MazeObjectDescription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FieldView extends JPanel implements Observable.Observer  {
    private final CommonField model;
    private final List<ComponentView> objects;
    private int changedModel = 0;

    private boolean displayInfo;

    private final Game game;

    JPanel thisReference;

    public FieldView(CommonField model, Game game) {
        this.model = model;
        this.game = game;
        this.objects = new ArrayList<>();
        this.privUpdate();
        model.addObserver(this);
        thisReference = this;

        this.addMouseListener(new MouseAdapter() {
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
                builder = builder.setDimension(new Dimension(150, 200));
                builder = builder.setPosition(getX() + diameter + 100,thisReference.getY() + diameter + 50);
                if (!o.isPacman())
                {
                    builder = builder.ghostType(((IGhost)o).ghostType());
                }
                game.setObjectDescription(builder.build());
                game.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                game.setObjectDescription(null);
                game.repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.objects.forEach((v) -> v.paintComponent(g));
    }

    private void privUpdate() {
        if (this.model.canMove()) {
            this.setBackground(Color.lightGray);
            if (!this.model.isEmpty()) {
                CommonMazeObject o = this.model.get();
                ComponentView v = o.isPacman() ? new PacmanView(this, this.model.get()) : new GhostView(this, this.model.get());
                this.objects.add(v);
            } else {
                this.objects.clear();
            }

        } else {
            this.setBackground(Color.BLACK);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
    }

    public final void update(Observable field) {
        ++this.changedModel;
        this.privUpdate();
    }

    public int numberUpdates() {
        return this.changedModel;
    }

    public void clearChanged() {
        this.changedModel = 0;
    }

    public CommonField getField() {
        return this.model;
    }

}

