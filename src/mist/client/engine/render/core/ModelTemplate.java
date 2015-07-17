package mist.client.engine.render.core;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import mist.client.engine.render.Drawable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


public class ModelTemplate implements Cloneable{
	
	Material material;
	private int vbo;
	private int ibo;
	private int drawCount;
	
	public ModelTemplate(ArrayList<Vertex> vertices, ArrayList<Face> faces){
		super();
		
		FloatBuffer vertexData = Vertex.bufferfy(vertices);
		IntBuffer indexData = Face.meshify(faces);
		
		drawCount = indexData.capacity();
		System.out.println(vertexData.capacity() / Vertex.SIZE + " " + drawCount);
		
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertexData /**/, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData /**/, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public ModelTemplate(int vbo, int ibo, int drawCount, Material material) {
		super();
		this.material = material;
		this.vbo = vbo;
		this.ibo = ibo;
		this.drawCount = drawCount;
	}


	public void setColor(int r, int g, int b, int a){
		
	}
	
	public Model newModel(Shader shader){
		return new Model(shader, vbo, ibo, drawCount, material);
	}
	
	public void destroy(){
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}
	
	
	

	public void setMaterial(Material material) {
		this.material = material;
	}
}