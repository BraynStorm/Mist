package mist.client.engine.event;

public class EventManager {
	public static Mouse mouse;
	public static KeyEventManager keyboard;
	
	public static void init(long windowID) {
		mouse = new Mouse(windowID);
		keyboard = new KeyEventManager(windowID);
	}
}