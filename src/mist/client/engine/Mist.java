package mist.client.engine;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

import mist.client.engine.event.EventManager;
import mist.client.engine.render.RenderEngine;
import mist.client.engine.render.core.Camera;
import mist.client.engine.render.core.ModelTemplate;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.loaders.ModelLoader;
import mist.client.engine.render.loaders.TextureLoader;
import mist.common.utils.Version;


public class Mist {
	private static Mist mist;
	
	private Version clientVersion;
	private Window gameWindow;
	private Camera camera;
	private RenderEngine renderEngine;
	
	private Config config;
	
	private GameState gameState;
	
	public static boolean isDev = true;
	
	public static String mainFolder = ((new File(System.getProperty("java.class.path"))).getAbsoluteFile().getParentFile().toString()).split(";")[0];
	public static String dataFolder = mainFolder + "/data";
	
	/*
	 * 1. Fix version control
	 * 2. Fix nameing.
	 * 3. Test out some models and decide the way of model importing that will be used.
	 * <i>ezpz they said... funny they said...</i>
	 */
	
	public Mist(){
		gameState = GameState.BOOTINGUP;
		clientVersion = Version.maybeRead("Pre-Pre-Pre-Alpha");
		config = new Config("config");
		gameWindow = Window.create(800, 800, "Mist Client " + clientVersion.toString());
		gameWindow.show();
		camera = new Camera();
		EventManager.setCamera(camera);
		RenderEngine.setProps(gameWindow, camera);
		
		
		// GUI
		try {
			TextureLoader.loadTexture("button_stripe", false, GL11.GL_LINEAR);
			//TextureLoader.loadTexture("tex_cube", false, GL11.GL_LINEAR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cycle(){
		System.out.println(glGetString(GL_VERSION));
		
		EventManager.prepare();
		RenderEngine.prepare();
		
		while(!gameWindow.shouldClose()){
			Time.loop();
			EventManager.loop();
			RenderEngine.loop();
		}
		
		clientVersion.saveAndUpdate();
		closingStage();
	}
	
	public float getConfigValuef(String name){
		return (float)config.get(name);
	}
	
	public int getConfigValuei(String name){
		return (int)config.get(name);
	}
	
	public String getConfigValueS(String name){
		return (String)config.get(name);
	}
	
	private void closingStage(){
		ModelLoader.destroy();
		RenderEngine.destroy();
		int k = GL11.glGetError();
		System.out.println(k);
		while (k != 0){
			k = GL11.glGetError();
			System.out.println(k);
		}
		
	}
	
	public GameState getGameState(){
		return gameState;
	}
	
	public void setGameState(GameState state){
		this.gameState = state;
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

	public Camera getCamera() {
		return camera;
	}

}
