package mist.client.engine.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import mist.client.engine.GameState;
import mist.client.engine.Mist;
import mist.client.engine.Time;
import mist.client.engine.Window;
import mist.client.engine.render.core.Camera;
import mist.client.engine.render.core.Model;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.gui.Font;
import mist.client.engine.render.loaders.ModelLoader;
import mist.client.engine.render.loaders.TextureLoader;


public class RenderEngine {
	
	private static Window window;
	private static Shader worldShader = new Shader();
	private static Shader guiShader = new Shader();
	private static Camera camera;
	private static float fps = 0;
	
	private static ArrayList<Model> world = new ArrayList<Model>();
	private static ArrayList<Drawable> gui = new ArrayList<Drawable>();
	
	
	private static GameState lastState = GameState.UNKNOWN;
	private static GameState nowState = GameState.BOOTINGUP;
	private static int i = 0;
	private static int frameCounter = 0;
	
	public static void prepare(){
		RenderEngine.window = Mist.getInstance().getWindow();
		camera = Mist.getInstance().getCamera();
		
		worldShader.loadShader("world");
		worldShader.addUniform("projection_transform");
		worldShader.addUniform("model_transform");
		worldShader.addUniform("model_color");
		worldShader.addUniform("model_hasTexture");
		worldShader.addUniform("camera_transform");
		
		guiShader.loadShader("gui");
		guiShader.addUniform("model_transform");
		guiShader.addUniform("model_color");
		guiShader.addUniform("model_hasTexture");
		
		loadLoginScreen();
	}
	
	
	public static void loop(){
		window.startRenderingCycle();
		fps = (frameCounter * 1000 * 1000) / Time.getDelta();
		
		nowState = Mist.getInstance().getGameState();
		
		worldShader.bind();
		worldShader.setUniform("camera_transform", camera.getTransform());
		
		if(!lastState.equals(nowState) && !nowState.equals(GameState.UNKNOWN) && !nowState.equals(GameState.BOOTINGUP)){
			worldShader.setUniform("projection_transform", Transform.getProjection(nowState.toString()));
		}
		
		
		
		switch (nowState){
			case BOOTINGUP:
				Mist.getInstance().setGameState(GameState.LOGIN);
				break;
			case LOGIN:
				worldShader.bind();
				
				for(Model model : world){
					TextureLoader.bindTexture("tex_cube");
					//model.render();
					model.setRotation(-30f, (i/10.0f), 0);
				}
				
				guiShader.bind();
				fontTransform.setRotation(0, 0, i/10.0f);
				font.renderChar(0, fontTransform);
				
				break;
			case LOADING:
				break;
			case CHARSELECT:
				
				break;
			case INWORLD:
				
				
				break;
			default: break;
		}
		i++;
		lastState = nowState;
		window.endRenderingCycle();
		frameCounter++;
	}
	
	
	private static Font font;
	private static Transform fontTransform;
	private static void loadLoginScreen(){
		ModelLoader.loadModel("tex_cube", false, false);
		TextureLoader.loadTexture("tex_cube", "tex_cube", false, GL11.GL_NEAREST);
		TextureLoader.loadTexture("calibri", "Calibri", false, GL11.GL_NEAREST);
		font = new Font(guiShader, "calibri");
		fontTransform = new Transform();
		fontTransform.setTranslation(0, 0, -0.5f);
		fontTransform.setRotation(170, 0, 0);
		world.add(ModelLoader.getNewModel("tex_cube", worldShader));
		//world.get(0).setScale(1f, 1f, 1f);
		//world.get(0).setTranslation(0, 0, -15f);
		world.get(0).setTranslation(0, 0, 5f);
		
		
		
	}
	
	public static void addToWorldRender(Model object){
		world.add(object);
	}
	
	public static void addToGUIRenderer(Drawable object){
		gui.add(object);
	}

	public static void destroy() {
		worldShader.delete();
		guiShader.delete();
		
	}


	public static void setProps(Window gameWindow, Camera camera) {
		RenderEngine.window = gameWindow;
		RenderEngine.camera = camera;
		
	}
	
}
