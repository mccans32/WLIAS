#version 460 core

in vec2 position;
in vec3 colour;
in vec2 textureCoords;

out vec3 passColour;
out vec2 passTextureCoords;

void main() {
    gl_Position = vec4(position, 0.0, 1.0);
    passColour = colour;
    passTextureCoords = textureCoords;
}