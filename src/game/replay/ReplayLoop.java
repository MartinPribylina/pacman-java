package src.game.replay;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.Ghost;
import src.game.save.GameLogging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReplayLoop {
    private GameLogging gameLogging;
    public ReplayLoop(GameLogging gameLogging){
        this.gameLogging = gameLogging;
    }
    public void run() {
        Thread replayThread = new Thread(this::processReplayLoop);
        replayThread.start();
    }

    protected void processReplayLoop(){
        CommonMaze maze = gameLogging.getMaze();
        List<CommonField.Direction> pacmanDirection = gameLogging.getPacmanDirection();
        for (int i = 1; i < gameLogging.getTime(); i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                movePacman(maze, pacmanDirection.get(i));
                moveGhosts(maze, i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void movePacman(CommonMaze maze, CommonField.Direction pacmanDirection) {
        CommonMazeObject pacman = maze.pacman();
        if (pacmanDirection != null) {
            pacman.move(pacmanDirection);
        }
    }
    public void moveGhosts(CommonMaze maze, int i) {
        List<CommonMazeObject> ghosts = maze.ghosts();
        for (CommonMazeObject ghost : ghosts) {
            List<CommonField.Direction> ghostDirection = ((Ghost) ghost).getGhostDirections();
            //System.out.println(ghostDirection.get(i));
            if (ghostDirection.get(i) != null){
                ghost.move(ghostDirection.get(i));
            }
            System.out.println(ghost.getField());
        }
    }
}
