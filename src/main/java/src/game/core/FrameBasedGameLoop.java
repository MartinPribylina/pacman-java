/*************************
 * Authors: Martin Pribylina
 *
 * Waits for Player input and calls game controller to update to new frame
 ************************/
package src.game.core;

/**
 * FrameBasedGameLoop is class which waits for Player input and calls game controller to update to new frame
 *
 * @author      Martin Pribylina
 */
public class FrameBasedGameLoop extends GameLoop {
    public FrameBasedGameLoop(GameController _controller) {
        super(_controller);
        _controller.setGameLoop(this);
    }

    @Override
    protected void processGameLoop() {
        while (isGameRunning()) {
            processInput();
            update();
        }
    }

    protected void update() {
        controller.Update();
    }


}
