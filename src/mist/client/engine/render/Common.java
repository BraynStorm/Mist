package mist.client.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import mist.client.engine.Mist;

public class Common {
	public static int RECTANGLE_IBO;
	public static int RECTANGLE_DRAWCOUNT = 6;
	
	public static void init(){
		IntBuffer buffer = BufferUtils.createIntBuffer(RECTANGLE_DRAWCOUNT);
		buffer.put(3).put(2).put(0);
		buffer.put(2).put(1).put(0);
		buffer.flip();
		
		RECTANGLE_IBO = bufferData(buffer, GL_ELEMENT_ARRAY_BUFFER);
	}
	
	
	// PIXELS!
	public static int createRectangleVBO(int width, int height){ return createRectangleVBO(width, height, 0);	}
	public static int createRectangleVBO(int width, int height, float z){
		return createRectangleVBO(width, height, z, 1, 1);
	}
	public static int createRectangleVBO(int width, int height, float z, float s, float t){
		
		float w = ((float) width)  / ((float)Mist.getInstance().getWindow().getWidth());
		float h = ((float) height) / ((float)Mist.getInstance().getWindow().getHeight());
		
		float w2 = w / 2.0f;
		float h2 = h / 2.0f;
		
		System.out.println("Creating Rect VBO: " + w2 + ", " + h2);
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(20);
		buffer.put(-w2).put(-h2).put(z).put(0).put(1);
		buffer.put( w2).put(-h2).put(z).put(s).put(1);
		buffer.put( w2).put( h2).put(z).put(s).put(1.f-t);
		buffer.put(-w2).put( h2).put(z).put(0).put(1.f-t);
		buffer.flip();
		
		return bufferData(buffer, GL_ARRAY_BUFFER);
	}
	public static int bufferData(FloatBuffer data, int type){int buffer = glGenBuffers(); bufferData(buffer, data, type, GL_STATIC_DRAW); return buffer;}
	public static int bufferData(FloatBuffer data, int type, int drawType){int buffer = glGenBuffers(); bufferData(buffer, data, type, drawType); return buffer;}
	public static void bufferData(int buffer, FloatBuffer data, int type, int drawType){
		glBindBuffer(type, buffer);
		glBufferData(type, data, GL_STATIC_DRAW);
		glBindBuffer(type, 0);
	}
	
	public static int bufferData(IntBuffer data, int type){int buffer = glGenBuffers(); bufferData(buffer, data, type, GL_STATIC_DRAW); return buffer;}
	public static int bufferData(IntBuffer data, int type, int drawType){int buffer = glGenBuffers(); bufferData(buffer, data, type, drawType); return buffer;}
	public static void bufferData(int buffer, IntBuffer data, int type, int drawType){
		glBindBuffer(type, buffer);
		glBufferData(type, data, GL_STATIC_DRAW);
		glBindBuffer(type, 0);
	}
	
}
