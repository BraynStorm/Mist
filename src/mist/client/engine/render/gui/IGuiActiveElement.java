package mist.client.engine.render.gui;

import mist.client.engine.event.KeyEvent;
import mist.client.engine.event.MouseEvent;

public interface IGuiActiveElement extends IGuiElement{
	public void mouseEvent(MouseEvent event);
	public void keyEvent(KeyEvent event);
}
