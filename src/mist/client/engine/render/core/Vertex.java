package mist.client.engine.render.core;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class Vertex {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((texCoord == null) ? 0 : texCoord.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (texCoord == null) {
			if (other.texCoord != null)
				return false;
		} else if (!texCoord.equals(other.texCoord))
			return false;
		return true;
	}

	public static final int SIZE = 3 + 2;
	
	private Vector3f position;
	private Vector2f texCoord;
	//private Vector3f normal;
	
	public Vertex(Vector3f position, Vector2f texCoord /*,Vector3f normal,*/) {
		super();
		this.position = position;
		this.texCoord = texCoord;
		//this.normal = normal;
	}
	
	public Vertex(float x, float y, float z, float s, float t){
		this(new Vector3f(x, y, z), new Vector2f(s, t));
	}
	
	public static FloatBuffer bufferfy(ArrayList<Vertex> vertices){
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * SIZE);
		
		for(Vertex v : vertices){
			buffer.put(v.position.getData());
			buffer.put(v.texCoord.getData());
		}
		
		buffer.flip();
		
		return buffer;
	}

}
