package src.game.replay;

import src.MazePresenter;
import src.common.CommonMaze;
import src.common.ElementCreator;
import src.common.readers.maze.MazeFileReader;
import src.common.readers.maze.MazeFileReaderResult;
import src.game.MazeConfigure;
import src.game.save.GameLogging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameReplay extends JPanel{
    private final JButton menu;
    private JButton error;
    private final ActionListener parentListener;
    public GameReplay(ActionListener parentListener, GameLogging gameLogging) {
        this.parentListener = parentListener;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        this.add(header, BorderLayout.NORTH);
        header.setPreferredSize(new Dimension(100, 50));
        header.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.BLACK);
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.PAGE_AXIS));
        this.add(sideBar, BorderLayout.WEST);

        menu = ElementCreator.CreateDefaultButton("Menu", 100, 50, parentListener);
        sideBar.add(menu, BorderLayout.WEST);

        JLabel score = ElementCreator.CreateDefaultLabel("Score: 0");
        score.setHorizontalTextPosition(JLabel.CENTER); // Horizontal text possition
        score.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        header.add(score, BorderLayout.CENTER);


        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(gameLogging.getFilepath(), gameLogging.getMaze().ghosts());
        MazeConfigure mazeConfigure = result.getMazeConfigure();
        CommonMaze maze = mazeConfigure.createMaze();
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

        MazePresenter presenter = new MazePresenter(maze);
        this.add(presenter, BorderLayout.CENTER);

        ReplayLoop rp = new ReplayLoop(gameLogging);
        rp.run();
    }
}
