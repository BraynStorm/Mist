package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPositionManager extends GLFWCursorPosCallback {
	
	boolean registered = false;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	public CursorPositionManager(long windowID){
		register(windowID);
	}
	
	public void register(long windowID){
		registered = true;
		GLFW.glfwSetCursorPosCallback(windowID, this);
	}
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		mouseX = (int) xpos;
		mouseY = (int) ypos;
	}

	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
}

