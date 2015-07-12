package mist.client_old.util;

public class Vertex {
	public float x,y,z,w;
	
	public Vertex(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vertex(float x, float y, float z){
		this(x, y, z, 0);
	}
}
