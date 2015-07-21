package mist.client.engine.render.gui.objects;

import mist.client.engine.render.IDrawable;
import mist.client.engine.render.core.TransformGUI;

public class PlayerFrame implements IDrawable {
	
	private TransformGUI transform;
	
	private GUIBar healthBar;
	
	public PlayerFrame(){
		transform = new TransformGUI();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScale(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslation(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRotation(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

}
