package src.gui;

import src.common.ElementCreator;
import src.common.ImageEditor;
import src.game.save.StatsData;
import src.game.save.StatsSaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Stats extends JPanel {
    private JButton back;

    private final ActionListener parentListener;

    public Stats(ActionListener parentListener){
        this.parentListener = parentListener;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        SetupHeader();
        SetupStats();
        SetupBack();
    }

    private void SetupHeader(){
        JLabel header = new JLabel();
        ImageIcon imageIcon = new ImageIcon(ImageEditor.ScaleImage("lib/pac-man-logo.png", 300));
        header.setIcon(imageIcon);
        header.setPreferredSize(new Dimension(300,150));
        this.add(header, BorderLayout.NORTH);

        header.setVerticalAlignment(JLabel.CENTER); // Set Vertical position within label
        header.setHorizontalAlignment(JLabel.CENTER); // Set Horizontal position within label
    }

    private void SetupStats(){
        StatsData data = StatsSaveManager.LoadStats();

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
        middlePanel.setBackground(Color.BLACK);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20,250,20,250));



        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.add(ElementCreator.CreateDefaultLabel("Max Score"), BorderLayout.WEST);
        panel.add(ElementCreator.CreateDefaultLabel(String.valueOf(data.maxScore)), BorderLayout.EAST);

        middlePanel.add(panel);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.add(ElementCreator.CreateDefaultLabel("Times Played"), BorderLayout.WEST);
        panel.add(ElementCreator.CreateDefaultLabel(String.valueOf(data.timesPlayed)), BorderLayout.EAST);

        middlePanel.add(panel);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.add(ElementCreator.CreateDefaultLabel("Times Won"), BorderLayout.WEST);
        panel.add(ElementCreator.CreateDefaultLabel(String.valueOf(data.timesWon)), BorderLayout.EAST);

        middlePanel.add(panel);



        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.add(ElementCreator.CreateDefaultLabel("Lives lost"), BorderLayout.WEST);
        panel.add(ElementCreator.CreateDefaultLabel(String.valueOf(data.livesLost)), BorderLayout.EAST);

        middlePanel.add(panel);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.add(ElementCreator.CreateDefaultLabel("Steps taken"), BorderLayout.WEST);
        panel.add(ElementCreator.CreateDefaultLabel(String.valueOf(data.stepsTaken)), BorderLayout.EAST);

        middlePanel.add(panel);

        this.add(middlePanel, BorderLayout.CENTER);
    }

    private void SetupBack(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        back = ElementCreator.CreateDefaultButton("Back", 150, 50, parentListener);
        panel.add(back);

        this.add(panel, BorderLayout.SOUTH);
    }

    public JButton getBack() {
        return back;
    }
}
