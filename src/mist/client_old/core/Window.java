package mist.client_old.core;

import mist.client_old.util.Camera;
import mist.client_old.util.GLUtils;
import mist.client_old.util.Model;
import mist.client_old.util.ModelManager;
import mist.client_old.util.Renderable;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
	public int width = 500;
	public int height = 500;
	
	public static Camera camera;
	
	private long window;
	
	private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWCursorEnterCallback cursorEnterCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    
    public static ArrayList<Renderable> uiElements = new ArrayList<Renderable>();
    
    public static boolean widthOrHeightChanged = true;
	protected boolean mouseDown;
	
	public Window(){
		camera = new Camera();
		try {
            init();
            loop();
            
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
            mouseButtonCallback.release();
            scrollCallback.release();
            windowSizeCallback.release();
            cursorEnterCallback.release();
            cursorPosCallback.release();
        }
	}
	
	public long getWindow(){
		return window;
	}
	
	public void init(){
		// Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
        
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        width = 480;
        height = 640;
 
        // Create the window
        window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
	        @Override
	        public void invoke(long window, int key, int scancode, int action, int mods) {
	            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
	                glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
	        }
        });
        
        // ON RESIZE
        glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke(long window, int width, int height) {
				Window.this.width = width;
				Window.this.height = height;
				widthOrHeightChanged = true;
			}
		});
		
        // ON MOUSE BUTTON PRESS
		glfwSetCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback() {
			
			@Override
			public void invoke(long window, int button, int action, int mods) {
				if(button == 0){
					if(action == GLFW_PRESS){
						mouseDown = true;
					}else{
						mouseDown = false;
					}
				}
			}
		});
		
		
		
		// ON SCROLL
		glfwSetCallback(window, scrollCallback = new GLFWScrollCallback() {
			
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				
			}
		});
		
		glfwSetCallback(window, cursorPosCallback = new GLFWCursorPosCallback(){

			double lastX = 0;
			double lastY = 0;
			
			
			
			@Override
			public void invoke(long window, double xpos, double ypos) {
				
				if(mouseDown && (lastX != 0 || lastY != 0)){
					
					camera.rotateCameraR((float)((ypos - lastY) * camera.getGetRotationSpeed()), (float)((lastX - xpos) * camera.getGetRotationSpeed()), 0);
				}
				
				lastX = xpos;
				lastY = ypos;
			}
			
		});
		
		glfwSetCallback(window, scrollCallback = new GLFWScrollCallback() {
			
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				camera.changeRotationSpeed((float)yoffset * 0.1f);
				
				System.out.println(camera.getGetRotationSpeed());
			}
		});
		
		glfwSetCallback(window, keyCallback = new GLFWKeyCallback() {
		
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(action == GLFW_PRESS || action == GLFW_REPEAT)
					if(key == GLFW_KEY_W){
						camera.moveCameraR(0, 0, -1);
					}else if(key == GLFW_KEY_A){
						camera.moveCameraR(1, 0, 0);
					}else if(key == GLFW_KEY_S){
						camera.moveCameraR(0, 0, 1);
					}else if(key == GLFW_KEY_D){
						camera.moveCameraR(-1, 0, 0);
					}
			}
		});
		
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - width) / 2,
            (GLFWvidmode.height(vidmode) - height) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        
        glfwShowWindow(window);
	}
	
	private void loop() {
		GLContext.createFromCurrent();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_ALPHA);
		glEnable(GL_DEPTH_TEST);
		glClearColor(.2f, .2f, .2f, 1f);
		
		try {
			ModelManager.loadModel("C:/objectFile2.obj");
			ModelManager.loadModel("C:/terrain.obj");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Model k = ModelManager.models.get("objectFile2");
		Model terrain = ModelManager.models.get("terrain");
		
		//uiElements.add(e)
		
		/**
		 * glBegin(GL_QUADS);
					glColor3f(0.7f, 0.1f, 0.1f);
					glVertex3f(1, 1, 0);
					glColor3f(0.7f, 0.1f, 0.1f);
					glVertex3f(20, 1, 0);
					glColor3f(0.7f, 0.1f, 0.1f);
					glVertex3f(20, 20, 0);
					glColor3f(0.7f, 0.1f, 0.1f);
					glVertex3f(20, 1, 0);
				glEnd();
		 */
		k.scale(.1f,.1f,.1f);
		//k.translate(5, 0, 0);
		terrain.scale(.0001f,.0001f,.0001f);
		
        // Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		
		ByteBuffer perspectiveMatix;
		float aspect = width / height;
		
		
		
		long lastTime = System.currentTimeMillis();
		long nowTime = System.currentTimeMillis();
		long i = 0;
		while ( glfwWindowShouldClose(window) == GL_FALSE ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			
			nowTime = System.currentTimeMillis();
			if(lastTime < nowTime - 1000){
				System.out.println("FPS: " + i);
				i=0;
				lastTime = nowTime;
			}
			
			if(widthOrHeightChanged)
				aspect = width / height;
			
			glViewport(0, 0, width, height);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			
			//glOrtho(0, width, 0, height, -10000, 10000);
			GLUtils.glPerspective(150, width/height, 100000,-100);
			//perspectiveMatix = GLUtils.getPerspectiveProjectionMatrix(90, aspect, 1, 100);
			
			glUseProgram(0);
			//glUniformMatrix4fv("projMat", 1, GL_FALSE, persl);
			
			
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			
			glPushMatrix();
				camera.applyPossitionToRenderer();
				
				k.rotate(0,1,0);
				k.render();
			 
				terrain.rotate(0,1,0);
				terrain.render();
			glPopMatrix();
			
			glUseProgram(0);
			
			
			//Draw UI
			//glOrtho(0, width, 0, height, -10000, 10000);
			glPushMatrix();
				for(Renderable e : uiElements){
					e.render();
				}
			glPopMatrix();
			
		            
            // Poll for window events. The key callback above will only be
            // invoked during this call.
			glfwPollEvents();
            glfwSwapBuffers(window); // swap the color buffers
            i++;

		}
    }
	
	
}
