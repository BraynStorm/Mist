package mist.client.engine.render.utils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import mist.client.engine.render.core.Vector2f;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vector3i;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class BSUtils {
	public static FloatBuffer floatBufferFrom3DList(ArrayList<Vector3f> list){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.size() * 3);
		for(Vector3f v : list){
			buffer.put(v.x);
			buffer.put(v.y);
			buffer.put(v.z);
		}
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer floatBufferFrom2DList(ArrayList<Vector2f> list){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.size() * 2);
		for(Vector2f v : list){
			buffer.put(v.x);
			buffer.put(v.y);
		}
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer intBufferFrom3DList(ArrayList<Vector3i> list){
		IntBuffer buffer = BufferUtils.createIntBuffer(list.size() * 3);
		for(Vector3i v : list){
			buffer.put(v.x);
			buffer.put(v.y);
			buffer.put(v.z);
		}
		buffer.flip();
		return buffer;
	}
	
	
	public static IntBuffer combineBuffers(ArrayList<IntBuffer> list, int bufferLength){
		IntBuffer finalBuffer = BufferUtils.createIntBuffer(list.size() * bufferLength);
		for(int i = 0; i < list.size(); i++){
			for(int j = 0; j < bufferLength; j++){
				finalBuffer.put((i * bufferLength) + j, list.get(i).get());
			}
		}
		//finalBuffer.flip();
		return finalBuffer;
	}
	
	
	public static FloatBuffer bufferFromArray(float [] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static void radianizeVector3f(Vector3f vector) {
		vector.x = (float)Math.toRadians(vector.x);
		vector.y = (float)Math.toRadians(vector.y);
		vector.z = (float)Math.toRadians(vector.z);
		
	}
	
	public static int textureFromImage(BufferedImage img){
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		int width = img.getWidth();
		int height = img.getHeight();
		int color = 0;
		float[] data = new float [width  * height];
		
		int ii = 0;
		for(int i = height - 1; i >= 0; i++){
			for(int j = 0; j < width; j++){
				color = img.getRGB(i, j);
				data[ii * width + j + 3] = color & 0xFF;
				
			}
			ii++;
		}
		
		FloatBuffer buffer = FloatBuffer.wrap(data);
		buffer.flip(); // TODO: Chcek if flipping is neaded;
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_FLOAT, buffer);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return id;
	}
}
