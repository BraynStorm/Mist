package mist.client.engine.render.gui;
import mist.client.engine.render.Drawable;
import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;

public abstract class GuiBaseElement extends Drawable {
	
	
	public GuiBaseElement(Shader shader, int vbo, int ibo, int drawCount,
			Material material) {
		super(shader, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}
	
	public GuiBaseElement(Shader shader, Transform transform, int vbo, int ibo,
			int drawCount, Material material) {
		super(shader, transform, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void renderForeground();
	public abstract void renderBackground();
}
