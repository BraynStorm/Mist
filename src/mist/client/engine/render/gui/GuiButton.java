package mist.client.engine.render.gui;

import mist.client.engine.event.KeyEvent;
import mist.client.engine.event.MouseEvent;
import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Transform;

public class GuiButton extends GuiBaseActiveElement {

	public GuiButton(Shader shader, int vbo, int ibo, int drawCount,
			Material material) {
		super(shader, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}

	public GuiButton(Shader shader, Transform transform, int vbo, int ibo,
			int drawCount, Material material) {
		super(shader, transform, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseEvent(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderForeground() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderBackground() {
		// TODO Auto-generated method stub
		
	}

}
