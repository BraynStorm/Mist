package mist.client.engine.render.core;

public class Bone {
	
	public static final Vector3f xAxis = new Vector3f(1,0,0);
	public static final Vector3f yAxis = Camera.Y_AXIS;
	public static final Vector3f zAxis = new Vector3f(0,0,1);
	
	public Vector3f head;
	public Vector3f tail;
	public float radius;
	public float roll;
	public float envelope;
	
	public Transform transform = new Transform();
	
	public boolean hasParent = true;
	public Bone parent;

	public Bone(Vector3f head, Vector3f tail, float radius, float roll, float envelope, Bone parent) {
		super();
		this.tail = tail;
		this.head = head;
		this.radius = radius;
		this.roll = roll;
		this.envelope = envelope;
		this.parent = parent;
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
	
	
	
}
