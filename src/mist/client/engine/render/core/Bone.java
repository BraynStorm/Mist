package mist.client.engine.render.core;

public class Bone {
	public Vector3f head;
	public Vector3f tail;
	public float radius;
	public float roll;
	public float envelope;
	
	public Transform transform = new Transform();
	
	public boolean hasParent = true;
	public Bone parrent;

	public Bone(Vector3f head, Vector3f tail, float radius, float roll, float envelope, Bone parrent) {
		super();
		this.tail = tail;
		this.head = head;
		this.radius = radius;
		this.roll = roll;
		this.envelope = envelope;
		this.parrent = parrent;
	}
	
	public Bone(Vector3f tail, Vector3f head, float radius, float roll, float envelope) {
		super();
		this.tail = tail;
		this.head = head;
		this.radius = radius;
		this.roll = roll;
		this.envelope = envelope;
		hasParent = false;
	}
	
	public Vector3f getCalculatedHeadPosition(){
		return hasParent ? head.add(parrent.getCalculatedHeadPosition()) : head;
	}
	
	public Vector3f getCalculatedTailPosition(){
		return hasParent ? tail.add(parrent.getCalculatedTailPosition()) : tail;
	}
	
	public Vector3f getCalculatedHeadRotation(){
		Vector3f res = null;
		
		if(hasParent){
			
		}
		
		return res;
	}
	
	public Vector3f getCalculatedTailRotation(){
		return hasParent ? tail.add(parrent.tail) : tail;
	}
	
	public Matrix4f getRotationForVertex(Vector3f vertexPosition){
		
		Vector3f calcHeadPosition = getCalculatedHeadPosition();
		Vector3f calcTailPosition = getCalculatedTailPosition();
		
		Vector3f calcHeadRotation = getCalculatedHeadRotation();
		Vector3f calcTailRotation = getCalculatedTailRotation();
		
		Matrix4f rotation = new Matrix4f().rotate(vertexPosition.mul(Vector3f.getDistance(calcTailPosition, vertexPosition)));
		Matrix4f translation = new Matrix4f().translation(calcHeadPosition.add(vertexPosition));
		
		return translation.mul(rotation);
	}
	
}
