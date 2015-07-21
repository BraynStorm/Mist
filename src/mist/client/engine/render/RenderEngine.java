package mist.client.engine.render;

import java.awt.Font;
import java.util.ArrayList;

import mist.client.engine.GameState;
import mist.client.engine.Mist;
import mist.client.engine.Time;
import mist.client.engine.Window;
import mist.client.engine.render.core.Camera;
import mist.client.engine.render.core.Model;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.core.TransformGUI;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.core.fonts.FontLibrary;
import mist.client.engine.render.core.fonts.TrueTypeFont;
import mist.client.engine.render.loaders.ModelLoader;
import mist.client.engine.render.utils.BSUtils;


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
		guiShader.addUniform("model_isFont");
		guiShader.addUniform("model_transform");
		guiShader.addUniform("screen_aspect_ratio");
		guiShader.addUniform("model_color");
		guiShader.addUniform("model_hasTexture");
		
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
					font.getFontTexture().bind();
					model.render();
					//model.setRotation(-30f, movement * 100, 0);
				}
				
				guiShader.bind();
				guiShader.setUniformf("screen_aspect_ratio", BSUtils.getAspectRatio(window));
				
				for(Drawable drawble : gui){
					drawble.render();
				}
				
				
				fontTransform.setRotation(0, 0, movement*100);
				font.drawString(fontTransform, "ASDFG_asdfg", null);
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
	
	private static void loadLoginScreen(){
		
		ModelLoader.loadModel("tex_cube");
		
		font = FontLibrary.requestFontWithSize(guiShader, "Calibri", Font.TRUETYPE_FONT, 40);
		
		world.add(ModelLoader.getNewModel("tex_cube", worldShader));
		world.get(0).setTranslation(1, 0, 15f);
		
		guiShader.bind();
		guiShader.setUniformi("model_isFont", 1);
		
		System.out.println(font.getWidth("LOL"));
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
