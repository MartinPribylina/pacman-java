package src.view;

import src.common.CommonField;
import src.common.CommonMazeObject;
import src.common.Observable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FieldView extends JPanel implements Observable.Observer {
    private final CommonField model;
    private final List<ComponentView> objects;
    private int changedModel = 0;

    public FieldView(CommonField model) {
        this.model = model;
        this.objects = new ArrayList<>();
        this.privUpdate();
        model.addObserver(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.objects.forEach((v) -> v.paintComponent(g));
    }

    private void privUpdate() {
        if (this.model.canMove()) {
            this.setBackground(Color.lightGray);
            this.objects.clear();
            if (!this.model.isEmpty()) {
                CommonMazeObject o = this.model.get();
                ComponentView v = o.isPacman() ? new PacmanView(this, this.model.get()) : new GhostView(this, this.model.get());
                this.objects.add(v);
            }

        } else {
            this.setBackground(Color.BLACK);
        }

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

