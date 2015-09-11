package mist.client.engine.render;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import mist.client.engine.GameState;
import mist.client.engine.Mist;
import mist.client.engine.Time;
import mist.client.engine.Window;
import mist.client.engine.render.core.Camera;
import mist.client.engine.render.core.Model;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.core.TransformGUI;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.core.fonts.FontLibrary;
import mist.client.engine.render.core.fonts.TrueTypeFont;
import mist.client.engine.render.gui.shapes.Rectangle;
import mist.client.engine.render.gui.shapes.RoundedRectangle;
import mist.client.engine.render.loaders.ModelLoader;
import mist.client.engine.render.loaders.TextureLoader;


public class RenderEngine {
	
	private static Window window;
	private static Shader worldShader = new Shader();
	private static Shader guiShader = new Shader();
	private static Shader guiButtonShader = new Shader();
	private static Camera camera;
	private static float fps = 0;
	
	private static ArrayList<Model> world = new ArrayList<Model>();
	private static ArrayList<Drawable> gui = new ArrayList<Drawable>();
	
	
	private static GameState lastState = GameState.UNKNOWN;
	private static GameState nowState = GameState.BOOTINGUP;
	
	public static void prepare(){
		RenderEngine.window = Mist.getInstance().getWindow();
		camera = Mist.getInstance().getCamera();
		
		worldShader.loadShader("world");
		worldShader.addUniform("projection_transform");
		worldShader.addUniform("model_transform");
		worldShader.addUniform("model_color");
		worldShader.addUniform("model_hasTexture");
		worldShader.addUniform("camera_translation");
		worldShader.addUniform("camera_rotation");
		worldShader.addUniform("bone");
		
		guiShader.loadShader("gui");
		guiShader.addUniform("model_transform");
		//guiShader.addUniform("model_color");
		//guiShader.addUniform("model_hasTexture");
		
		guiButtonShader.loadShader("gui_button");
		guiButtonShader.addUniform("innerAnimationTL");
		guiButtonShader.addUniform("model_transform");
		
		Common.init();
		
		fontTransform = new TransformGUI();
		
		loadLoginScreen();
	}
	
	private static float movement = 0;
	
	public static void loop(){
		window.startRenderingCycle();
		
		
		nowState = Mist.getInstance().getGameState();
		
		worldShader.bind();
		worldShader.setUniform("camera_translation", camera.getTranslation());
		worldShader.setUniform("camera_rotation", camera.getRotation());
		
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
					model.render();
					//model.setRotation(-30f, movement * 100, 0);
				}
				
				guiShader.bind();
				
				fontTransform.setRotation(0,0, movement * 10);
				font.drawString(fontTransform, "[Player_Name]", new Vector4f(0,1,0, 1));
				
				for(Drawable drawble : gui){
					drawble.render();
				}
				
				button.render();
				
				break;
			case LOADING:
				break;
			case CHARSELECT:
				
				break;
			case INWORLD:
				
				
				break;
			default: break;
		}
		lastState = nowState;
		window.endRenderingCycle();
		movement += Time.getDeltaSeconds();
		
		fps = (float)(1 / Time.getDeltaSeconds());
	}
	
	
	private static TrueTypeFont font;
	private static TransformGUI fontTransform;
	
	private static TransformGUI buttonTransform;
	private static RoundedRectangle button;
	
	private static Rectangle recteeed;
	
	private static void loadLoginScreen(){
		
		ModelLoader.loadModel("tex_cube");
		
		TextureLoader.loadTexture("button_corner", false, false, GL11.GL_LINEAR);
		TextureLoader.loadTexture("button_stripe", false, false, GL11.GL_LINEAR);
		
		font = FontLibrary.requestFontWithSize(guiShader, "Calibri", Font.TRUETYPE_FONT, 40);
		fontTransform.setScreenTranslation(0,50);//(window.getHeight()/2) - font.getLineHeight()
		
		
		buttonTransform = new TransformGUI();
		buttonTransform.setScreenTranslation(405, 405);
		
		
		button = new RoundedRectangle(guiShader, guiButtonShader, 400, 400, buttonTransform, "ASDdsadsasadsdasadsd");
		
		//Test:
		
		
		world.add(ModelLoader.getNewModel("tex_cube", worldShader));
		world.get(0).setTranslation(0, 0, 15f);
		
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
		guiButtonShader.delete();
	}


	public static void setProps(Window gameWindow, Camera camera) {
		RenderEngine.window = gameWindow;
		RenderEngine.camera = camera;
		
	}
	
}
