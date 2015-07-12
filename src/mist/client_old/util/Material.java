package mist.client_old.util;

public class Material {
	
	protected String name;
	protected String textureName;
	
	public Material(String name){
		this.name = name;
	}
	
	public void setTextureName(String texName){
		this.textureName = texName;
	}
	
	public Texture getTexture(){
		return TextureBank.list.get(textureName);
	}
	
	public boolean hasTextrue() {
		return textureName.length() == 0;
	}
	
}
