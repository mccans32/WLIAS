#version 330 core

in vec2 outTexCoord;

out vec4 fragColor;

uniform vec3 colourOffset;
uniform sampler2D tex;

void main() {

    fragColor = vec4(colourOffset, 1) * texture(tex, outTexCoord);

}
