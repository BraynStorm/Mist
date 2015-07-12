package mist.client_old.util;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Model extends Renderable {
	public ArrayList<TriangularFace> faces = new ArrayList<TriangularFace>();
	
	public void render(){
		glPushMatrix();
		
		glRotatef(rotX, 1, 0, 0);
		glRotatef(rotY, 0, 1, 0);
		glRotatef(rotZ, 0, 0, 1);
		
		glScalef(scaleX, scaleY, scaleZ);
		
		glTranslatef(x, y, z);
		
		for(TriangularFace f : faces){
			f.render();
		}
		
		
		glPopMatrix();
	}
}
