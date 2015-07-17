package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonEventManager extends GLFWMouseButtonCallback{
	
	boolean registered = false;
	
	public MouseButtonEventManager(long windowID){
		register(windowID);
	}
	
	public void register(long windowID){
		registered = true;
		GLFW.glfwSetMouseButtonCallback(windowID, this);
	}
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		System.out.println("MBEV: click");
		EventManager.mouseClick(button, action, mods);
		
	}

}
