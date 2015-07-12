package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyEventManager extends GLFWKeyCallback {

	boolean registered = false;
	
	public KeyEventManager(long windowID){
		register(windowID);
	}
	
	public void register(long windowID){
		registered = true;
		GLFW.glfwSetKeyCallback(windowID, this);
	}
	
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		// TODO Auto-generated method stub
		
	}

}
