package mist.client.engine.render.gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.loaders.TextureLoader;

import org.lwjgl.BufferUtils;

public class Font {
	
	private int vbo;
	private static int ibo = 0;
	private String texture;
	private Shader shader;
	
	private int[] charVBOs = new int[30];
	
	public Font(Shader shader, String texture){
		vbo = glGenBuffers();
		this.texture = texture;
		this.shader = shader;
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 5);
		buffer.put(new float[]{ // 0.44356435f  0.33673267f
				-0.5f,  0.3f, 1, 0.33673267f, 0.33673267f, //0, 1,
				 0.5f,  0.5f, 1, 0.44356435f, 0.33673267f,//1, 1,
				 0.5f, -0.5f, 1, 0.44356435f, 0.44356435f,//1, 0,
				-0.5f, -0.5f, 1, 0.33673267f, 0.44356435f //0, 0 
		});
		buffer.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		if(ibo == 0){
			ibo = glGenBuffers();
			IntBuffer ibob = BufferUtils.createIntBuffer(6);
			
			ibob.put(new int[]{ // 0.44356435f  0.33673267f
					0, 1, 2,
					2, 3, 0
			});
			ibob.flip();
			
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibob, GL_STATIC_DRAW);
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void renderChar(int charIndex, Transform transform){
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		TextureLoader.bindTexture(texture);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo/*charVBOs[charIndex]*/);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);
		
		shader.setUniform("model_color", new Vector4f(0,1,0,0));
		shader.setUniform("model_transform", transform.getTansformation());
		shader.setUniformi("model_hasTexture", 1 /*material.getTextureID() == 0 ? 0 : 1*/);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
	}
}
