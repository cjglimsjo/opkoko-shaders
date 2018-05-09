package com.cjglimsjo.opkoko.shaders.demo;

import com.cjglimsjo.opkoko.shaders.engine.GameLogic;
import com.cjglimsjo.opkoko.shaders.engine.Window;
import com.cjglimsjo.opkoko.shaders.engine.graphics.Model;
import com.cjglimsjo.opkoko.shaders.engine.graphics.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class Demo extends GameLogic {

    private ShaderProgram shaderProgram;
    private Model model;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix = new Matrix4f();
    private Vector3f cameraPos = new Vector3f(0.0f, 0.0f, 0.0f);
    private Vector3f cameraRot = new Vector3f(0.0f, 0.0f, 0.0f);

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

        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            cameraPos.x -= 0.02f;
            cameraRot.y += 0.4f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            cameraPos.x += 0.02f;
            cameraRot.y -= 0.4f;
        }
    }

    @Override
    public void update() {
        viewMatrix.identity()
                .rotate((float) Math.toRadians(cameraRot.x), 1.0f, 0.0f, 0.0f)
                .rotate((float) Math.toRadians(cameraRot.y), 0.0f, 1.0f, 0.0f)
                .translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
    }

    @Override
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shaderProgram.bind();
        shaderProgram.setUniformMatrix4f("projectionMatrix", projectionMatrix);
        shaderProgram.setUniformMatrix4f("viewMatrix", viewMatrix);
        model.render();
        shaderProgram.unbind();
    }

    @Override
    public void cleanup() {
        model.cleanup();
        shaderProgram.cleanup();
    }
}
