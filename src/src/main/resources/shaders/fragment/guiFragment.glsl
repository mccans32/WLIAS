#version 330 core

in vec3 passColor;
in vec2 passTextureCoords;

out vec4 outColor;

uniform sampler2D tex;
uniform vec4 colourOffset;

void main() {
    outColor = texture(tex, passTextureCoords) * colourOffset;
}