#version 330

uniform sampler2D diffuse;

in vec2 texCoord0;

void main(){
  vec4 texPixel = vec4(0,1,0,1);
  
  gl_FragColor = texture2D(diffuse, texCoord0);
}
