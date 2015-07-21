package mist.client.engine.render.gui.objects;

import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.gui.shapes.Rectangle;

public class GUIBar extends Rectangle{
	
	public boolean isVertical = false;
	public float progress = 1; // 0...1
	
	public GUIBar(Shader shader, int width, int height, Texture texture) {
		super(shader, width, height, texture);
	}
	
}
