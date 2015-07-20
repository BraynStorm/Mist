package mist.client.engine.render.core.fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import mist.client.engine.Mist;
import mist.client.engine.render.core.Matrix4f;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.core.Vertex;
import mist.client.engine.render.utils.BSUtils;

public class TrueTypeFont {
	
	private IntObject[] charArray = new IntObject[256];
	private Map customChars = new HashMap();
	
	private boolean antiAlias;
	
	private Texture fontTexture;
	private int textureWidth = 512;
	private int textureHeight = 512;
	
	private Font font;
	private FontMetrics fontMetrics;
	private int fontSize = 0;
	private int fontHeight = 0;
	
	private String name;
	private int style;
	private int quadVBO, quadIBO;
	
	private class IntObject {
		/** Character's width */
		public int width;

		/** Character's height */
		public int height;

		/** Character's stored x position */
		public int storedX;

		/** Character's stored y position */
		public int storedY;
	}

	private Shader shader;
	
	private Transform charTransform;
	private Transform textTransform;
	
	public TrueTypeFont(Font font, String name, boolean antiAlias) {
		
		this.font = font;
		this.fontSize = font.getSize();
		this.antiAlias = antiAlias;
		this.name = name;
	}
	
	public void setShader(Shader shader){
		this.shader = shader;
	}
	
	private BufferedImage getFontImage(char ch) {
		
		BufferedImage tempfontImage = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		if (antiAlias == true) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch);

		if (charwidth <= 0) {
			charwidth = 1;
		}
		int charheight = fontMetrics.getHeight();
		if (charheight <= 0) {
			charheight = fontSize;
		}

		// Create another image holding the character we are creating
		BufferedImage fontImage;
		fontImage = new BufferedImage(charwidth, charheight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		if (antiAlias == true) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		gt.setFont(font);

		gt.setColor(Color.WHITE);
		int charx = 0;
		int chary = 0;
		gt.drawString(String.valueOf(ch), (charx), (chary)
				+ fontMetrics.getAscent());

		return fontImage;

	}

	
	private void createSet() {
		
		try {
			
			BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();

			g.setColor(new Color(255,255,255,1));
			g.fillRect(0,0,textureWidth,textureHeight);
			
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			
			for (int i = 0; i < 256; i++) {
				
				// get 0-255 characters and then custom characters
				char ch = (char) i;
				
				BufferedImage fontImage = getFontImage(ch);

				IntObject newIntObject = new IntObject();

				newIntObject.width = fontImage.getWidth();
				newIntObject.height = fontImage.getHeight();

				if (positionX + newIntObject.width >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}

				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;

				if (newIntObject.height > fontHeight) {
					fontHeight = newIntObject.height;
				}

				if (newIntObject.height > rowHeight) {
					rowHeight = newIntObject.height;
				}

				// Draw it here
				g.drawImage(fontImage, positionX, positionY, null);

				positionX += newIntObject.width;
				
				charArray[i] = newIntObject;
				fontImage = null;
			}

			fontTexture = BSUtils.textureFromImage(imgTemp);

		} catch (IOException e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Draw a textured quad
	 * 
	 * @param drawX
	 *            The left x position to draw to
	 * @param drawY
	 *            The top y position to draw to
	 * @param drawX2
	 *            The right x position to draw to
	 * @param drawY2
	 *            The bottom y position to draw to
	 * @param srcX
	 *            The left source x position to draw from
	 * @param srcY
	 *            The top source y position to draw from
	 * @param srcX2
	 *            The right source x position to draw from
	 * @param srcY2
	 *            The bottom source y position to draw from
	 */
	private void drawQuad(float drawX, float drawY, float drawX2, float drawY2,
			float srcX, float srcY, float srcX2, float srcY2) {
		float DrawWidth = drawX2 - drawX;
		float DrawHeight = drawY2 - drawY;
		float TextureSrcX = srcX / textureWidth;
		float TextureSrcY = srcY / textureHeight;
		float SrcWidth = srcX2 - srcX;
		float SrcHeight = srcY2 - srcY;
		float RenderWidth = (SrcWidth / textureWidth);
		float RenderHeight = (SrcHeight / textureHeight);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		fontTexture.bind();
		
		shader.setUniform("model_color", new Vector4f(0,0,0,1));
		shader.setUniform("model_transform", textTransform.getTansformation().mul(charTransform.getTansformation()));
		shader.setUniformi("model_hasTexture", 1);
		
		glBindBuffer(GL_ARRAY_BUFFER, quadVBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0); // positions
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 12); // texCoords
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadIBO);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		/*
		GL.glTexCoord2f(TextureSrcX, TextureSrcY);
		GL.glVertex2f(drawX, drawY);
		GL.glTexCoord2f(TextureSrcX, TextureSrcY + RenderHeight);
		GL.glVertex2f(drawX, drawY + DrawHeight);
		GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
		GL.glVertex2f(drawX + DrawWidth, drawY + DrawHeight);
		GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY);
		GL.glVertex2f(drawX + DrawWidth, drawY);
		*/
	}

	/**
	 * Get the width of a given String
	 * 
	 * @param whatchars
	 *            The characters to get the width of
	 * 
	 * @return The width of the characters
	 */
	public int getWidth(String whatchars) {
		int totalwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;
		for (int i = 0; i < whatchars.length(); i++) {
			currentChar = whatchars.charAt(i);
			if (currentChar < 256) {
				intObject = charArray[currentChar];
			} else {
				intObject = (IntObject)customChars.get( new Character( (char) currentChar ) );
			}
			
			if( intObject != null )
				totalwidth += intObject.width;
		}
		return totalwidth;
	}

	/**
	 * Get the font's height
	 * 
	 * @return The height of the font
	 */
	public int getHeight() {
		return fontHeight;
	}

	/**
	 * Get the height of a String
	 * 
	 * @return The height of a given string
	 */
	public int getHeight(String HeightString) {
		return fontHeight;
	}

	/**
	 * Get the font's line height
	 * 
	 * @return The line height of the font
	 */
	public int getLineHeight() {
		return fontHeight;
	}

	/**
	 * Draw a string
	 * 
	 * @param x
	 *            The x position to draw the string
	 * @param y
	 *            The y position to draw the string
	 * @param whatchars
	 *            The string to draw
	 * @param color
	 *            The color to draw the text
	 */
	public void drawString(float x, float y, String whatchars, org.newdawn.slick.Color color) {
		//drawString(x,y,whatchars,color,0,whatchars.length()-1);
	}
	
	
	public void drawString(float x, float y, String whatchars, Vector3f color) {
		fontTexture.bind();
		//IntObject intObject = 
	}
	
	/**
	 * @see Font#drawString(float, float, String, org.newdawn.slick.Color, int, int)
	 */
	public void drawString(Transform transform, String whatchars, int startIndex, int endIndex) {
		fontTexture.bind();
		Transform charTransform = new Transform();
		IntObject intObject = null;
		int charCurrent;

		int totalwidth = 0;
		
		
		
		
		for (int i = 0; i < whatchars.length(); i++) {
			charCurrent = whatchars.charAt(i);
			if (charCurrent < 256) {
				intObject = charArray[charCurrent];
			} else {
				intObject = (IntObject)customChars.get( new Character( (char) charCurrent ) );
			} 
			
			if( intObject != null ) {
				if ((i >= startIndex) || (i <= endIndex)) {
					charTransform.setScale(x, y, z);
					drawQuad(intObject.width, intObject.height);
					drawQuad((x + totalwidth), y,
							(x + totalwidth + intObject.width),
							(y + intObject.height), intObject.storedX,
							intObject.storedY, intObject.storedX + intObject.width,
							intObject.storedY + intObject.height);
				}
				transform.moveBy(intObject.width, 0, 0);
				
			}
		}
		
	}

	/**
	 * Draw a string
	 * 
	 * @param x
	 *            The x position to draw the string
	 * @param y
	 *            The y position to draw the string
	 * @param whatchars
	 *            The string to draw
	 */
	public void drawString(float x, float y, String whatchars) {
		drawString(x, y, whatchars, org.newdawn.slick.Color.white);
	}

	public float getSize() {
		return this.font.getSize();
	}

	public String getName() {
		return name;
	}
	
	public int getStyle() {
		return style;
	}

	public Font getFont() {
		return font;
	}

	public boolean equals(String fontName, int style2, int size2) {
		return name.equals(fontName) && style2 == style && size2 == size;
	}
	
}
