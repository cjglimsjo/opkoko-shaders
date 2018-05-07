package com.cjglimsjo.opkoko.shaders.engine.graphics;

import com.cjglimsjo.opkoko.shaders.engine.util.ResourceUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int program;

    public ShaderProgram() {
        program = glCreateProgram();
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (program == 0) {
            throw new IllegalStateException("Failed to create shader program");
        }
        //</editor-fold>
    }

    public void loadShader(int type, String name) {
        String source = ResourceUtil.loadAsString(name);

        int shader = glCreateShader(type);
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (shader == 0) {
            throw new IllegalStateException("Failed to create shader");
        }
        //</editor-fold>

        glShaderSource(shader, source);
        glCompileShader(shader);
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new IllegalStateException(String.format("Failed to compile shader\n%s",
                    glGetShaderInfoLog(shader, 1024)));
        }
        //</editor-folding>

        glAttachShader(program, shader);
        glDeleteShader(shader);
    }

    public void link() {
        glLinkProgram(program);
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new IllegalStateException(String.format("Failed to link shader program\n%s",
                    glGetProgramInfoLog(program, 1024)));
        }
        //</editor-fold>

        glValidateProgram(program);
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println(String.format("Warning validating shader program\n%s",
                    glGetProgramInfoLog(program, 1024)));
        }
        //</editor-fold>
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        glDeleteProgram(program);
    }

    public void setUniform3f(String name, float x, float y, float z) {
        glUniform3f(getUniformLocation(name), x, y, z);
    }

    public void setUniformVector3f(String name, Vector3f vector) {
        glUniform3f(getUniformLocation(name), vector.x, vector.y, vector.z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            matrix.get(buffer);
            glUniformMatrix4fv(getUniformLocation(name), false, buffer);
        }
    }

    private int getUniformLocation(String name) {
        int location = glGetUniformLocation(program, name);
        //<editor-fold defaultstate="collapsed" desc="Error handling">
        if (location == -1) {
            System.err.println(String.format("Could not find location of uniform variable %s", name));
        }
        //</editor-fold>

        return location;
    }
}
