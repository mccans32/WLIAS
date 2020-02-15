#version 460 core

in vec3 passColour;
in vec2 passTextureCoords;

out vec4 outColour;

uniform sampler2D tex;

void main() {
    outColour = texture(tex, passTextureCoords);
}