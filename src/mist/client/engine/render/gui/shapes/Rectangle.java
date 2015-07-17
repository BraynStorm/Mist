package mist.client.engine.render.gui.shapes;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import mist.client.engine.Mist;
import mist.client.engine.Window;
import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vertex;
import mist.client.engine.render.gui.GuiBaseElement;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;

public class Rectangle extends GuiBaseElement{
	
	private float width;
	private float height;
	
	public Rectangle(Shader shader, int width, int height, int textureID) {
		super(shader, glGenBuffers(), glGenBuffers(), 4, new Material(textureID));
		
		this.width = width;
		this.height = height;
		
		float w = width / Mist.getInstance().getWindow().getWidth();
		float h = height/ Mist.getInstance().getWindow().getHeight();
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
	
	@Override
	public void renderForeground() {}

	@Override
	public void renderBackground() {
		
		
	}

}
