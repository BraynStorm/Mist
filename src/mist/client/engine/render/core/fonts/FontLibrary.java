package mist.client.engine.render.core.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import mist.client.engine.Mist;
import mist.client.engine.render.core.Shader;

public class FontLibrary {
	
	public static FontRenderContext frc = new FontRenderContext(null, true, false);
	public static HashMap<String, TrueTypeFont> fontLibrary = new HashMap<String, TrueTypeFont>();
	
	public static TrueTypeFont requestFontWithSize(Shader shader, String fontName, int style, int size){
		for(Entry<String, TrueTypeFont> entry : fontLibrary.entrySet())
			if(entry.getValue().equals(fontName, style, size))
				return entry.getValue();
		
		//Otherwise...
		
		try {
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(Mist.dataFolder + "/fonts/" + fontName + ".ttf"));
			awtFont = awtFont.deriveFont(style, size);
			TrueTypeFont ttf = new TrueTypeFont(shader, awtFont, fontName, true);
			fontLibrary.put(fontName, ttf);
			return ttf;
		} catch (FontFormatException | IOException e) {
			System.out.println("[ERROR] Font " + fontName + " not found.");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
