package mist.client.engine.render.loaders;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import org.lwjgl.BufferUtils;

import mist.client.engine.Mist;
import mist.client.engine.render.core.Face;
import mist.client.engine.render.core.Model;
import mist.client.engine.render.core.ModelTemplate;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Vector2f;
import mist.client.engine.render.core.Vector3f;
import mist.client.engine.render.core.Vertex;

public class ModelLoader {
	
	private static String modelsPath = Mist.dataFolder + "/models/";
	
	private static HashMap<String, ModelTemplate> models = new HashMap<String, ModelTemplate>();
	
	public static void loadModel(String name){
		String filename = name + ".obj";
		System.out.println(modelsPath + filename);
		
		if(models.containsKey(name)){
			System.out.println("[WARNING] Model with name " + name + " has already been loaded. Skipping!");
			return;
		}
		
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Face> faces = new ArrayList<Face>();
		
		String materialName = null;
		
		try{
			Scanner s = new Scanner(new File(modelsPath + filename));
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				String[] parts = line.split("  *");
				
				switch(parts[0]){
					case "#":
						continue;
					case "o":
						continue;
					case "mtllib":
						materialName = MaterialLoader.loadMaterial(parts[1]);
						break;
					case "v":
						positions.add(
							new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
							)
						);
						break;
					case "vt":
						texCoords.add(
							new Vector2f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2])
							)
						);
						break;
					case "f":
						IntBuffer indices = BufferUtils.createIntBuffer(3);
						
						for(int i = 1; i < parts.length; i++){
							String[] p = parts[i].split("/");
							
							int pos = Integer.parseInt(p[0]) - 1;
							int tex = 0;
							
							//Texture
							if(p.length > 1 && p[1].length() > 0){
								tex = Integer.parseInt(p[1]) - 1;
							}
							
							Vector3f posVector = positions.get(pos);
							Vector2f texVector = tex != 0 ? texCoords.get(tex) : new Vector2f(0, 0);
							
							Vertex potentialVertex = new Vertex(posVector, texVector);
							boolean found = false;
							
							for(int j = 0; j < vertices.size(); j++){
								if(vertices.get(j).equals(potentialVertex)){
									indices.put(j);
									found = true;
									break;
								}
							}
							
							if(!found){
								indices.put(vertices.size());
								vertices.add(potentialVertex);
							}
						}
						
						indices.flip();
						
						faces.add(new Face (indices));
						
						break;
				}
			}
			
			ModelTemplate m = new ModelTemplate(vertices, faces);
			if(materialName != null){
				m.setMaterial(MaterialLoader.getMaterial(materialName));
			}
			models.put(name, m);
			
			
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static Model getNewModel(String name, Shader shader){
		return models.get(name).newModel(shader);
	}

	public static void destroy() {
		for(Entry<String, ModelTemplate> e : models.entrySet()){
			e.getValue().destroy();
		}
		
	}
}
