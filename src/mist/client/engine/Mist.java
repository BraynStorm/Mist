package mist.client.engine;

import java.io.File;

import mist.client.engine.render.RenderEngine;
import mist.common.utils.Version;


public class Mist {
	private static Mist mist;
	
	private Version clientVersion;
	private Window gameWindow;
	private RenderEngine renderEngine;
	
	private GameState gameState;
	
	public static boolean isDev = true;
	
	public static String mainFolder = ((new File(System.getProperty("java.class.path"))).getAbsoluteFile().getParentFile().toString()).split(";")[0];
	
	/*
	 * 1. Fix version control
	 * 2. Fix nameing.
	 * 3. Test out some models and decide the way of model importing that will be used.
	 * <i>ezpz they said... funny they said...</i>
	 */
	
	public Mist(){
		gameState = GameState.LOGIN;
		clientVersion = Version.maybeRead("Pre-Pre-Pre-Alpha");
		gameWindow = Window.create(800, 600, "Mist Client " + clientVersion.toString());
		gameWindow.show();
		renderEngine = new RenderEngine(gameWindow);
		
	}
	
	public void cycle(){
		while(!gameWindow.shouldClose()){
			gameWindow.startRenderingCycle();
			
			/* Render */
			renderEngine.doRender();
			
			gameWindow.endRenderingCycle();
		}
		
		
		clientVersion.saveAndUpdate();
	}
	
	public GameState getGameState(){
		return gameState;
	}
	
	public static void main(String[] args) {
		mist = new Mist();
		mist.cycle();
	}
	
	public static Mist getInstance(){
		return mist;
	}

	public Window getWindow() {
		return gameWindow;
	}

}
