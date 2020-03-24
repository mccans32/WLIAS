#version 330 core

in vec3 position;
in vec3 color;
in vec2 textureCoords;

out vec3 passColour;
out vec2 passTextureCoords;
out vec4 passColourOffset;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 colourOffset;

float offsetAlpha = 0;

void main() {
    gl_Position = projection * view * model * vec4(position, 1.0);
    passColour = color;
    passColourOffset = vec4(colourOffset, offsetAlpha);
    passTextureCoords = textureCoords;
}