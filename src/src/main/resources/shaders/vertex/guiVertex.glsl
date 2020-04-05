#version 330 core

in vec2 position;
in vec3 color;
in vec2 textureCoords;

out vec3 passColour;
out vec2 passTextureCoords;

uniform mat4 model;
uniform mat4 projection;

void main() {
    gl_Position =  projection * model * vec4(position, 0, 1.0);
    passColour = color;
    passTextureCoords = textureCoords;
}