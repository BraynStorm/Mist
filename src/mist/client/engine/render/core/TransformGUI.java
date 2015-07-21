package mist.client.engine.render.core;

import mist.client.engine.Mist;
import mist.client.engine.Window;

public class TransformGUI extends Transform{
	
	private Window window;
	
	public TransformGUI() {
		super();
		this.translation.z = -1;
		window = Mist.getInstance().getWindow();
	}
	
	/**
	 * [IN PIXELS]
	 * @param x
	 * @param y
	 */
	public void setScreenTranslation(float x, float y){
		translation.x = x / window.getWidth();
		translation.y = y / window.getHeight();
	}
	
	/**
	 * [IN PIXELS]
	 * @param x
	 * @param y
	 */
	public void moveOnScreen(float x, float y){
		translation.x += x / window.getWidth();
		translation.y += y / window.getHeight();
	}
	
}
