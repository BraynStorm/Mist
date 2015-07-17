package mist.client.engine.render;

import java.util.ArrayList;

import mist.client.engine.GameState;
import mist.client.engine.Mist;
import mist.client.engine.Window;
import mist.client.engine.render.core.Camera;
import mist.client.engine.render.core.Matrix4f;
import mist.client.engine.render.core.Model;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.loaders.ModelLoader;
import mist.client.engine.render.loaders.TextureLoader;

public class RenderEngine {
	
	private Window window;
	private Shader worldShader;
	private Shader guiShader;
	private Camera camera;
	private Texture lol;
	private float fps;
	
	private ArrayList<Model> world;
	
	public RenderEngine(Window window, Camera cam){
		this.window = window;
		camera = cam;
		
		worldShader = new Shader();
		worldShader.loadShader("world");
		worldShader.addUniform("projection_transform");
		worldShader.addUniform("model_transform");
		worldShader.addUniform("model_color");
		worldShader.addUniform("model_hasTexture");
		worldShader.addUniform("camera_transform");
		
		guiShader = new Shader();
		
		world = new ArrayList<Model>();
		fps = 0;
		
		loadLoginScreen();
		
	}
	
	public void addToWorldRender(Model object){
		world.add(object);
	}
	
	public void addToGUIRenderer(Drawable object){
		
	}
	
	
	public void startCycle(){
		long lastTime = 0;
		long nowTime = 0;
		long frames = 0; 
		
		lol = TextureLoader.getTexture("tex_cube");
		lol.bind();
		while(!window.shouldClose()){
			nowTime = System.currentTimeMillis();
			
			if(nowTime > (lastTime + 1000)){
				fps = (1000 * frames)/(nowTime - lastTime + 1000);
				frames = 0;
				System.out.println(fps);
				lastTime = nowTime;
			}
			
			window.startRenderingCycle();
			
			/* Render */
			cycle();
			
			window.endRenderingCycle();
			
			frames++;
		}
		
	}
	
	private static GameState lastState = GameState.UNKNOWN;
	private static GameState nowState = GameState.BOOTINGUP;
	private static int i = 0;
	
	public void cycle(){
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
					model.render();
					model.setRotation(-30f, (i/100.0f), 0);
				}
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
	}
	
	private void loadLoginScreen(){
		ModelLoader.loadModel("tex_cube", false, false);
		
		world.add(ModelLoader.getNewModel("tex_cube", worldShader));
		//world.get(0).setScale(1f, 1f, 1f);
		//world.get(0).setTranslation(0, 0, -15f);
		world.get(0).setTranslation(0, 0, 5f);
	}

	public void destroy() {
		worldShader.delete();
		guiShader.delete();
		
	}
	
}
