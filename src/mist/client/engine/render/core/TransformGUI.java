package mist.client.engine.render.core;

import mist.client.engine.Mist;
import mist.client.engine.Window;
import mist.client.engine.render.utils.BSUtils;

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
		translation.x = (x*2) / window.getWidth();
		translation.y = (y*2) / window.getHeight();
	}
	
	/**
	 * [IN PIXELS]
	 * @param x
	 * @param y
	 */
	public void moveOnScreen(float x, float y){
		translation.x += (x*2) / window.getWidth();
		translation.y += (y*2) / window.getHeight();
	}
	
	/**
	 * FONTS ONLY!
	 * @param offset
	 * @return
	 */
	public Matrix4f getTansformation(float offset) {
		Matrix4f t = new Matrix4f().translation(translation);
		Matrix4f r = new Matrix4f().rotate(rotation);
		Matrix4f s = new Matrix4f().scale(scale.x, scale.y * BSUtils.getAspectRatio(window), scale.z);
		
		Matrix4f offset_mat = new Matrix4f().translation(offset, 0, 0);
		
		return t.mul(s.mul(r.mul(offset_mat)));
	}
}
