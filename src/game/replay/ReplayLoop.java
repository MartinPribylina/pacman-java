package src.game.replay;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.Ghost;
import src.game.MazeObject;
import src.game.save.GameLogging;

import java.util.List;

import static src.common.CommonField.Direction.*;

public class ReplayLoop {
    private GameLogging gameLogging;
    public int step;
    public boolean back = false;
    public ReplayLoop(GameLogging gameLogging){
        this.gameLogging = gameLogging;
    }
    public void run() {
        Thread replayThread = new Thread(this::processReplayLoop);
        replayThread.start();
    }

    protected void processReplayLoop(){
        CommonMaze maze = gameLogging.getMaze();
        for (step = -1; step < gameLogging.getTime();) {
            synchronized (this){
                try{
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!back) step++;
            System.out.println(step);
            movePacman(maze, step);
            for (CommonMazeObject ghostCommon : maze.ghosts()) {
                moveGhost(ghostCommon, step);
            }
            if (back) step--;
        }
    }
    public void movePacman(CommonMaze maze, int i) {

        MazeObject pacman = (MazeObject) maze.pacman();
        List<CommonField.Direction> pacmanDirection = gameLogging.getPacmanPath();
        CommonField.Direction dir = null;
        if (pacmanDirection.get(i) != null && !back) {
            dir = pacmanDirection.get(i);
            pacman.move(dir);
        }else if (pacmanDirection.get(i) != null && back) {
            switch (pacmanDirection.get(i)){
                case UP -> dir = DOWN;
                case DOWN -> dir = UP;
                case LEFT -> dir = RIGHT;
                case RIGHT -> dir = LEFT;
            }
            CommonField nextField = pacman.field.nextField(dir);
            pacman.field.removeObserver(pacman);
            pacman.field.notifyObservers();
            nextField.addObserver(pacman);
            pacman.field = nextField;
            nextField.notifyObservers();
            pacman.setLastMove(dir);
        }
    }
    public void moveGhost(CommonMazeObject CMOghost, int i) {
            MazeObject ghost = (MazeObject) CMOghost;
            CommonField.Direction dir = null;
            List<CommonField.Direction> ghostDirection = ((Ghost) ghost).getGhostDirections();
            System.out.println(ghostDirection);
            if (ghostDirection.get(i) != null && !back){
                dir = ghostDirection.get(i);
                ghost.move(dir);
            }else if (ghostDirection.get(i) != null && back){
                switch (ghostDirection.get(i)){
                    case UP -> dir = DOWN;
                    case DOWN -> dir = UP;
                    case LEFT -> dir = RIGHT;
                    case RIGHT -> dir = LEFT;
                }
                //ghost.move(dir);
                CommonField nextField = ghost.field.nextField(dir);
                ghost.field.removeObserver(ghost);
                ghost.field.notifyObservers();
                nextField.addObserver(ghost);
                ghost.field = nextField;
                nextField.notifyObservers();
                ghost.setLastMove(dir);
            }
            System.out.println(dir);
    }
}
