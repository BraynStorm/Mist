package mist.client.engine.event;

public class MouseEvent {
	
	public int mouseX = 0;
	public int mouseY = 0;
	/**
	 * GLFW.MOUSE_BUTTON_#...
	 */
	public int button = 0;
	
	/**
	 * 0 - no buttons were pressd\n1 - press\n 2 - release 
	 */
	public int action = 0;
	
	/**
	 * The amount scrolled.
	 */
	public int scrollY = 0;
	
	
	public MouseEvent(int mouseX, int mouseY, int button, int action) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.button = button;
		this.action = action;
	}
	
	public MouseEvent(int mouseX, int mouseY, int scrollY){
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.scrollY = scrollY;
	}
	
}
