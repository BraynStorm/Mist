package mist.client_old.util;

public abstract class Renderable {
	
	protected float x = 0;
	protected float y = 0;
	protected float z = 0;
	
	protected float scaleX = 1;
	protected float scaleY = 1;
	protected float scaleZ = 1;
	
	protected float rotX = 180;
	protected float rotY = 0;
	protected float rotZ = 0;
	
	protected boolean useTexture;
	protected boolean useNormal;
	
	
	public void translate(float x, float y, float z){
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void rotate(float x, float y, float z){
		this.rotX += x;
		this.rotY += y;
		this.rotZ += z;
	}
	
	/*
	 * Addative! (1,0,0) will give double x scale and will not the change others.
	 */
	public void scale(float x, float y, float z){
		this.scaleX = x;
		this.scaleY = y;
		this.scaleZ = z;
	}
	
	public void setUseTexture(boolean value){
		useTexture = value;
	}
	
	public void setUseNormal(boolean value){
		useNormal = value;
	}
	
	public abstract void render();
}
