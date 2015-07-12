package mist.client.engine.render.gui.shapes;

import mist.client.engine.render.gui.GuiBaseElement;


public class Rectangle extends GuiBaseElement{
	
	
	private int textureID;
	
	
	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setVertexColor(int id, int r, int g, int b){
		
	}
	
	public void setTextureID(int id){
		
	}
	
	public boolean hasTexture(){
		return textureID != 0;
	}
	
	@Override
	public void renderForeground() {}

	@Override
	public void renderBackground() {
		
		if(hasTexture()){
			
		}
	}

}
