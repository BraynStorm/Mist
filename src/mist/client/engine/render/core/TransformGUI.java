package mist.client.engine.render.core;

import mist.client.engine.Mist;
import mist.client.engine.Window;
import mist.client.engine.render.gui.GUIDrawLevel;
import mist.client.engine.render.utils.BSUtils;

public class TransformGUI extends Transform{
	
	private Window window;
	private Vector3f screenTranslation = new Vector3f(0,0,0);
	private GUIDrawLevel drawLevel = GUIDrawLevel.LOWMOST_LEVEL;
	
	
	public TransformGUI() {
		super();
		this.translation.z = 0;
		window = Mist.getInstance().getWindow();
	}
	
	public void setDrawLevel(GUIDrawLevel level){
		drawLevel = level;
	}
	
	public GUIDrawLevel getDrawLevel(){
		return drawLevel;
	}
	
	private void fixTraslation2D(){
		setTranslation(2 * (screenTranslation.x / window.getWidth()) - 1, 2 * (-screenTranslation.y / window.getHeight()) + 1, drawLevel.getZValue());
	}
	
	public void setScreenTranslation(float x, float y){
		screenTranslation.x = x;
		screenTranslation.y = y;
	}
	
	public void moveOnScreen(float x, float y){
		screenTranslation.x += x;
		screenTranslation.y += y;
	}
	
	@Override
	public Matrix4f getTransformation() {
		fixTraslation2D();
		return super.getTransformation();
	}
	
	/**
	 * FONTS ONLY!
	 * @param offset
	 * @return
	 */
	public Matrix4f getFontTransformation(float offset) {
		fixTraslation2D();
		Matrix4f t = new Matrix4f().translation(translation);
		Matrix4f r = new Matrix4f().rotate(rotation);
		Matrix4f s = new Matrix4f().scale(scale.x, scale.y * BSUtils.getAspectRatio(window), scale.z);
		
		Matrix4f offset_mat = new Matrix4f().translation(offset, 0, 0);
		
		return t.mul(s.mul(r.mul(offset_mat)));
	}

	@Override
	public TransformGUI clone() {
		TransformGUI transformGUI = new TransformGUI();
		
		transformGUI.rotation = this.rotation.clone();
		transformGUI.scale = this.scale.clone();
		transformGUI.translation= this.translation.clone();
		
		transformGUI.screenTranslation = this.screenTranslation.clone();
		
		return transformGUI;
	}
}
