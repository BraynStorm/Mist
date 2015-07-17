package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;

import mist.client.engine.Mist;
import mist.client.engine.render.core.Camera;

public class EventManager {
	
	private static final int MAX_KEYCODE = 348;
	
	private static Camera camera;
	private static long window;
	private static float mouseSpeed;
	
	public static Mouse mouse;
	public static KeyEventManager keyboard;
	
	private static boolean[] pressedKeys = new boolean[348];
	
	public static boolean mouseLocked = false;
	
	public static void init(long windowID) {
		window = windowID;
		mouse = new Mouse(windowID);
		keyboard = new KeyEventManager(windowID);
	}
	
	public static void loadConfigValues(){
		mouseSpeed = Mist.getInstance().getConfigValuef("mouse_speed");
	}
	
	public static void setCamera(Camera cam){
		camera = cam;
	}
	
	public static void keyEvent(int keycode, int action, int mods){
		boolean keyDown = (action == GLFW.GLFW_KEY_DOWN);
		pressedKeys[keycode] = keyDown;
		System.out.println(action);
	}
	
	
	public static void mouseClick(int button, int action, int mods){
		//TODO: perform RayCast to decide if the player clicked in the world. Lock their mouse while they hold the button if so.
		if(action == GLFW.GLFW_PRESS){
			if(button == 0 || button == 1)
				mouseLocked = true;
		}else if (action == GLFW.GLFW_RELEASE){
			if(button == 0 || button == 1)
				mouseLocked = false;
		}
		
		//if(mouseLocked)
			//GLFW.glfwSetInputMode(window, , value);
	}
	
	public static void mouseMove(int x, int y){
		if(mouseLocked){
			camera.rotateX((float)y / mouseSpeed, true);
			camera.rotateY((float)x / mouseSpeed, true);
		}
	}
}