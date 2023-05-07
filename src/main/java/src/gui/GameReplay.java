/*************************
 * Authors: Samuel Gall
 *
 * Class that creates a replay GUI and starts the replay loop
 ************************/
package src.gui;

import src.MazePresenter;
import src.common.CommonField;
import src.common.CommonMaze;
import src.common.ElementCreator;
import src.common.readers.maze.MazeFileReader;
import src.common.readers.maze.MazeFileReaderResult;
import src.game.MazeConfigure;
import src.game.replay.ReplayDetails;
import src.game.replay.ReplayLoop;
import src.game.replay.GhostData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

/**
 * GameReplay is a class displaying and handling Replay functions
 *
 * @author      Samuel Gall
 */
public class GameReplay extends AbstractGame implements ActionListener{
    private final JButton menu;
    private JButton error;
    private JButton stepForward;
    private JButton stepBackwards;
    private JButton playForward;
    private JButton playBackwards;
    private JButton playPause;
    private File logFile;
    private String filepath;
    private int time;
    private List<CommonField.Direction> pacmanPath = new ArrayList<>();
    private List<GhostData> ghostData = new ArrayList<>();
    private ReplayDetails replayDetails = new ReplayDetails();
    private MazePresenter presenter;
    private CommonMaze maze;
    private final ActionListener parentListener;
    private ReplayLoop rp;
    private DefaultPosition defaultPosition;

    /**
     *
     * @param parentListener used for handling button actions outside this class in MainFrame
     * @param logFile Log file path for reading replay details
     */
    public GameReplay(ActionListener parentListener, File logFile) {
        this.parentListener = parentListener;
        this.logFile = logFile;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        this.add(header, BorderLayout.NORTH);
        header.setPreferredSize(new Dimension(100, 50));
        header.setLayout(new BorderLayout());

        JLabel currentStep = ElementCreator.CreateDefaultLabel("Current step: 0");
        currentStep.setHorizontalTextPosition(JLabel.CENTER);
        currentStep.setVerticalAlignment(JLabel.CENTER);
        currentStep.setHorizontalAlignment(JLabel.CENTER);
        header.add(currentStep, BorderLayout.CENTER);

        JLabel steps = ElementCreator.CreateDefaultLabel("Steps: 0");
        header.add(steps, BorderLayout.EAST);

        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.BLACK);
        sideBar.setPreferredSize(new Dimension(100, 700));
        sideBar.setMaximumSize(new Dimension(100, 700));
        sideBar.setMinimumSize(new Dimension(100, 700));
        sideBar.setLayout(new FlowLayout());
        this.add(sideBar, BorderLayout.WEST);

        menu = ElementCreator.CreateDefaultButton("Menu", 100, 50, parentListener);
        sideBar.add(menu, BorderLayout.PAGE_START);
        stepForward = ElementCreator.CreateButton(">", 45, 30, this);
        stepBackwards = ElementCreator.CreateButton("<", 45, 30, this);
        playForward = ElementCreator.CreateDefaultButton("\u00BB", 45, 30, this);
        playBackwards = ElementCreator.CreateDefaultButton("\u00AB", 45, 30, this);
        playPause = ElementCreator.CreateDefaultButton("Pause", 100, 30, this);

        sideBar.add(stepBackwards, BorderLayout.WEST);
        sideBar.add(stepForward, BorderLayout.WEST);
        sideBar.add(playBackwards, BorderLayout.WEST);
        sideBar.add(playForward, BorderLayout.WEST);
        sideBar.add(playPause, BorderLayout.WEST);

        try {
            Scanner myReader = new Scanner(logFile);
            while (!myReader.nextLine().equals("*")) {}
            filepath = myReader.nextLine();
            time = Integer.valueOf(myReader.nextLine());

            pacmanPath = getPath(myReader.nextLine());

            while (myReader.hasNextLine()){
                String line = myReader.nextLine();
                int type = Integer.valueOf(String.valueOf(line.charAt(0)));
                int row = Integer.valueOf(String.valueOf(line.charAt(3)));
                int col = Integer.valueOf(String.valueOf(line.charAt(5)));

                GhostData ghost = new GhostData(type, row, col);
                ghost.setPath(getPath(line.substring(line.indexOf('['))));
                ghostData.add(ghost);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(filepath, ghostData);
        MazeConfigure mazeConfigure = result.getMazeConfigure();
        maze = mazeConfigure.createMaze();

        replayDetails.setMaze(maze);
        replayDetails.setTime(time);
        replayDetails.setPacmanPath(pacmanPath);
        replayDetails.setGhostData(ghostData);

        if (maze == null)
        {
            JPanel errorPanel = new JPanel();
            errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
            errorPanel.setBackground(Color.BLACK);
            this.add(errorPanel, BorderLayout.CENTER);

            JPanel wrap = new JPanel();
            wrap.setBackground(Color.BLACK);
            error = ElementCreator.CreateDefaultButton("OK", 100, 50, parentListener);
            error.setAlignmentX(CENTER_ALIGNMENT);
            wrap.add(error);
            errorPanel.add(wrap);

            return;
        }

        defaultPosition = new DefaultPosition(this);

        this.add(defaultPosition, BorderLayout.CENTER);
        setVisible(true);

        steps.setText("Steps: " + time);
        rp = new ReplayLoop(replayDetails);
        rp.setCurrentStep(currentStep);
        rp.setBackwards(stepBackwards);
        rp.setForward(stepForward);
        rp.setPlayForward(playForward);
        rp.setPlayBackwards(playBackwards);
        rp.setPlayPause(playPause);
        rp.run();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == defaultPosition.getBegining()){
            rp.setStep(-1);
            defaultPosition.setVisible(false);
            this.remove(defaultPosition);
            presenter = new MazePresenter(maze, this);
            this.add(presenter);
        } else if (e.getSource() == defaultPosition.getEnd()) {
            rp.setStep(-1);
            defaultPosition.setVisible(false);
            this.remove(defaultPosition);
            presenter = new MazePresenter(maze, this);
            this.add(presenter);
            rp.jumpToEnd();
        } else if (e.getSource() == stepBackwards) {
            rp.back = true;
            synchronized(rp) {
                rp.notify();
            }
        } else if (e.getSource() == stepForward) {
            rp.back = false;
            synchronized(rp) {
                rp.notify();
            }
        } else if (e.getSource() == playForward) {
            rp.back = false;
            rp.forward = true;
            synchronized(rp) {
                rp.notify();
            }
            stepBackwards.setEnabled(false);
            stepForward.setEnabled(false);
            playBackwards.setEnabled(false);
            playForward.setEnabled(false);
        } else if (e.getSource() == playBackwards) {
            rp.back = true;
            rp.backwards = true;
            synchronized(rp) {
                rp.notify();
            }
            stepBackwards.setEnabled(false);
            stepForward.setEnabled(false);
            playForward.setEnabled(false);
            playBackwards.setEnabled(false);
        } else if (e.getSource() == playPause) {
            rp.backwards = false;
            rp.forward = false;
            stepBackwards.setEnabled(true);
            stepForward.setEnabled(true);
            playForward.setEnabled(true);
            playBackwards.setEnabled(true);
        }
    }
    public List<CommonField.Direction> getPath(String line){
        List<CommonField.Direction> path = new ArrayList<>();
        for (int i = 0; i<line.length(); i++){
            if (line.charAt(i) == ' ' || line.charAt(i) == '['){
                switch (line.charAt(i+1)){
                    case 'n' -> {
                        path.add(null);
                        if (line.charAt(i+5) != '\0') i+=5;
                    }
                    case 'U' -> {
                        path.add(CommonField.Direction.UP);
                        if (line.charAt(i+3) != '\0') i+=3;
                    }
                    case 'D' -> {
                        path.add(CommonField.Direction.DOWN);
                        if (line.charAt(i+5) != '\0') i+=5;
                    }
                    case 'L' -> {
                        path.add(CommonField.Direction.LEFT);
                        if (line.charAt(i+5) != '\0')i+=5;
                    }
                    case 'R' -> {
                        path.add(CommonField.Direction.RIGHT);
                        if (line.charAt(i+6) != '\0') i+=6;
                    }
                }
            }
        }
        return path;
    }


    public JButton getMenu() {
        return menu;
    }
}
