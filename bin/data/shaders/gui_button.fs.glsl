#version 330

uniform sampler2D diffuse;

uniform vec2 innerAnimationTL;

in vec2 texCoord0;

void main(){
  vec4 color = texture2D(diffuse, texCoord0 + innerAnimationTL);
  gl_FragColor = color;
}
