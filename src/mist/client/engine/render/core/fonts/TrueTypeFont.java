package mist.client.engine.render.core.fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import mist.client.engine.Mist;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.Transform;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.utils.BSUtils;

public class TrueTypeFont {
	
	private IntObject[] charArray = new IntObject[224];
	private int[] vboArray = new int[224];
	
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
	private static int quadIBO;
	
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
	
	private static boolean initialized = false;
	
	private static void initializeIBO(){
		if(initialized)
			return;
		
		IntBuffer buffer = BufferUtils.createIntBuffer(6);
		buffer.put(new int[]{
				0,1,2,
				1,2,3
		});
		
		buffer.flip();
		
		quadIBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadIBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		initialized = true;
	}
	
	public TrueTypeFont(Shader shader, Font font, String name, boolean antiAlias) {
		
		this.font = font;
		this.fontSize = font.getSize();
		this.antiAlias = antiAlias;
		this.name = name;
		this.shader = shader;
		
		initializeIBO();
		createSet();
	}
	
	public void setShader(Shader shader){
		this.shader = shader;
	}
	
	private BufferedImage getFontImage(char ch) {
		
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		
		if (antiAlias == true) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
		fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		
		if (antiAlias == true) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		gt.setFont(font);
		gt.setColor(Color.WHITE);
		gt.drawString(String.valueOf(ch), 0, fontMetrics.getAscent());
		
		return fontImage;
	}

	
	private void createSet() {
		
		BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) imgTemp.getGraphics();

		g.setColor(new Color(255,255,255,1));
		g.fillRect(0,0,textureWidth,textureHeight);
		
		int rowHeight = 0;
		int positionX = 0;
		int positionY = 0;
		
		for (int i = 0; i < 224; i++) {
			
			char ch = (char) (i + 32);
			
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
			
			//glyphData[ii] = new Vector4f(newIntObject.storedX, newIntObject.storedY, newIntObject.width, newIntObject.height);
			
			
			
			// Draw it here
			g.drawImage(fontImage, positionX, positionY, null);

			positionX += newIntObject.width;
			
			charArray[i] = newIntObject;
			fontImage = null;
		}
		
		for(int i = 0; i < 224; i++)
			glyphDataProcessing(i, imgTemp);
		
		File of = new File(Mist.dataFolder + "/image.png");
		try {
			ImageIO.write(imgTemp, "png", of);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fontTexture = BSUtils.textureFromBufferedImage(imgTemp);
	}
	
	
	private void glyphDataProcessing(int i, BufferedImage finalImage){
		IntObject intObject = charArray[i];
		
		float realW = intObject.width / finalImage.getWidth();
		float realH = intObject.height / finalImage.getHeight();
		
		//In tex coords the coordinates are correct. (Trust me, I'm your future self)
		float realX1 = intObject.storedX / finalImage.getWidth(); // Left
		float realY1 = intObject.storedY / finalImage.getHeight(); // Bottom
		float realX2 = realW + realX1; // Right
		float realY2 = realH + realY1; // Top
		
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4*5);
		buffer.put(0)			.put(0)		.put(1)		.put(realX1)		.put(realY2);
		buffer.put(intObject.width)		.put(0)		.put(1)		.put(realX2)		.put(realY2);
		buffer.put(intObject.width)		.put(intObject.height)	.put(1)		.put(realX2)		.put(realY1);
		buffer.put(0)			.put(intObject.height)	.put(1)		.put(realX1)		.put(realY1);
		buffer.flip();
		
		vboArray[i] = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboArray[i]);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void drawQuad(int index) {
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboArray[index]);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0); // positions
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 12); // texCoords
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadIBO);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public int getWidth(String whatchars) {
		int totalwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;
		for (int i = 0; i < whatchars.length(); i++) {
			currentChar = whatchars.charAt(i);
			
			intObject = charArray[currentChar+32];
			
			
			if( intObject != null )
				totalwidth += intObject.width;
		}
		return totalwidth;
	}
	
	public int getHeight() {
		return fontHeight;
	}
	
	public int getHeight(String HeightString) {
		return fontHeight;
	}
	
	public int getLineHeight() {
		return fontHeight;
	}
	
	/**
	 * 
	 * @param transform The transformation matrix (zRotOnly?) that applies to that whole text
	 * @param whatchars the string to render
	 * @param color a Vector4f (RGBA). Black if null
	 */
	public void drawString(Transform transform, String whatchars, Vector4f color) {
		fontTexture.bind();
		shader.setUniformi("model_hasTexture", 1);
		shader.setUniformi("model_isFont", 1);
		
		
		for (int i = 0; i < whatchars.length(); i++) {
			int charCurrent = whatchars.charAt(i)-32;
			
			if(charCurrent < 0){
				//Something has messed up. Horribly.
				new Exception().printStackTrace();
				return;
			}
			
			IntObject intObject = charArray[charCurrent];
			
			if( intObject != null ) {
				shader.setUniform("model_transform", transform.getTansformation());
				shader.setUniform("model_color", (color != null) ? color : new Vector4f(0,0,0,1));
				drawQuad(charCurrent);
				transform.moveBy(0.1f, 0, 0);
			}
		}
		shader.setUniformi("model_isFont", 0);
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
		return name.equals(fontName) && style2 == style && size2 == fontSize;
	}
	
}
