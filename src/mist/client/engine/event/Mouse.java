package mist.client.engine.event;

public class Mouse {
	private MouseButtonEventManager mouseButtonEventManager;
	private CursorPositionManager cursorPos;
	private ScrollEventManager scroll;
	
	private long windowID;
	
	public Mouse(long windowID){
		this.windowID = windowID;
		
		cursorPos = new CursorPositionManager(windowID);
		mouseButtonEventManager = new MouseButtonEventManager(windowID);
		scroll = new ScrollEventManager(windowID);
	}
	
	public int getMouseX(){
		return cursorPos.getMouseX();
	}
	
	public int getMouseY(){
		return cursorPos.getMouseY();
	}
	
}
