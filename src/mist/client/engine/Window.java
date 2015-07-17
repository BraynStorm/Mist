package mist.client.engine;

import java.io.Closeable;
import java.io.IOException;
import java.nio.DoubleBuffer;

import mist.client.engine.event.EventManager;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

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

@SuppressWarnings("unused")
public class Window implements Closeable {
	
	private int width, height;
	private long windowID;
	
	private static String title;
	
	private Window(){
		
	}
	
	public static Window create(int initW, int initH, String title) throws IllegalStateException{
		if ( GLFW.glfwInit() != GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
		
		Window w = new Window();
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL_TRUE);
		
		w.width = initW;
		w.height = initH;
		
		w.windowID = GLFW.glfwCreateWindow(initW, initH, title, 0, 0);
		EventManager.init(w.windowID);
		return w;
	}
	
	public void show(){
		GLFW.glfwShowWindow(windowID);
		GLFW.glfwMakeContextCurrent(windowID);
		GLContext.createFromCurrent();
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		//glEnable(GL_CULL_FACE);
		//glCullFace(GL_BACK);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		//glEnable(GL_TEXTURE_2D);
		//GLFW.glfwSwapInterval(1);
	}
	
	public void startRenderingCycle(){
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
		glClearColor(0.5f, 0.5f, 0.5f, 1);
	}
	
	public void endRenderingCycle(){
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(windowID);
	}
	
	public void setSize(int nWidth, int nHeight){
		width = nWidth;
		height = nHeight;
		
		GLFW.glfwSetWindowSize(windowID, width, height);
		
	}
	
	public boolean shouldClose(){
		return GLFW.glfwWindowShouldClose(windowID) == 1;
	}
	
	
	public void setTitle(String nTitle){
		title = nTitle;
		GLFW.glfwSetWindowTitle(windowID, title);
	}
	

	@Override
	public void close() throws IOException {
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static String getTitle() {
		return title;
	}
	
}
