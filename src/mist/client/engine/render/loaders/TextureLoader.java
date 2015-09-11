package mist.client.engine.render.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import mist.client.engine.Mist;
import mist.client.engine.render.core.Texture;

public class TextureLoader {

	private static String texturesPath = Mist.dataFolder + "/textures/";
	
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture loadTexture(String name, boolean flipped, int filter){
		return loadTexture(name, flipped, true, filter);
	}
	
	public static Texture loadTexture(String name, boolean flipped, boolean repeat, int filter){
		try {
			PNGDecoder decoder = new PNGDecoder(new FileInputStream(new File(texturesPath + name + ".png")));
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
			buffer.flip();
			
			int textureID = GL11.glGenTextures();
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP);
			
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			
			Texture t = new Texture(textureID,  decoder.getWidth(), decoder.getHeight(), Format.RGBA);
			textures.put(name, t);
			return t;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void bindTexture(String name){
		Texture t = getTexture(name);
		if(t != null){
			t.bind();
		}else{
			System.out.println("[WARNING] Trying to bind a NULL texture.");
		}
	}
	
	public static Texture getTexture(String name){
		Texture t = textures.get(name);
		if(t == null)
			return loadTexture(name, false, GL11.GL_LINEAR);
		return t;
	}
	
	public static void unload(String name){
		Texture t = getTexture(name);
		if(t != null)
			GL11.glDeleteTextures(t.textureID);
	}
}
