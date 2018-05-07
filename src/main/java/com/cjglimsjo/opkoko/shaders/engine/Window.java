package com.cjglimsjo.opkoko.shaders.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Callback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.opengl.GLUtil.setupDebugMessageCallback;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final int width;
    private final int height;
    private final String title;

    private long window;

    private Callback debugCallback;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        boolean glfwInitialized = glfwInit();
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (!glfwInitialized) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        //</editor-fold>

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        //<editor-fold defaultstate="collapsed" desc="Specify the required client API version and disable deprecated functionality">
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 4);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        //</editor-fold>

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }
        //</editor-fold>

        // Center window
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (videoMode != null) {
            int x = (videoMode.width() - width) / 2;
            int y = (videoMode.height() - height) / 2;
            glfwSetWindowPos(window, x, y);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        // Enable debug message callbacks
        glEnable(GL_DEBUG_OUTPUT);
        debugCallback = setupDebugMessageCallback();
        // Enable depth test
        glEnable(GL_DEPTH_TEST);
        // Enable alpha blending/transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void cleanup() {
        debugCallback.free();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public boolean isKeyPressed(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }

    public boolean isKeyReleased(int key) {
        return glfwGetKey(window, key) == GLFW_RELEASE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
}
