#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

uniform mat4 projection_transform;
uniform mat4 model_transform;
uniform mat4 camera_transform;

out vec2 texCoord0;

void main(){
  texCoord0 = texCoord;
	gl_Position = projection_transform * camera_transform * model_transform * vec4(position, 1.0);
}