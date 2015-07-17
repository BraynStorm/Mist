package mist.client.engine.render.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import mist.client.engine.render.core.Vector2f;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vector3i;

import org.lwjgl.BufferUtils;

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
}
