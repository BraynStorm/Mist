#version 330

struct Bone{ // TODO: do this thing in JAVA on the CPU not here.. just read the article, it's awesome 
// https://www.opengl.org/wiki/Skeletal_Animation
	vec3 head;
	vec3 tail;
	mat4 translation_matrix;
	mat4 rotation_matrix;
	
	float roll;
	float envelope;
	float radius;
	
	boolean hasParent;
	int parentID;
	Bone parent;
	
	void calculate(){
		
		if(!hasParent)
			return;
		
		parent = bones[parentID];
		parent.calculate();
		
		head = parent.tail;
		
		// Time spent 1h 30hm
		tail -= parent.head;
		tail *= parent.rotation_matrix;
		tail += parent.head;
		tail += 
		
		
		roll += parent.roll;
	}
};

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

uniform mat4 projection_transform;
uniform mat4 model_transform;
uniform mat4 camera_transform;

uniform Bone bones[5];

out vec2 texCoord0;

float getDistance(vec3 a, vec3 b){
	return sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2) + pow(a.z - b.z, 2));
}

float getWeight(Bone bone){
	return getDistance(bone.)
}

void main(){
  texCoord0 = texCoord;
  bones[4].calculate(); // Cascade calculate FTW.
  
  gl_Position = projection_transform * camera_transform * model_transform * vec4(position, 1.0);
}