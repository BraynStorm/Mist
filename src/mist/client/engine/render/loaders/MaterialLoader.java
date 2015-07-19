package mist.client.engine.render.loaders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;

import mist.client.engine.Mist;
import mist.client.engine.render.core.Material;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.Vector3f;

public class MaterialLoader {

	public static String modelsFolder = Mist.dataFolder + "/models/";
	
	private static HashMap<String, Material> materials = new HashMap<String, Material>();
	
	public static String loadMaterial(String filename){
		String name = "";
		try {
			Scanner s = new Scanner(new FileInputStream(modelsFolder + filename));
			StringBuilder data = new StringBuilder();
			
			Material m = new Material();
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				String parts[] = line.split("  *");
				
				switch(parts[0]){
					case "newmtl":
						name = parts[1];
						break;
					case "kd":
						m.setKd(new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
						));
						break;
					case "ka":
						m.setKa(new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
						));
						break;
					case "ks":
						m.setKs(new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
						));
						break;
					case "ke":
						m.setKe(new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
						));
						break;
					case "d":
						m.setD(Float.parseFloat(parts[1]));
						break;
					case "Ns":
						m.setNs(Float.parseFloat(parts[1]));
						break;
					case "illum":
						m.setIllum(Float.parseFloat(parts[1]));
						break;
					case "map_kd":
						String texName = parts[1].split("\\.")[0]; // TODO: learn effing regex
						System.out.println("TEXTURE: " + texName);
						Texture texture = TextureLoader.loadTexture(texName, false, GL11.GL_LINEAR);
						
						m.setTextureID(texture.textureID);
						break;
				}
				
				
				
			}
			s.close();
			System.out.println("MATERIAL " + name);
			materials.put(name, m);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return name;
	}

	public static Material getMaterial(String materialName) {
		return materials.get(materialName);
	}
}
// List of models and procedural loading...