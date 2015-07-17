package mist.client.engine.render.core;

public class Material {
	
	private int textureID;
	private float ns;
	private float ka;
	
	public Material(int textureID) {
		this.textureID = textureID;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public float getNs() {
		return ns;
	}

	public void setNs(float ns) {
		this.ns = ns;
	}

	public float getKa() {
		return ka;
	}

	public void setKa(float ka) {
		this.ka = ka;
	}
	
	

}
