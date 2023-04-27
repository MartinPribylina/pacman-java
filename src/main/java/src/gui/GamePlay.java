package src.main.java.src.gui;

import src.main.java.src.MazePresenter;
import src.main.java.src.common.CommonField;
import src.main.java.src.common.CommonMaze;
import src.main.java.src.common.ElementCreator;
import src.main.java.src.common.readers.maze.MazeFileReader;
import src.main.java.src.common.readers.maze.MazeFileReaderResult;
import src.main.java.src.game.MazeConfigure;
import src.main.java.src.game.core.FrameBasedGameLoop;
import src.main.java.src.game.core.GameController;
import src.main.java.src.game.core.GameLoop;
import src.main.java.src.game.save.GameLogging;

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


public class GamePlay extends Game implements ActionListener{

    private final JButton menu;
    private JButton pause;
    private JButton error;
    private JButton playAgain;
    private final ActionListener parentListener;
    private FrameBasedGameLoop gameLoop;
    private GameController gameController;
    private MazePresenter presenter;

    public GamePlay(ActionListener parentListener, String filePath){
        this.parentListener = parentListener;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
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

        JLabel steps = ElementCreator.CreateDefaultLabel("Steps: 0");
        header.add(steps, BorderLayout.EAST);

        JLabel lives = ElementCreator.CreateDefaultLabel("Lives: 0");
        header.add(lives, BorderLayout.WEST);

        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(filePath, null);
        MazeConfigure mazeConfigure = result.getMazeConfigure();
        CommonMaze maze = mazeConfigure.createMaze();

        if (result.getErrorCode() != 0 || (mazeConfigure != null && !mazeConfigure.isReadingSuccess()) || maze == null)
        {
            ShowError(result);
            return;
        }


        presenter = new MazePresenter(maze, this);
        this.add(presenter, BorderLayout.CENTER);

        GameLogging gameLogging = new GameLogging(filePath);

        gameController = new GameController(maze, gameLogging, this);
        gameController.setLives(lives);
        gameController.setScore(score);
        gameController.setSteps(steps);

        gameLoop = new FrameBasedGameLoop(gameController);

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "upAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", gameLoop.getPlayerActions().getUpAction());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "downAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", gameLoop.getPlayerActions().getDownAction());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "leftAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", gameLoop.getPlayerActions().getLeftAction());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "rightAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", gameLoop.getPlayerActions().getRightAction());

        gameLoop.run();
    }

    private void ShowError(MazeFileReaderResult result){
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
        errorPanel.setBackground(Color.BLACK);
        this.add(errorPanel, BorderLayout.CENTER);

        JLabel errorLabel = ElementCreator.CreateDefaultLabel("Error: " + result.getErrorMessage());
        errorPanel.setAlignmentX(CENTER_ALIGNMENT);
        errorPanel.add(errorLabel);

        JPanel wrap = new JPanel();
        wrap.setBackground(Color.BLACK);
        error = ElementCreator.CreateDefaultButton("OK", 100, 50, parentListener);
        error.setAlignmentX(CENTER_ALIGNMENT);
        wrap.add(error);
        errorPanel.add(wrap);
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

    @Override
    public void endGame(boolean isVictory) {

        JPanel endGame = new JPanel();
        endGame.setLayout(new BoxLayout(endGame, BoxLayout.PAGE_AXIS));
        endGame.setBackground(Color.BLACK);
        this.add(endGame, BorderLayout.SOUTH);

        JLabel errorLabel = ElementCreator.CreateDefaultLabel(isVictory ? "You won!" : "You lost!");
        errorLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        errorLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        endGame.add(errorLabel);

        JPanel wrap = new JPanel();
        wrap.setBackground(Color.BLACK);
        playAgain = ElementCreator.CreateDefaultButton("Play Again!", 250, 50, parentListener);
        playAgain.setAlignmentX(CENTER_ALIGNMENT);
        wrap.add(playAgain);
        wrap.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        endGame.add(wrap);
    }

    public JButton getMenu() {
        return menu;
    }

    public JButton getError() {
        return error;
    }
    public GameLoop getGameLoop() { return gameLoop; }

    public JButton getPlayAgain() {
        return playAgain;
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

    @Override
    public void setPacmanGoalDestinationClick(CommonField field) {
        if (!field.canMove()) {
            System.out.println("Can't move there");
            return;
        }
        gameController.setPacmanGoalDestination(field);
    }
}
