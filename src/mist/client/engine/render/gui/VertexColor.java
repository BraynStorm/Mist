package mist.client.engine.render.gui;

public class VertexColor {
	
	public int index;
	public int r,g,b,a;
	
	public static final int COLOR_MAX = 255;
	public static final int ALPHA_MAX = 255;
	
	/**
	 * @param index Range dependant on shape.
	 * @param r 0 - COLOR_MAX
	 * @param g 0 - COLOR_MAX
	 * @param b 0 - COLOR_MAX
	 * @param a 0 - ALPHA_MAX
	 */
	public VertexColor(int index, int r, int g, int b, int a) {
		this.index = index;
		this.r = Math.min(255, r);
		this.g = Math.min(255, g);;
		this.b = Math.min(255, b);;
		this.a = Math.min(255, a);;
	}
	
	

}
