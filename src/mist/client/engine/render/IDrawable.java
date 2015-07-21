package mist.client.engine.render;

public interface IDrawable {
	public void render();
	public void setScale(float x, float y, float z);
	public void setTranslation(float x, float y, float z);
	public void setRotation(float x, float y, float z);
}
