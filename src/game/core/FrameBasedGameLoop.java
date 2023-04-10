package src.game.core;

public class FrameBasedGameLoop extends GameLoop{
    public FrameBasedGameLoop(GameController _controller) {
        super(_controller);
    }

    @Override
    protected void processGameLoop() {
        while (isGameRunning()) {
            processInput();
            update();
        }
        stop();
    }

    protected void update() {
        controller.Update();
    }


}
