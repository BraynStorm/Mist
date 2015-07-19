package mist.client.engine.render.core;

public class Transform {
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;

	public Transform() {
		translation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
		
	}
	
	public Matrix4f getTansformation(){
		Matrix4f translationMatrix = new Matrix4f().translation(translation);
		Matrix4f scaleMatrix = new Matrix4f().scale(scale);
		Matrix4f rotationMatrix = new Matrix4f().rotate(rotation);
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	public Matrix4f getTansformationBone(){
		Matrix4f translationMatrix = new Matrix4f().translation(translation);
		Matrix4f rotationMatrix = new Matrix4f().rotate(rotation);
		
		return rotationMatrix.mul(translationMatrix);
	}
	
	public static Matrix4f getProjection(String suffix) {
		return new Matrix4f().projection(suffix);
	}
	
	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}
	
	public void setTranslation(float x, float y, float z) {
		this.translation = new Vector3f(x, y, z);
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation = new Vector3f(x, y, z);
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z) {
		this.scale = new Vector3f(x, y, z);
	}
	
}
