package mist.client.engine.render.core;

public class Transform {
	protected Vector3f translation;
	protected Vector3f rotation;
	protected Vector3f scale;

	public Transform() {
		translation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
		
	}
	
	public Matrix4f getTransformation(){
		Matrix4f translationMatrix = new Matrix4f().translation(translation);
		Matrix4f scaleMatrix = new Matrix4f().scale(scale);
		Matrix4f rotationMatrix = new Matrix4f().rotate(rotation);
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("Translation: ").append(translation.toString());
		sb.append("Rotation: ").append(rotation.toString());
		sb.append("Scale: ").append(scale.toString());
		
		return sb.toString();
	}
	
	public Matrix4f getTranslationMatrix(){
		return new Matrix4f().translation(translation);
	}
	public Matrix4f getRotationMatrix(){
		return new Matrix4f().rotate(translation);
	}
	public Matrix4f getScaleMatrix(){
		return new Matrix4f().scale(translation);
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
		this.scale.x = x;
		this.scale.y = y;
		this.scale.z = z;
	}
	
	public void moveBy(float x, float y, float z){
		translation.x += x;
		translation.y += y;
		translation.z += z;
	}

	public void rotateBy(float angle, Vector3f axis) {
		
	}
	
}
