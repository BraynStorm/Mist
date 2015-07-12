package mist.client_old.util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

@SuppressWarnings("unused")
public class GLUtils {
	
	final static double PI = 3.1415926535897932384626433832795;
	final static double PI_OVER_360 = PI / 360;
	
	public static void vertexBufferData(int id, FloatBuffer buffer) { //Not restricted to FloatBuffer
	    glBindBuffer(GL_ARRAY_BUFFER, id); //Bind buffer (also specifies type of buffer)
	    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW); //Send up the data and specify usage hint.
	}

	
	public static void glPerspective(float fovY, float aspect, float zNear, float zFar){
		double fW, fH;
		fH = Math.tan(fovY / 360 * PI) * zNear;
		fW = fH * aspect;
		glFrustum( -fW, fW, -fH, fH, zNear, zFar );
	}
}
