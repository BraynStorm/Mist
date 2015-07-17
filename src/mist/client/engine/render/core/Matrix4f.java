package mist.client.engine.render.core;

import mist.client.engine.Mist;
import mist.client.engine.render.utils.BSUtils;

public class Matrix4f {
	public float[][] m;
	
	public Matrix4f(){
		m = new float[4][4];
	}
	
	public Matrix4f mul(Matrix4f v){
		Matrix4f res = new Matrix4f();
		
		for(    int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				res.set(i, j, 	m[i][0] * v.get(0, j) +
								m[i][1] * v.get(1, j) +
								m[i][2] * v.get(2, j) +
								m[i][3] * v.get(3, j) );
			}
		}
		
		return res;
	}
	
	public void set(int x, int y, float v) {
		m[x][y] = v;
	}

	public float get(int x, int y) {
		return m[x][y];
	}

	public float[] getData() {
		float[] data = new float [16];
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				data[(i * 4) + j] = m[i][j];
			}
		}
		
		return data;
	}
	
	public Matrix4f identity(){
		
		m[0][0] = 1;				m[0][1] = 0;				m[0][2] = 0;				m[0][3] = 0;
		m[1][0] = 0;				m[1][1] = 1;				m[1][2] = 0;				m[1][3] = 0;
		m[2][0] = 0;				m[2][1] = 0;				m[2][2] = 1;				m[2][3] = 0;
		m[3][0] = 0;				m[3][1] = 0;				m[3][2] = 0;				m[3][3] = 1;
		
		return this;		
	}

	public Matrix4f translation(Vector3f v) {
		
		m[0][0] = 1;				m[0][1] = 0;				m[0][2] = 0;				m[0][3] = v.x;
		m[1][0] = 0;				m[1][1] = 1;				m[1][2] = 0;				m[1][3] = v.y;
		m[2][0] = 0;				m[2][1] = 0;				m[2][2] = 1;				m[2][3] = v.z;
		m[3][0] = 0;				m[3][1] = 0;				m[3][2] = 0;				m[3][3] = 1;
		
		return this;
	}
	public Matrix4f translation(float x, float y, float z){ return translation(new Vector3f(x, y, z)); }
	
	public Matrix4f scale(Vector3f v) {
		
		m[0][0] = v.x;				m[0][1] = 0;				m[0][2] = 0;				m[0][3] = 0;
		m[1][0] = 0;				m[1][1] = v.y;				m[1][2] = 0;				m[1][3] = 0;
		m[2][0] = 0;				m[2][1] = 0;				m[2][2] = v.z;				m[2][3] = 0;
		m[3][0] = 0;				m[3][1] = 0;				m[3][2] = 0;				m[3][3] = 1;
		
		return this;
	}
	public Matrix4f scale(float x, float y, float z){ return scale(new Vector3f(x, y, z)); }

	public Matrix4f rotate(Vector3f rot) {
		
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		BSUtils.radianizeVector3f(rot);
		
		/* ...Errmahghurrrd... */
		rz.m[0][0] =  (float)Math.cos(rot.z);		rz.m[0][1] = -(float)Math.sin(rot.z);		rz.m[0][2] = 0;								rz.m[0][3] = 0;
		rz.m[1][0] =  (float)Math.sin(rot.z);		rz.m[1][1] =  (float)Math.cos(rot.z);		rz.m[1][2] = 0;								rz.m[1][3] = 0;
		rz.m[2][0] = 0;								rz.m[2][1] = 0;								rz.m[2][2] = 1;								rz.m[2][3] = 0;
		rz.m[3][0] = 0;								rz.m[3][1] = 0;								rz.m[3][2] = 0;								rz.m[3][3] = 1;
		
		
		rx.m[0][0] = 1;								rx.m[0][1] = 0;								rx.m[0][2] = 0;								rx.m[0][3] = 0;
		rx.m[1][0] = 0;								rx.m[1][1] =  (float)Math.cos(rot.x);		rx.m[1][2] = -(float)Math.sin(rot.x);		rx.m[1][3] = 0;
		rx.m[2][0] = 0;								rx.m[2][1] =  (float)Math.sin(rot.x);		rx.m[2][2] =  (float)Math.cos(rot.x);		rx.m[2][3] = 0;
		rx.m[3][0] = 0;								rx.m[3][1] = 0;								rx.m[3][2] = 0;								rx.m[3][3] = 1;
		
		
		ry.m[0][0] =  (float)Math.cos(rot.y);		ry.m[0][1] = 0;								ry.m[0][2] = -(float)Math.sin(rot.y);		ry.m[0][3] = 0;
		ry.m[1][0] = 0;								ry.m[1][1] = 1;								ry.m[1][2] = 0;								ry.m[1][3] = 0;
		ry.m[2][0] =  (float)Math.sin(rot.y);		ry.m[2][1] = 0;								ry.m[2][2] =  (float)Math.cos(rot.y);		ry.m[2][3] = 0;
		ry.m[3][0] = 0;								ry.m[3][1] = 0;								ry.m[3][2] = 0;								ry.m[3][3] = 1;
		
		
		// rz * ry * rx;
		m = rz.mul(ry.mul(rx)).m;
		return this;
	}
	
	public Matrix4f rotate(float x, float y, float z) { return rotate(new Vector3f (x,y,z)); }
	
	public Matrix4f projection(String suffix) {
		
		float zNear = Mist.getInstance().getConfigValuef("zNear_" + suffix);
		float zFar = Mist.getInstance().getConfigValuef("zFar_" + suffix);
		float fov = Mist.getInstance().getConfigValuef("fov_" + suffix);
		float zRange = zNear - zFar;
		
		float aspect = (0.0f + Mist.getInstance().getWindow().getWidth()) / Mist.getInstance().getWindow().getHeight();
		System.out.println("projection " + aspect);
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
		
		m[0][0] = 1.0f / (aspect * tanHalfFOV);		m[0][1] = 0;					m[0][2] = 0;							m[0][3] = 0;
		m[1][0] = 0;								m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;							m[1][3] = 0;
		m[2][0] = 0;								m[2][1] = 0;					m[2][2] = (-zNear - zFar) / zRange;		m[2][3] = 2 * zFar * zNear / zRange;
		m[3][0] = 0;								m[3][1] = 0;					m[3][2] = 1;							m[3][3] = 0;
		
		return this;
	}
	
	public Matrix4f camera(Vector3f forward, Vector3f up){
		
		Vector3f f = forward.getNormalized();
		Vector3f r = up.getNormalized();
		r = r.cross(f);
		Vector3f u = f.cross(r);
		
		m[0][0] = r.x;				m[0][1] = r.y;				m[0][2] = r.z;				m[0][3] = 0;
		m[1][0] = u.x;				m[1][1] = u.y;				m[1][2] = u.z;				m[1][3] = 0;
		m[2][0] = f.x;				m[2][1] = f.y;				m[2][2] = f.z;				m[2][3] = 0;
		m[3][0] = 0;				m[3][1] = 0;				m[3][2] = 0;				m[3][3] = 1;
		
		return this;		
	}
}
