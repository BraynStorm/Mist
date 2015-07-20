#version 330

uniform sampler2D diffuse;
uniform vec4 model_color;
uniform int model_hasTexture;
uniform int model_isFont;

in vec2 texCoord0;

void main(){
  vec4 texPixel = vec4(0,0,0,0);
  
  if(model_hasTexture == 1){
    texPixel = texture2D(diffuse, texCoord0);
  }
  
  
  if(model_isFont == 1){
      gl_FragColor=vec4(0,0,0,1);
      return;
      texPixel = vec4(model_color.xyz, texPixel.r);
      gl_FragColor = texPixel;
  }
  
  if(texPixel.w <= 0.3){
    gl_FragColor = vec4(model_color.xyz, 1);
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
