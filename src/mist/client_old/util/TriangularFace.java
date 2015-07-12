package mist.client_old.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

@SuppressWarnings("unused")
public class TriangularFace extends Renderable{
	
	private FloatBuffer vertices;
	private FloatBuffer colors;
	private FloatBuffer normals;
	private FloatBuffer texCoords;
	
	private String materialName;
	
	private int vertexVBO;
	private int colorsVBO;
	private int texCoordsVBO;
	private int normalsVBO;
	
	public TriangularFace(){
		vertices = BufferUtils.createFloatBuffer(9);
		colors = BufferUtils.createFloatBuffer(12);
		texCoords = BufferUtils.createFloatBuffer(9);
		normals = BufferUtils.createFloatBuffer(9);
		
		vertexVBO = glGenBuffers();
		colorsVBO = glGenBuffers();
		texCoordsVBO = glGenBuffers();
		normalsVBO = glGenBuffers();
		
	}
	
	public void setVertex(int index, float x, float y, float z){
		vertices.put(index * 3    , x);
		vertices.put(index * 3 + 1, y);
		vertices.put(index * 3 + 2, z);
		
		GLUtils.vertexBufferData(vertexVBO, vertices);
	}
	
	public void setNormal(int index, float x, float y, float z){
		normals.put(index * 3    , x);
		normals.put(index * 3 + 1, y);
		normals.put(index * 3 + 2, z);
		
		GLUtils.vertexBufferData(normalsVBO, normals);
	}
	
	public void setColors(int index, float r, float g, float b, float a){
		colors.put(index * 4    , r);
		colors.put(index * 4 + 1, g);
		colors.put(index * 4 + 2, b);
		colors.put(index * 4 + 3, a);
		
		GLUtils.vertexBufferData(colorsVBO, colors);
	}
	
	public void setColor(float r, float g, float b, float a){
		for(int i = 0; i < 3; i++){
			colors.put(i * 4, r);
			colors.put(i * 4 + 1, g);
			colors.put(i * 4 + 2, b);
			colors.put(i * 4 + 3, a);
			
		}
		
		GLUtils.vertexBufferData(colorsVBO, colors);
	}
	
	public void setTexCoord(int index, float x, float y, float z) {
		texCoords.put(index * 3    , x);
		texCoords.put(index * 3 + 1, y);
		texCoords.put(index * 3 + 2, z);
		
		GLUtils.vertexBufferData(texCoordsVBO, texCoords);
	}
	
	public void setMaterialName(String name){
		materialName = name;
	}
	
	public void render(){
		glPushMatrix();
		
		glScalef(scaleX, scaleY, scaleZ);
		
		glRotatef(rotX, 1,0,0);
		glRotatef(rotY, 0,1,0);
		glRotatef(rotZ, 0,0,1);
		
		glTranslatef(x, y, z);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vertexVBO);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		
		glEnableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, colorsVBO);
		glColorPointer(4, GL_FLOAT, 0, 0);
		
		if(useNormal){
			glEnableClientState(GL_NORMAL_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, normalsVBO);
			glNormalPointer(GL_FLOAT, 0, 0);
		}
		
		if(useTexture && MaterialManager.list.containsKey(materialName) && MaterialManager.list.get(materialName).hasTextrue()){
			
			MaterialManager.list.get(materialName).getTexture().bind();
			
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, texCoordsVBO);
			glTexCoordPointer(3, GL_FLOAT, 0, 0);
		}
		
		
		glDrawArrays(GL_TRIANGLES, 0, 3);
		
		glPopMatrix();
	}

	
	
}
