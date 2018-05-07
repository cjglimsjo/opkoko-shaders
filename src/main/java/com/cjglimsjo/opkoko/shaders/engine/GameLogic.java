package com.cjglimsjo.opkoko.shaders.engine;

public abstract class GameLogic {

    private boolean running = true;

    public boolean isRunning() {
        return running;
    }

    public void stopRunning() {
        running = false;
    }

    public abstract void init(Window window);

    public abstract void handleInput(Window window);

    public abstract void update();

    public abstract void render();

    public abstract void cleanup();
}
