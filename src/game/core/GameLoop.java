package src.game.core;

public abstract class GameLoop{

    protected volatile GameStatus status;

    protected GameController controller;

    private final PlayerActions playerActions;

    public GameLoop(GameController controller) {
        this.controller = controller;
        status = GameStatus.STOPPED;
        playerActions = new PlayerActions(controller);
    }

    public void run() {
        status = GameStatus.RUNNING;
        Thread gameThread = new Thread(this::processGameLoop);
        gameThread.start();
    }

    protected abstract void processGameLoop();

    public void stop() {
        status = GameStatus.STOPPED;
        controller.saveIntoFile();
    }

    public boolean isGameRunning() {
        return status == GameStatus.RUNNING;
    }

    protected void processInput() {
        try {
            var lag = 200;
            Thread.sleep(lag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PlayerActions getPlayerActions() {
        return playerActions;
    }
}

