package mist.client.engine.render.core;

public class Vector3f {

	public float x,y,z;
	
	public Vector3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float[] getData(){
		return new float[] { x, y, z };
	}
	
	/* MATH */
	
	public float length(){
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public float dot(Vector3f v){
		return x * v.x + y * v.y + z * v.z;
	}
	
	public Vector3f getNormalized(){
		Vector3f v = new Vector3f(x, y, z);
		return v.normalize();
	}
	
	public Vector3f normalize(){
		float len = length();
		
		x /= len;
		y /= len;
		z /= len;
		
		return this;
	}
	
	public Vector3f rotate(float angle, Vector3f axis){
		
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle/2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle/2));
		
		float rX = axis.x * sinHalfAngle;
		float rY = axis.y * sinHalfAngle;
		float rZ = axis.z * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rot = new Quaternion(rX, rY, rZ, rW);
		Quaternion w = rot.mul(this).mul(rot.conjugate());
		
		x = w.x;
		y = w.y;
		z = w.z;
		
		return this;
	}
	
	public Vector3f cross(Vector3f v){
		float x_ = y * v.z - z * v.y;
		float y_ = z * v.x - x * v.z;
		float z_ = x * v.y - y * v.x;
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f add(Vector3f v){
		return new Vector3f(v.x + x, v.y + y, v.z + z);
	}
	
	public Vector3f add(float v){
		return new Vector3f(v + x, v + y, v + z);
	}
	
	public Vector3f sub(Vector3f v){
		return new Vector3f(v.x - x, v.y - y, v.z - z);
	}
	
	public Vector3f sub(float v){
		return new Vector3f(v - x, v - y, v - z);
	}
	
	public Vector3f mul(Vector3f v){
		return new Vector3f(v.x * x, v.y * y, v.z * z);
	}
	
	public Vector3f mul(float v){
		return new Vector3f(v * x, v * y, v * z);
	}
	
	public Vector3f div(Vector3f v){
		return new Vector3f(v.x / x, v.y / y, v.z / z);
	}
	
	public Vector3f div(float v){
		return new Vector3f(v / x, v / y, v / z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3f other = (Vector3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}
	
	public Vector3f invert(){
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public Vector3f getInverted() {
		return new Vector3f(-x, -y, -z);
	}

}

