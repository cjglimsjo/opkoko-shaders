package com.cjglimsjo.opkoko.shaders.demo;

import com.cjglimsjo.opkoko.shaders.engine.GameLogic;
import com.cjglimsjo.opkoko.shaders.engine.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Demo extends GameLogic {

    @Override
    public void init(Window window) {

    }

    @Override
    public void handleInput(Window window) {
        if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            stopRunning();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }
}
