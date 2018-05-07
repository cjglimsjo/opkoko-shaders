package com.cjglimsjo.opkoko.shaders;

import com.cjglimsjo.opkoko.shaders.demo.Demo;
import com.cjglimsjo.opkoko.shaders.engine.GameEngine;
import com.cjglimsjo.opkoko.shaders.engine.Window;

public class Main {

    public static void main(String[] args) {
        Window window = new Window(720, 720, "Fifty Shaders of Grey");
        Demo demo = new Demo();
        GameEngine gameEngine = new GameEngine(window, demo);

        new Thread(gameEngine).start();
    }
}
