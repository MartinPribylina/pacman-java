package src.main.java.src.game.core;

import java.io.Serializable;

public class FrameBasedGameLoop extends GameLoop implements Serializable {
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
