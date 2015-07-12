package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollEventManager extends GLFWScrollCallback {

	boolean registered = false;
	
	public ScrollEventManager(long windowID){
		register(windowID);
	}
	
	public void register(long windowID){
		registered = true;
		GLFW.glfwSetScrollCallback(windowID, this);
	}
	
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		// TODO Auto-generated method stub
		System.out.println("SEM: scrolling..");
	}

}
