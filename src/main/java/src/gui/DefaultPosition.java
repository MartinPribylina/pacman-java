package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultPosition extends JPanel {
    private JLabel startLabel;
    private JButton begining;
    private JButton end;
    private ActionListener parentListener;

    public DefaultPosition(ActionListener parentListener){
        this.parentListener = parentListener;

        startLabel = new JLabel("Choose a starting position");
        begining = new JButton("Start");
        end = new JButton("End");

        this.setBackground(Color.BLACK);

        startLabel.setForeground(Color.white);
        startLabel.setBackground(Color.BLUE);
        startLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        startLabel.setBounds(120, 150, 400, 50);

        begining.setBounds(180, 240, 100, 50);
        begining.setFont(new Font("Arial", Font.PLAIN, 30));

        end.setBounds(300, 240, 100, 50);
        end.setFont(new Font("Arial", Font.PLAIN, 30));

        begining.addActionListener(parentListener);
        end.addActionListener(parentListener);

        this.setLayout(null);
        this.add(startLabel);
        this.add(begining);
        this.add(end);
    }

    public JButton getBegining() {
        return begining;
    }

    public JButton getEnd() {
        return end;
    }
}
