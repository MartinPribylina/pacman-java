package src.gui;

import src.MazePresenter;
import src.common.CommonMaze;
import src.common.ElementCreator;
import src.common.readers.maze.MazeFileReader;
import src.common.readers.maze.MazeFileReaderResult;
import src.game.MazeConfigure;
import src.game.replay.ReplayLoop;
import src.game.save.GameLogging;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class GameReplay extends JPanel implements ActionListener{
    private final JButton menu;
    private JButton error;
    private JButton forward;
    private JButton backwards;
    private JButton playForward;
    private JButton playBackwards;
    private GameLogging gameLogging;
    private MazePresenter presenter;
    private CommonMaze maze;
    private final ActionListener parentListener;
    private ReplayLoop rp;
    private DefaultPosition defaultPosition;
    public GameReplay(ActionListener parentListener, GameLogging gameLogging) {
        this.parentListener = parentListener;
        this.gameLogging = gameLogging;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        this.add(header, BorderLayout.NORTH);
        header.setPreferredSize(new Dimension(100, 50));
        header.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.BLACK);
        sideBar.setPreferredSize(new Dimension(100, 700));
        sideBar.setMaximumSize(new Dimension(100, 700));
        sideBar.setMinimumSize(new Dimension(100, 700));
        sideBar.setLayout(new FlowLayout());
        this.add(sideBar, BorderLayout.WEST);

        menu = ElementCreator.CreateDefaultButton("Menu", 100, 50, parentListener);
        sideBar.add(menu, BorderLayout.PAGE_START);
        forward = ElementCreator.CreateButton(">", 45, 30, this);
        backwards = ElementCreator.CreateButton("<", 45, 30, this);
        playForward = ElementCreator.CreateDefaultButton("\u00BB", 45, 30, this);
        playBackwards = ElementCreator.CreateDefaultButton("\u00AB", 45, 30, this);

        sideBar.add(backwards, BorderLayout.WEST);
        sideBar.add(forward, BorderLayout.WEST);
        sideBar.add(playBackwards, BorderLayout.WEST);
        sideBar.add(playForward, BorderLayout.WEST);

        JLabel score = ElementCreator.CreateDefaultLabel("Score: 0");
        score.setHorizontalTextPosition(JLabel.CENTER); // Horizontal text possition
        score.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        header.add(score, BorderLayout.CENTER);

        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(gameLogging.getFilepath(), gameLogging.getGhostsData());
        MazeConfigure mazeConfigure = result.getMazeConfigure();
        maze = mazeConfigure.createMaze();
        gameLogging.setMaze(maze);

        if (maze == null)
        {
            JPanel errorPanel = new JPanel();
            errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
            errorPanel.setBackground(Color.BLACK);
            this.add(errorPanel, BorderLayout.CENTER);

            JPanel wrap = new JPanel();
            wrap.setBackground(Color.BLACK);
            error = ElementCreator.CreateDefaultButton("OK", 100, 50, parentListener);
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrap.add(error);
            errorPanel.add(wrap);

            return;
        }

        defaultPosition = new DefaultPosition(this);

        this.add(defaultPosition, BorderLayout.CENTER);
        setVisible(true);

        rp = new ReplayLoop(gameLogging);
        rp.run();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == defaultPosition.getBegining()){
            rp.setStep(-1);
            defaultPosition.setVisible(false);
            this.remove(defaultPosition);
            presenter = new MazePresenter(maze);
            this.add(presenter);
        } else if (e.getSource() == defaultPosition.getEnd()) {
            rp.setStep(-1);
            defaultPosition.setVisible(false);
            this.remove(defaultPosition);
            presenter = new MazePresenter(maze);
            this.add(presenter);
            rp.play(0);
        } else if (e.getSource() == backwards) {
            rp.back = true;
            synchronized(rp) {
                rp.notify();
            }
        } else if (e.getSource() == forward) {
            rp.back = false;
            synchronized(rp) {
                rp.notify();
            }
        } else if (e.getSource() == playForward) {
            rp.back = false;
            rp.play(300);
        } else if (e.getSource() == playBackwards) {
            rp.back = true;
            rp.play(300);
        }
    }
    public JButton getMenu() {
        return menu;
    }
}
