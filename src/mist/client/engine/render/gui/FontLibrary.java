package mist.client.engine.render.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.TrueTypeFont;

@Deprecated
public class FontLibrary {
	
	private Font defaultFont;
	private int defaultSize = 12;
	
	private HashMap<String, Font> fonts;
	
	public FontLibrary (String defaultFontName, int defaultSize, File defaultFont){
		
		fonts = new HashMap<String, Font>();
		loadFont(defaultFontName, defaultFont);
		
		this.defaultSize = defaultSize;
		this.defaultFont = fonts.get(defaultFontName);
	}
	
	public TrueTypeFont getFont(String name, boolean antialias){
		return getSizedFont(name, defaultSize, antialias);
	}
	
	public TrueTypeFont getSizedFont(String name, int size, boolean antialias){
		return new TrueTypeFont(fonts.get(name).deriveFont(size), antialias);
	}
	
	public TrueTypeFont getDefaultFont(boolean antialias){
		return getSizedDefaultFont(defaultSize, antialias);
	}
	
	public TrueTypeFont getSizedDefaultFont(int size, boolean antialias){
		return new TrueTypeFont(defaultFont.deriveFont(size), antialias);
	}
	
	public void setDefaultFont(String name, File font){
		loadFont(name, font);
		defaultFont = fonts.get(name);
	}
	
	public void loadFont(String name, File font){
		if(!fonts.containsKey(name)){
			Font f;
			try {
				f = Font.createFont(Font.TRUETYPE_FONT, font);
				
				if(!fonts.containsValue(f))
					fonts.put(name, f);
				
			} catch (IOException | FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("WARNING: A font with name " + name + " has already been loaded! SKIPPING");
		}
	}
}
