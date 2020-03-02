#version 330 core

in vec2 position;
in vec3 color;
in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 model;
uniform mat4 projection;

void main() {
    gl_Position = model * projection * vec4(position, 0, 1.0);
    outTexCoord = texCoord;
}
