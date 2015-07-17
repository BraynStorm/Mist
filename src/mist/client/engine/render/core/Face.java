package mist.client.engine.render.core;

import java.nio.IntBuffer;
import java.util.ArrayList;

import mist.client.engine.render.utils.BSUtils;

public class Face {
	
	public IntBuffer vertexIndices;
	
	public Face(IntBuffer vertexIndices) {
		this.vertexIndices = vertexIndices;
	}
	
	/** Create buffers for vertexIndices from a list of faces.
	 * @param faces The faces that describe the mesh/model
	 * @return IntBuffer vertexIndices;
	 */
	public static IntBuffer meshify(ArrayList<Face> faces){
		
		ArrayList<IntBuffer> buffer = new ArrayList<IntBuffer>();
		
		for(Face f : faces){
			buffer.add(f.vertexIndices);
		}
		
		return BSUtils.combineBuffers(buffer, 3);
	}
}
