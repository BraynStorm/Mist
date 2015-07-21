package mist.client.engine.event;

import org.lwjgl.glfw.GLFW;

import mist.client.engine.Mist;
import mist.client.engine.Time;
import mist.client.engine.render.core.Camera;

public class EventManager {
	
	private static final int MAX_KEYCODE = 348;
	
	private static Camera camera;
	private static long window;
	private static float mouseSpeed;
	
	public static Mouse mouse;
	public static KeyEventManager keyboard;
	protected static WindowEvent windowResizeEvent;
	
	private static boolean[] pressedKeys = new boolean[348];
	
	public static boolean mouseLocked = false;
	
	public static void init(long windowID) {
		window = windowID;
		mouse = new Mouse(windowID);
		keyboard = new KeyEventManager(windowID);
		windowResizeEvent = new WindowEvent(windowID);
	}
	
	public static void prepare(){
		mouseSpeed = Mist.getInstance().getConfigValuef("mouse_speed");
	}
	
	public static void setCamera(Camera cam){
		camera = cam;
	}
	
	public static void keyEvent(int keycode, int action, int mods){
		boolean keyDown = (action == GLFW.GLFW_KEY_DOWN || action == GLFW.GLFW_REPEAT);
		pressedKeys[keycode] = keyDown;
		
		//System.out.println(action);
	}
	
	public static void loop(){
		// TODO: MOVEMENT!!!!
		
		if(pressedKeys[GLFW.GLFW_KEY_W]){
			camera.moveForward((float)(Time.getDeltaSeconds() * 1e4));
		}
		
		if(pressedKeys[GLFW.GLFW_KEY_S]){
			camera.moveBackward((float)(Time.getDeltaSeconds() * 1e4));
		}
		
		if(pressedKeys[GLFW.GLFW_KEY_A]){
			camera.moveLeft((float)(Time.getDeltaSeconds() * 1e4));
		}
		
		if(pressedKeys[GLFW.GLFW_KEY_D]){
			camera.moveRight((float)(Time.getDeltaSeconds() * 1e4));
		}
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
			camera.rotateX(-(float)y / mouseSpeed, true);
			camera.rotateY(-(float)x / mouseSpeed, true);
		}
	}

	public static void framebufferResize(int width, int height) {
		if(Mist.getInstance() != null)
			Mist.getInstance().getWindow().framebufferResize(width, height);
	}
}