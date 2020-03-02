#version 330

in vec2 outTexCoord;
in vec3 mvPos;
out vec4 fragColor;

uniform smapler2D texture_smapler;
uniform vec4 color;

void main() {

    fragColor = color * texture(texture_smapler, outTexCoord);

}
