package mist.client.engine.render.core;

public class Camera {
	
	public static final Vector3f Y_AXIS = new Vector3f(0,1,0);
	
	private boolean lockRotX;
	private boolean lockRotY;
	private boolean lockPos;
	
	private Vector3f pos;
	private Vector3f up;
	private Vector3f forward;
	
	private float xRot;
	private float zoom = 0;
	
	
	public Camera(){
		pos = new Vector3f(0,0,0);
		zoom = 0;
		up = Y_AXIS;
		forward = new Vector3f(0,0,1);
		xRot = 0;
	}
	
	public void setLock(boolean v){
		lockRotX = v;
		lockRotX = v;
		lockPos = v;
	}
	
	public boolean isRotXLocked() {
		return lockRotX;
	}

	public void setRotXLock(boolean lockRotX) {
		this.lockRotX = lockRotX;
	}

	public boolean isRotYLocked() {
		return lockRotY;
	}

	public void setRotYLock(boolean lockRotY) {
		this.lockRotY = lockRotY;
	}

	public boolean isPosLocked() {
		return lockPos;
	}

	public void setPosLock(boolean lockPos) {
		this.lockPos = lockPos;
	}

	public void move(Vector3f dir, float amount){
		if(lockPos) return;
		pos = pos.add(dir.mul(amount));
	}
	
	public void moveForward(float amount){
		if(lockPos) return;
		pos = pos.add(forward.mul(amount));
	}
	
	public void moveBackward(float amount){
		if(lockPos) return;
		pos = pos.add(forward.getInverted().mul(amount));
	}
	
	public void moveLeft(float amount){
		if(lockPos) return;
		pos = pos.add(getRight().mul(amount));
	}
	
	public void moveRight(float amount){
		if(lockPos) return;
		pos = pos.add(getLeft().mul(amount));
	}
	
	public Vector3f getLeft(){
		return up.cross(forward).normalize(); // deabtable
	}
	
	public Vector3f getRight(){
		return getLeft().invert();
	}
	
	public Vector3f getForward(){
		return forward;
	}
	
	public Vector3f getBackward(){
		return forward.getInverted();
	}
	
	public void rotateX(float angle, boolean respectLimits){
		if(respectLimits){
			if(lockRotX) return;
			if(xRot ==  90 && angle > 0) return;
			if(xRot == -90 && angle < 0) return;
			
			xRot += angle;
			if(respectLimits){
				xRot = xRot >  90 ?  90 : xRot;
				xRot = xRot < -90 ? -90 : xRot;
			}
		}
		
		Vector3f horizontalAxis = Y_AXIS.cross(forward).normalize();
		
		forward.rotate(angle, horizontalAxis);
		forward.normalize();
		
		up = forward.cross(horizontalAxis);
		up.normalize();
		
	}
	
	public void rotateY(float angle, boolean respectLimits){
		if(respectLimits){
			if(lockRotY) return;
		}
		
		Vector3f horizontalAxis = Y_AXIS.cross(forward).normalize();
		
		forward.rotate(angle, Y_AXIS);
		forward.normalize();
		
		up = forward.cross(horizontalAxis);
		up.normalize();
		
	}
	@Deprecated
	public Matrix4f getTransform() {
		Matrix4f rotation = new Matrix4f().camera(forward, up);
		Matrix4f position = new Matrix4f().translation(pos.getInverted());
		return position.mul(rotation);
	}
	
	public Matrix4f getTranslation(){
		return new Matrix4f().translation(pos.getInverted());
	}
	
	public Matrix4f getRotation(){
		return new Matrix4f().camera(forward, up);
	}
}
