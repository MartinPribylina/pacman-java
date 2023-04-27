/*************************
 * Authors: Samuel Gall
 *
 * Manages replay progress and it's GUI
 ************************/
package src.game.replay;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.objects.Ghost;
import src.game.objects.MazeObject;

import javax.swing.*;
import java.util.List;

import static src.common.CommonField.Direction.*;

public class ReplayLoop {
    private JLabel steps, currentStep;
    private JButton stepForward, stepBackwards, playForward, playBackwards, playPause;
    private Thread replayThread;
    private ReplayDetails replayDetails;
    public int step;
    public boolean back = false;
    public boolean forward = false, backwards = false;
    public ReplayLoop(ReplayDetails replayDetails){
        this.replayDetails = replayDetails;
    }
    public void run() {
        replayThread = new Thread(this::processReplayLoop);
        replayThread.start();
    }
    public void jumpToEnd() {
        replayThread.stop();
        CommonMaze maze = replayDetails.getMaze();
        if(step == -1) step++;
        if (!back){
            for (;step < replayDetails.getTime(); step++){
                movePacman(maze, step);
                for (CommonMazeObject ghostCommon : maze.ghosts()) {
                    moveGhost(ghostCommon, step);
                }
                System.out.println(step);
            }
        }else {
            for (;step > -1; step--){
                movePacman(maze, step);
                for (CommonMazeObject ghostCommon : maze.ghosts()) {
                    moveGhost(ghostCommon, step);
                }
                System.out.println(step);
            }
        }

        if(step > replayDetails.getTime()-1) step--;
        replayThread = new Thread(this::processReplayLoop);
        replayThread.start();
    }

    protected void processReplayLoop(){
        CommonMaze maze = replayDetails.getMaze();
        while (true){
            synchronized (this){
                try{
                    if (forward || backwards) {
                        playPause.setEnabled(true);
                        this.wait(200);
                    }else {
                        playPause.setEnabled(false);
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!back){
                step++;
            }

            if (!back && step > replayDetails.getTime() - 1){
                step--;
                forward = false;
                stepBackwards.setEnabled(true);
                stepForward.setEnabled(false);
                playBackwards.setEnabled(true);
                playForward.setEnabled(false);
                playPause.setEnabled(false);
                continue;
            } else if (forward || backwards){

            }else {
                stepForward.setEnabled(true);
                playForward.setEnabled(true);
            }

            if(step < 0){
                backwards = false;
                stepBackwards.setEnabled(false);
                stepForward.setEnabled(true);
                playBackwards.setEnabled(false);
                playForward.setEnabled(true);
                playPause.setEnabled(false);
                continue;
            } else if (forward || backwards){

            } else {
                stepBackwards.setEnabled(true);
                playBackwards.setEnabled(true);
            }


            movePacman(maze, step);
            for (CommonMazeObject ghostCommon : maze.ghosts()) {
                moveGhost(ghostCommon, step);
            }
            if (back) step--;
            UpdateGameStats();
            System.out.println(step);
        }
    }
    public void movePacman(CommonMaze maze, int i) {

        MazeObject pacman = (MazeObject) maze.pacman();
        List<CommonField.Direction> pacmanPath = replayDetails.getPacmanPath();
        CommonField.Direction dir = null;
        if (pacmanPath.get(i) != null && !back) {
            dir = pacmanPath.get(i);
            pacman.move(dir);
        }else if (pacmanPath.get(i) != null && back) {
            switch (pacmanPath.get(i)){
                case UP -> dir = DOWN;
                case DOWN -> dir = UP;
                case LEFT -> dir = RIGHT;
                case RIGHT -> dir = LEFT;
            }
            pacman.move(dir);
            pacman.setLastMove(pacmanPath.get(i));
        }
    }
    public void moveGhost(CommonMazeObject CMOghost, int i) {
            MazeObject ghost = (MazeObject) CMOghost;
            CommonField.Direction dir = null;
            List<CommonField.Direction> ghostPath = ((Ghost) ghost).getGhostPath();
            if (ghostPath.get(i) != null && !back){
                dir = ghostPath.get(i);
                ghost.move(dir);
            }else if (ghostPath.get(i) != null && back){
                switch (ghostPath.get(i)){
                    case UP -> dir = DOWN;
                    case DOWN -> dir = UP;
                    case LEFT -> dir = RIGHT;
                    case RIGHT -> dir = LEFT;
                }
                ghost.move(dir);
                ghost.setLastMove(ghostPath.get(i));
            }
    }

    private void UpdateGameStats() {
        currentStep.setText("Current step: " + Integer.valueOf(step+1));
    }

    public void setStep(int step) {
        this.step = step;
    }
    public void setCurrentStep(JLabel currentStep) {
        this.currentStep = currentStep;
    }

    public void setForward(JButton stepForward) {
        this.stepForward = stepForward;
    }

    public void setBackwards(JButton stepBackwards) {
        this.stepBackwards = stepBackwards;
    }

    public void setPlayForward(JButton playForward) {
        this.playForward = playForward;
    }

    public void setPlayBackwards(JButton playBackwards) {
        this.playBackwards = playBackwards;
    }

    public void setPlayPause(JButton playPause) { this.playPause = playPause; }
}
