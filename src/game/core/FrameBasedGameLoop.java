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
        // Toto by tu nemalo byť, teraz keď dám pause tak to zakaždým uloží, ukladanie treba bindnuť na koniec hry a Back to menu button
        // A stop nech len zastuvuje hru
        stop();
    }

    protected void update() {
        controller.Update();
    }


}
