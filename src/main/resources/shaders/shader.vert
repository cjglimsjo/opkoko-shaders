#version 440 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;

uniform mat4 projectionMatrix;

out vec3 vColor;

void main() {
    gl_Position = projectionMatrix * vec4(position, 1.0);
    vColor = color;
}
