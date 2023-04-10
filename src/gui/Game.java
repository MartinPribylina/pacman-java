package src.gui;

import src.MazePresenter;
import src.common.CommonMaze;
import src.common.ElementCreator;
import src.common.readers.maze.MazeFileReader;
import src.common.readers.maze.MazeFileReaderResult;
import src.game.MazeConfigure;
import src.game.core.FrameBasedGameLoop;
import src.game.core.GameController;
import src.view.ComponentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;


public class Game extends JPanel implements ActionListener{

    private final JButton menu;
    private JButton pause;
    private JButton error;
    private final ActionListener parentListener;
    private FrameBasedGameLoop gameLoop;

    private MazeObjectDescription objectDescription;

    public Game(ActionListener parentListener, String filePath){
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
        pause = ElementCreator.CreateDefaultButton("Pause", 100, 50, this);
        sideBar.add(menu);
        sideBar.add(pause);

        JLabel score = ElementCreator.CreateDefaultLabel("Score: 0");
        score.setHorizontalTextPosition(JLabel.CENTER); // Horizontal text possition
        score.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        header.add(score, BorderLayout.CENTER);

        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(filePath);
        MazeConfigure mazeConfigure = result.getMazeConfigure();
        CommonMaze maze = mazeConfigure.createMaze();

        if (result.getErrorCode() != 0 || (mazeConfigure != null && !mazeConfigure.isReadingSuccess()) || maze == null)
        {
            JPanel errorPanel = new JPanel();
            errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
            errorPanel.setBackground(Color.BLACK);
            this.add(errorPanel, BorderLayout.CENTER);

            JLabel errorLabel = ElementCreator.CreateDefaultLabel("Error: " + result.getErrorMessage());
            errorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            errorPanel.add(errorLabel);

            JPanel wrap = new JPanel();
            wrap.setBackground(Color.BLACK);
            error = ElementCreator.CreateDefaultButton("OK", 100, 50, parentListener);
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrap.add(error);
            errorPanel.add(wrap);

            return;
        }


        MazePresenter presenter = new MazePresenter(maze, this);
        this.add(presenter, BorderLayout.CENTER);

        GameController gameController = new GameController(maze);

        gameLoop = new FrameBasedGameLoop(gameController);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "upAction");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", gameLoop.getPlayerActions().getUpAction());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "downAction");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", gameLoop.getPlayerActions().getDownAction());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "leftAction");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", gameLoop.getPlayerActions().getLeftAction());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "rightAction");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", gameLoop.getPlayerActions().getRightAction());

        gameLoop.run();
    }

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

    public JButton getMenu() {
        return menu;
    }

    public JButton getError() {
        return error;
    }

    public void setObjectDescription(MazeObjectDescription objectDescription) {
        this.objectDescription = objectDescription;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pause){
            if (gameLoop.isGameRunning()){
                gameLoop.stop();
                pause.setText("Play");
            }else{
                gameLoop.run();
                pause.setText("Pause");
            }
        }
    }
}
