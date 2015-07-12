package mist.client_old.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ModelManager {
	public static HashMap<String, Model> models = new HashMap<String, Model>();
	
	
	
	public static boolean loadModel(String filename) throws Exception{
		
		float x, y, z;
		
		if(!filename.endsWith(".obj"))
			throw new IOException("Model must be an .obj file.");
		
		Path p = Paths.get(filename);
		filename = p.getFileName().toString();
		
		
		if(models.containsKey(filename))
			throw new Exception("Model has already been loaded.");
		
		
		
		Scanner s = new Scanner(p);
		
		String line = "";
		String [] parts;
		
		Model model = new Model();
		
		TriangularFace face;
		Vertex tempV;
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Vertex> normals = new ArrayList<Vertex>();
		ArrayList<Vertex> texCoords = new ArrayList<Vertex>();
		
		float colorV = 0;
		
		while (s.hasNextLine()){
			
			line = s.nextLine();
			parts = line.split(" ");
			
			if(parts[0].equals("newmtl")){
				
				MaterialManager.loadMaterial(p);
				
				continue;
			}
			
			if(parts[0].equals("#") || parts[0].equals("o"))
				continue;
			
			if (parts[0].equals("v")) {

				x = Float.parseFloat(parts[1]);
				y = Float.parseFloat(parts[2]);
				z = Float.parseFloat(parts[3]);

				vertices.add(new Vertex(x, y, z));
			}
			
			if (parts[0].equals("vt")) {
				x = Float.parseFloat(parts[1]);
				y = Float.parseFloat(parts[2]);

				texCoords.add(new Vertex(x, y, 0));
			}

			if (parts[0].equals("vn")) {
				x = Float.parseFloat(parts[1]);
				y = Float.parseFloat(parts[2]);
				z = Float.parseFloat(parts[3]);

				normals.add(new Vertex(x, y, z));
			}
			
			
			//Faces...
			if (parts[0].equals("f")) {
				
				face = new TriangularFace();
				
				String[][] ps = new String[3][3];
				ps[0] = parts[1].split("/");
				ps[1] = parts[2].split("/");
				ps[2] = parts[3].split("/");

				// Vertices
				int xv = Integer.parseInt(ps[0][0]) - 1;
				int yv = Integer.parseInt(ps[1][0]) - 1;
				int zv = Integer.parseInt(ps[2][0]) - 1;
				
				tempV = vertices.get(xv); // Coords for Vertex 1
				face.setVertex(0, tempV.x, tempV.y, tempV.z);
				tempV = vertices.get(yv); // Coords for Vertex 2
				face.setVertex(1, tempV.x, tempV.y, tempV.z);
				tempV = vertices.get(zv); // Coords for Vertex 3
				face.setVertex(2, tempV.x, tempV.y, tempV.z);
				
				
				// Normals
				if(ps[0][2] != null && !ps[0][2].equals("")){
					int xvn = Integer.parseInt(ps[0][2]) - 1;
					int yvn = Integer.parseInt(ps[1][2]) - 1;
					int zvn = Integer.parseInt(ps[2][2]) - 1;
					
					face.setUseNormal(true);
					
					tempV = normals.get(xvn); // Normal for Vertex 1
					face.setNormal(0, tempV.x, tempV.y, tempV.z);
					tempV = normals.get(yvn); // Normal for Vertex 2
					face.setNormal(1, tempV.x, tempV.y, tempV.z);
					tempV = normals.get(zvn); // Normal for Vertex 3
					face.setNormal(2, tempV.x, tempV.y, tempV.z);
				}
				
				// TexCoords
				if (ps[0][1] != null && !ps[0][1].equals("")) {
					int xvt = Integer.parseInt(ps[0][1]) - 1;
					int yvt = Integer.parseInt(ps[1][1]) - 1;
					int zvt = Integer.parseInt(ps[2][1]) - 1;
					
					face.setUseTexture(true);
					
					tempV = texCoords.get(xvt); // Normal for Vertex 1
					face.setTexCoord(0, tempV.x, tempV.y, tempV.z);
					tempV = texCoords.get(yvt); // Normal for Vertex 2
					face.setTexCoord(1, tempV.x, tempV.y, tempV.z);
					tempV = texCoords.get(zvt); // Normal for Vertex 3
					face.setTexCoord(2, tempV.x, tempV.y, tempV.z);
				}
				
				face.setColor(0, colorV, colorV, .0f);
				colorV += .1f;
				
				if(colorV > 1f)
					colorV = 0;
				
				model.faces.add(face);
			}
			
		}
		s.close();
		
		models.put(filename.substring(0, filename.length() - 4), model);
		
		return false;
	}
}
