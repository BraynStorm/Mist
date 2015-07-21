package mist.client.engine.render.gui;

import mist.client.engine.event.KeyEvent;
import mist.client.engine.event.MouseEvent;
import mist.client.engine.render.Drawable;
import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;

public abstract class GuiBaseActiveElement extends Drawable {
	
	public GuiBaseActiveElement(Shader shader, int vbo, int ibo, int drawCount, Material material) {
		super(shader, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}
	
	public GuiBaseActiveElement(Shader shader, Transform transform, int vbo, int ibo, int drawCount, Material material) {
		super(shader, transform, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void mouseEvent(MouseEvent event);
	public abstract void keyEvent(KeyEvent event);
	
}
