package mist.client.engine.render.loaders;

import java.util.HashMap;

import mist.client.engine.Mist;
import mist.client.engine.render.core.Material;

public class MaterialLoader {

	public static String modelsFolder = Mist.dataFolder + "/models"; 
	private static HashMap<String, Material> materials = new HashMap<String, Material>();
	public static void loadMaterial(String name, String filename, boolean autoloadTextures){
		
	}

	public static Material getMaterial(String materialName) {
		return materials.get(materialName);
	}
}
// List of models and procedural loading...