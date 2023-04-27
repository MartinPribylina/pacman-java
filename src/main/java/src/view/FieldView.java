package src.main.java.src.view;

import src.main.java.src.common.CommonField;
import src.main.java.src.common.CommonMazeObject;
import src.main.java.src.common.IGhost;
import src.main.java.src.common.Observable;
import src.main.java.src.common.gfx.MiscGfx;
import src.main.java.src.common.lore.MazeObjectDescriptionBuilder;
import src.main.java.src.game.objects.PathField;
import src.main.java.src.gui.IGame;

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

    private final IGame game;

    JPanel thisReference;

    public FieldView(CommonField model, IGame game) {
        this.model = model;
        this.game = game;
        this.objects = new ArrayList<>();
        this.privUpdate();
        model.addObserver(this);
        thisReference = this;

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
                builder = builder.setDimension(new Dimension(150, 200));
                builder = builder.setPosition(getX() + diameter + 100,thisReference.getY() + diameter + 50);
                if (o.isPacman()){
                    builder = builder.pacman();
                } else if (o.isGhost())
                {
                    builder = builder.ghostType(((IGhost)o).ghostType());
                } else if (o.isKey()) {
                    builder = builder.key();
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.model.canMove()){
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < this.objects.size(); i++){
            objects.get(i).paintComponent(g);
        }
    }

    private void privUpdate() {
        if (this.model.canMove()) {
            this.setBackground(Color.lightGray);
            this.objects.clear();
            if (!this.model.isEmpty()) {
                CommonMazeObject o = this.model.get();
                ComponentView v = null;
                if (o.isPacman()){
                    v = new PacmanView(this, this.model.get());
                }else if (o.isGhost()){
                    v = new GhostView(this, this.model.get());
                }else if (o.isKey()){
                    v = new KeyView(this, this.model.get());
                }

                this.objects.add(v);
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

