package mist.client_old.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class MaterialManager {
	
	public static HashMap<String, Material> list = new HashMap<String,Material>();
	
	public static TextureLoader textureLoader = new TextureLoader();
	
	public static void loadMaterial(Path objFile) throws Exception{
		
		String filename = objFile.getFileName().toString().replace(".obj", ".mtl");
		
		if(!filename.endsWith(".mtl"))
			throw new IOException("Material must be an .mtl file.");
		
		if(list.containsKey(filename))
			throw new Exception("Material has already been loaded.");
		
		Scanner s = new Scanner(objFile);
		String line;
		
		Material m = new Material(filename.substring(0, filename.length() - 4));
		
		while (s.hasNextLine()){
			line = s.nextLine();
			
			//Texture Name
			if(line.startsWith("map_Kd")){
				String path = line.substring(6).trim();
				TextureBank.list.put(m.name, textureLoader.getTexture(path));
			}
		}
		
		list.put(m.name, m);
		
		s.close();
	}
	
}
