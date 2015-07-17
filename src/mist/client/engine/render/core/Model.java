package mist.client.engine.render.core;

import mist.client.engine.render.Drawable;

public class Model extends Drawable implements Cloneable {

	public Model(Shader shader, int vbo, int ibo, int drawCount, Material material) {
		super(shader, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}

	public Model(Shader shader, Transform transform, int vbo, int ibo, int drawCount, Material material) {
		super(shader, transform, vbo, ibo, drawCount, material);
		// TODO Auto-generated constructor stub
	}
	

}
