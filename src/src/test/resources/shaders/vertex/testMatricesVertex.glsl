#version 330 core

in vec2 position;
in vec3 color;
in vec2 textureCoords;

out vec3 passColor;
out vec2 passTextureCoords;

float z = 0.0;

uniform mat4 model;

void main() {
    gl_Position = vec4(position, z, 1.0) * model;
    passColor = color;
    passTextureCoords = textureCoords;
}