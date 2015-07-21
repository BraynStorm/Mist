package mist.client.engine.render.gui.shapes;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import mist.client.engine.Mist;
import mist.client.engine.render.Drawable;
import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.TransformGUI;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vertex;

import static org.lwjgl.opengl.GL15.*;

public class Rectangle extends Drawable{
	
	private float width;
	private float height;
	private Texture texture;
	private TransformGUI transform;
	private Vector3f color;
	
	public Rectangle(Shader shader, int width, int height, Texture texture) {
		super(shader, glGenBuffers(), glGenBuffers(), 4, new Material(texture.textureID));
		
		this.width = width;
		this.height = height;
		
		float w = (float)(width)  / Mist.getInstance().getWindow().getWidth();
		float h = (float)(height) / Mist.getInstance().getWindow().getHeight();
		float w2 = w/2;
		float h2 = h/2;
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		vertices.add(new Vertex(-w2, -h2, 1, 0, 0));
		vertices.add(new Vertex( w2, -h2, 1, 0, 0));
		vertices.add(new Vertex( w2,  h2, 1, 0, 0));
		vertices.add(new Vertex(-w2,  h2, 1, 0, 0));
		
		float[] ibob = new float[]{0,1,2,};
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Vertex.bufferfy(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, FloatBuffer.wrap(ibob), GL_STATIC_DRAW);
		
	}
	
	public float getWidth(){
		return transform.getScale().x * width;
	}
	
	public float getHeight(){
		return transform.getScale().y * height;
	}
	
	public boolean hasTexture(){
		return material.getTextureID() != 0;
	}
}
