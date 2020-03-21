#version 330 core

in vec2 outTexCoord;

out vec4 fragColor;

uniform vec3 colourOffset;
uniform sampler2D tex;

void main() {

    fragColor = texture(tex, outTexCoord) * vec4(colourOffset, 1) ;

}
