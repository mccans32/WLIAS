#version 330 core

in vec2 position;
in vec3 color;
in vec2 textureCoords;

out vec3 passColor;
out vec2 passTextureCoords;

float z = 0.0;

uniform float scale = 1;

void main() {
    gl_Position = vec4(position, 0.0, 1.0) * vec4(scale, scale, scale, 1);
    passColor = color;
    passTextureCoords = textureCoords;
}