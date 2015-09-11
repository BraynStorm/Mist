package mist.client.engine.render.gui.shapes;

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

import mist.client.engine.render.Common;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.TransformGUI;
import mist.client.engine.render.core.Vector3f;

public class Rectangle{
	
	private int vbo;
	private Shader shader;
	private Texture texture;
	private TransformGUI transform;
	
	private int pxWidth = 1;
	private int pxHeight = 1;
	
	private Vector3f scale = new Vector3f(1,1,1);
	
	public Rectangle(Shader shader, int width, int height, Texture texture) {
		this(shader, width, height, new TransformGUI(), texture);
	}

	public Rectangle(Shader shader, int width, int height, TransformGUI transform, Texture texture) {
		this.shader = shader;
		this.vbo = Common.createRectangleVBO(1, 1, 0);
		this.texture = texture;
		this.transform = transform;
		init(width, height);
	}
	
	private void init(int width, int height){
		setWidth(width);
		setHeight(height);
	}
	
	public void render(){
		
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		texture.bind();
		
		shader.setUniform("model_transform", transform.getTransformation());
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0); // positions
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 12); // texCoords
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, Common.RECTANGLE_IBO);
		glDrawElements(GL_TRIANGLES, Common.RECTANGLE_DRAWCOUNT, GL_UNSIGNED_INT, 0);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public void setScale(float x, float y, float z) {
		scale.x = x;
		scale.y = y;
		scale.z = z;
	}
	
	public void setWidth(int pixels){
		pxWidth = pixels;
		transform.setScale(pxWidth * scale.x, pxHeight * scale.y, 1);
	}
	
	public void setHeight(int pixels){
		pxHeight = pixels;
		transform.setScale(pxWidth * scale.x, pxHeight * scale.y, 1);
	}
	
	public void setTransform(TransformGUI t){
		transform = t;
	}
}
