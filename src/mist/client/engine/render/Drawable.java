package mist.client.engine.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Matrix4f;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.core.Vertex;

public abstract class Drawable {
	
	protected Shader shader;
	protected Transform transform;
	protected int vbo;
	protected int ibo;
	protected int drawCount;
	protected Material material;
	
	protected Vector4f color;
	
	public Drawable(Shader shader, int vbo, int ibo, int drawCount, Material material) {
		this.shader = shader;
		this.transform = new Transform();
		this.vbo = vbo;
		this.ibo = ibo;
		this.drawCount = drawCount;
		this.material = material;
		color = new Vector4f(1, 0, 0, .5f);
	}
	
	public Drawable(Shader shader, Transform transform, int vbo, int ibo, int drawCount, Material material) {
		this.shader = shader;
		this.transform = transform;
		this.vbo = vbo;
		this.ibo = ibo;
		this.drawCount = drawCount;
		this.material = material;
	}
	
	public void setColor(float r, float g, float b, float a){
		color.x = r/255;
		color.y = g/255;
		color.z = b/255;
		color.w = a/255;
	}
	
	public void render(){
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		shader.setUniform("model_color", color);
		shader.setUniform("model_transform", transform.getTansformation());
		shader.setUniformi("model_hasTexture", 1 /*material.getTextureID() == 0 ? 0 : 1*/);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0); // positions
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12); // texCoords
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public void setScale(Vector3f scale){
		transform.setScale(scale);
	}
	
	public void setScale(float x, float y, float z){
		transform.setScale(x,y,z);
	}
	
	public void setTranslation(Vector3f translation){
		transform.setTranslation(translation);
	}

	public void setTranslation(float x, float y, float z){
		transform.setTranslation(x,y,z);
	}

	public void setRotation(float x, float y, float z){
		transform.setRotation(x,y,z);
	}
}
