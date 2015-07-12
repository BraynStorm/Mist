package mist.client.engine.render.gui;

public abstract class GuiBaseElement {
	public int width;
	public int height;
	public int x;
	public int y;
	
	public abstract void renderForeground();
	public abstract void renderBackground();
}
