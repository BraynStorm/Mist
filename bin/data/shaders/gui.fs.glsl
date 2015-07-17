#version 330

uniform sampler2D diffuse;
uniform vec4 model_color;
uniform int model_hasTexture;

in vec2 texCoord0;

void main(){
  vec4 texPixel = vec4(0,0,0,0);
  vec4 color = model_color;
  if(model_hasTexture == 1){
    texPixel = texture2D(diffuse, texCoord0);
  }
  
  if(texPixel.w == 0.0){
    color.w = 1;
    gl_FragColor = color;
    return;
  }else{
    gl_FragColor = vec4(
              clamp(0, 1, model_color.x + texPixel.x),
              clamp(0, 1, model_color.y + texPixel.y),
              clamp(0, 1, model_color.z + texPixel.z),
              clamp(0, 1, model_color.w + texPixel.w)
    );
    return;
  }
}
