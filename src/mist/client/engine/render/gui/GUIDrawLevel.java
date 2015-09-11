package mist.client.engine.render.gui;

public class GUIDrawLevel {
	private float zValue;
	
	private GUIDrawLevel(float zValue){ this.zValue = zValue; }
	
	public float getZValue() {
		return zValue;
	}
	
	public static final GUIDrawLevel TOPMOST_LEVEL 	= new GUIDrawLevel(0.00f);
	public static final GUIDrawLevel TOP_LEVEL 		= new GUIDrawLevel(0.01f);
	public static final GUIDrawLevel MIDDLE_LEVEL 	= new GUIDrawLevel(0.02f);
	public static final GUIDrawLevel LOW_LEVEL 		= new GUIDrawLevel(0.03f);
	public static final GUIDrawLevel LOWMOST_LEVEL 	= new GUIDrawLevel(0.04F);

	
	
}
