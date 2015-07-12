package mist.client.engine.render;

import mist.client.engine.GameState;
import mist.client.engine.Mist;
import mist.client.engine.Window;

public class RenderEngine {
	
	private Window window;
	
	public RenderEngine(Window window){
		this.window = window;
	}
	
	public void addToWorldRender(){
		
	}
	
	public void addToGUIRenderer(){
		
	}
	
	
	public void doRender(){
		if(Mist.getInstance().getGameState() == GameState.LOGIN){
			/* Render login screen and BG */
			
			
			
			return;
		}
	}
	
}
