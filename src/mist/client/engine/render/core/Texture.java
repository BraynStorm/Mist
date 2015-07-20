package mist.client.engine.render.core;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {

	public int textureID;
	
	Format format;
	
	int width;
	int height;
	
	public Texture(int id, int width, int height, Format format) {
		this.format = format;
		this.width = width;
		this.height = height;
		textureID = id;
		
	}
	
	public void bind(){
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

}
