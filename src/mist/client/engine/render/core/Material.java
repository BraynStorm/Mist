package mist.client.engine.render.core;

public class Material {
	
	protected int textureID;
	protected Vector3f ka;
	protected Vector3f kd;
	protected Vector3f ks;
	protected Vector3f ke = new Vector3f (0,0,0);
	
	protected float d;
	protected float ns;
	protected float illum;
	
	public Material(int textureID) {
		this.textureID = textureID;
	}
	
	public Material() {}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public Vector3f getKa() {
		return ka;
	}

	public void setKa(Vector3f ka) {
		this.ka = ka;
	}

	public Vector3f getKd() {
		return kd;
	}

	public void setKd(Vector3f kd) {
		this.kd = kd;
	}

	public Vector3f getKs() {
		return ks;
	}

	public void setKs(Vector3f ks) {
		this.ks = ks;
	}

	public float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

	public float getNs() {
		return ns;
	}

	public void setNs(float ns) {
		this.ns = ns;
	}

	public float getIllum() {
		return illum;
	}

	public void setIllum(float illum) {
		this.illum = illum;
	}

	public Vector3f getKe() {
		return ke;
	}

	public void setKe(Vector3f ke) {
		this.ke = ke;
	}

}
