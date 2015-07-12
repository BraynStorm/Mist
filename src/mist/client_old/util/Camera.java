package mist.client_old.util;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera {
	protected float x,y,z;
	protected float rotX, rotY, rotZ;
	
	protected float movementSpeed = 0.4f;
	protected float rotationSpeed = 0.1f;
	//Make the player rotate! .. and pan around!
	//pff.. why do I always get interupted? ...
	
	public double getGetRotationSpeed(){
		return rotationSpeed;
	}
	
	public void changeRotationSpeed(float change){
		if(rotationSpeed + change > 0)
			rotationSpeed += change;
	}
	
	public void moveCamera(float x, float y , float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * RELATIVE!
	 */
	public void moveCameraR(float x, float y , float z){
		this.x += (movementSpeed * x);
		this.y += (movementSpeed * y);
		this.z += (movementSpeed * z * 2);
	}
	
	public void rotateCamera(float x, float y, float z){
		rotX += (rotationSpeed * x);
		rotY += (rotationSpeed * y);
		rotZ += (rotationSpeed * z);
	}
	
	/*
	 * RELATIVE!
	 */
	public void rotateCameraR(float rotX, float rotY, float rotZ){
		this.rotX += rotX;
		this.rotY += rotY;
		this.rotZ += rotZ;
	}
	
	public void applyPossitionToRenderer(){
		
		glTranslatef(x, y, z);
		
		glRotatef(rotX, 1, 0, 0);
		glRotatef(rotY, 0, 1, 0);
		glRotatef(rotZ, 0, 0, 1);
		
		
	}
	
}
