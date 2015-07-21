package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

public class WindowEvent extends GLFWFramebufferSizeCallback {

	boolean registered = false;
	
	public WindowEvent(long windowID){
		register(windowID);
	}
	
	public void register(long windowID){
		registered = true;
		GLFW.glfwSetFramebufferSizeCallback(windowID, this);
	}
	
	@Override
	public void invoke(long window, int width, int height) {
		EventManager.framebufferResize(width, height);
	}

}
