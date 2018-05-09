package com.cjglimsjo.opkoko.shaders.demo;

import com.cjglimsjo.opkoko.shaders.engine.GameLogic;
import com.cjglimsjo.opkoko.shaders.engine.Window;
import com.cjglimsjo.opkoko.shaders.engine.graphics.Model;
import com.cjglimsjo.opkoko.shaders.engine.graphics.ShaderProgram;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class Demo extends GameLogic {

    private ShaderProgram shaderProgram;
    private Model model;

    private Matrix4f projectionMatrix;

    @Override
    public void init(Window window) {
        shaderProgram = new ShaderProgram();
        shaderProgram.loadShader(GL_VERTEX_SHADER, "shaders/shader.vert");
        shaderProgram.loadShader(GL_FRAGMENT_SHADER, "shaders/shader.frag");
        shaderProgram.link();

        model = new Model(new float[]{
                -0.5f, -0.5f, -2.0f,
                0.5f, 0.5f, -2.0f,
                -0.5f, 0.5f, -2.0f,

                -0.5f, -0.5f, -1.0f,
                0.5f, -0.5f, -1.0f,
                0.5f, 0.5f, -1.0f
        }, new float[]{
                0.0f, 0.5f, 0.0f,
                1.0f, 0.5f, 1.0f,
                0.0f, 0.5f, 1.0f,

                0.0f, 0.5f, 0.0f,
                1.0f, 0.5f, 0.0f,
                1.0f, 0.5f, 1.0f
        });

        float fov = (float) Math.toRadians(60.0f);
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        float zNear = 0.01f;
        float zFar = 100.0f;
        projectionMatrix = new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
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
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shaderProgram.bind();
        shaderProgram.setUniformMatrix4f("projectionMatrix", projectionMatrix);
        model.render();
        shaderProgram.unbind();
    }

    @Override
    public void cleanup() {
        model.cleanup();
        shaderProgram.cleanup();
    }
}
