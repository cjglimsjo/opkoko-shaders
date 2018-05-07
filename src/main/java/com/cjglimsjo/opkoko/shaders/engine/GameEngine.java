package com.cjglimsjo.opkoko.shaders.engine;

public class GameEngine implements Runnable {

    private final Window window;
    private final GameLogic game;

    public GameEngine(Window window, GameLogic game) {
        this.window = window;
        this.game = game;
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void init() {
        window.init();
        game.init(window);
    }

    private void gameLoop() {
        while (game.isRunning() && !window.shouldClose()) {
            game.handleInput(window);
            game.update();
            game.render();
            window.update();
        }
    }

    private void cleanup() {
        game.cleanup();
        window.cleanup();
    }
}
