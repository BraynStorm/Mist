package mist.client.engine.render.gui.shapes;

import java.awt.Font;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import mist.client.engine.Mist;
import mist.client.engine.render.Common;
import mist.client.engine.render.IDrawable;
import mist.client.engine.render.core.Shader;
import mist.client.engine.render.core.Texture;
import mist.client.engine.render.core.TransformGUI;
import mist.client.engine.render.core.Vector2f;
import mist.client.engine.render.core.Vector4f;
import mist.client.engine.render.core.fonts.FontLibrary;
import mist.client.engine.render.core.fonts.TrueTypeFont;
import mist.client.engine.render.loaders.TextureLoader;

public class RoundedRectangle implements IDrawable {
	Rectangle corner;
	Rectangle stripe;
	Rectangle filling;
	
	TransformGUI fillingTransform;
	
	TransformGUI vStripeTransformL;
	TransformGUI vStripeTransformR;
	
	TransformGUI hStripeTransformT;
	TransformGUI hStripeTransformB;
	
	TransformGUI cornerTransformTL;
	TransformGUI cornerTransformTR;
	TransformGUI cornerTransformBL;
	TransformGUI cornerTransformBR;
	
	TransformGUI buttonFontTransform;
	TrueTypeFont buttonFont;
	
	Vector2f animation0 = new Vector2f(0,0); 
	Vector2f innerAnimationTL = new Vector2f(0,0);
	
	Shader guiShader;
	Shader guiButtonShader;
	
	int fillingVBO;
	
	String text;
	Vector4f color;
	
	boolean animationSwitch = false;
	
	/**
	 * 
	 * @param width of the outer
	 * @param height of the outer
	 * @param mainTransform Moddle Point of the whole button
	 */
	private void initTransforms(float width, float height, TransformGUI mainTransform){
		
		float w2 = width/2.f;
		float h2 = height/2.f;
		
		float w4 = w2/2.f;
		float h4 = h2/2.f;
		
		//Copy the original transform
		
		vStripeTransformL = mainTransform.clone();
		vStripeTransformL.moveOnScreen(-w2, 0);
		vStripeTransformL.setRotation(0, 0, 0);
		
		vStripeTransformR = mainTransform.clone();
		vStripeTransformR.moveOnScreen(w2, 0);
		vStripeTransformR.setRotation(0, 0, 90);
		
		hStripeTransformT = mainTransform.clone();
		hStripeTransformT.moveOnScreen(0, 300);
		hStripeTransformT.setRotation(0, 0, 0);
		
		hStripeTransformB = mainTransform.clone();
		hStripeTransformB.moveOnScreen(0, h2);
		hStripeTransformB.setRotation(0, 0, 90);
		
		
		cornerTransformTL = mainTransform.clone();
		cornerTransformTL.setRotation(0, 0, 0);
		//cornerTransformTL.setScreenTranslation(0, 0);
		cornerTransformTL.moveOnScreen(-w4, -h4);
		
		cornerTransformTR = mainTransform.clone();
		cornerTransformTR.setRotation(0, 0, -90);
		cornerTransformTR.moveOnScreen(w4, -h4);
		
		cornerTransformBR = mainTransform.clone();
		cornerTransformBR.setRotation(0, 0, 180);
		cornerTransformBR.moveOnScreen(w4, h4);
		
		cornerTransformBL = mainTransform.clone();
		cornerTransformBL.setRotation(0, 0, 90);
		cornerTransformBL.moveOnScreen(-w4, h4);
		
	}
	
	public RoundedRectangle(Shader guiShader, Shader guiButtonShader, int width, int height, TransformGUI transform, String text){
		
		this.guiShader = guiShader;
		this.guiButtonShader = guiButtonShader;
		
		corner = new Rectangle(guiShader, 64, 64, TextureLoader.getTexture("button_corner"));
		stripe = new Rectangle(guiShader, 64, 1, TextureLoader.getTexture("button_stripe"));
		fillingTransform = transform;
		filling = new Rectangle(guiButtonShader, width, height, fillingTransform, TextureLoader.getTexture("button_background"));
		
		buttonFont = FontLibrary.requestFontWithSize(guiShader, "Calibri", Font.TRUETYPE_FONT, 12);
		buttonFontTransform = transform.clone();
		buttonFontTransform.moveOnScreen(-buttonFont.getWidth(text)/2.0f, 0);
		
		initTransforms(width, height, transform);
		
		color = new Vector4f(0, 0, 0, 1);
		this.text = text;
		
		float w = (float) width / Mist.getInstance().getWindow().getWidth();
		float h = (float) height / Mist.getInstance().getWindow().getHeight();
		System.out.println("Filling WH" + w + ", " + h);
		

		//fillingVBO = Common.createRectangleVBO(width, height, 0, 0.5f, 1);
	}

	@Override
	public void setScale(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslation(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRotation(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
	//	System.out.println(fillingTransform.toString());
		guiShader.bind();
		corner.setTransform(cornerTransformTL);
		corner.render();
		corner.setTransform(cornerTransformTR);
		corner.render();
		corner.setTransform(cornerTransformBR);
		corner.render();
		corner.setTransform(cornerTransformBL);
		corner.render();
		
		//stripe.setTransform(hStripeTransformT);
		//stripe.render();
		//stripe.setTransform(hStripeTransformB);
		//stripe.render();
		//stripe.setTransform(vStripeTransformL);
		//stripe.render();
		//stripe.setTransform(vStripeTransformR);
		//stripe.render();
		
		//	Filling:
		
		if(animationSwitch){
			innerAnimationTL.x += 0.0001f;
			if(innerAnimationTL.x >= 0.1f)
				animationSwitch = !animationSwitch;
		}else{
			innerAnimationTL.x -= 0.0001f;
			if(innerAnimationTL.x <= 0.0f)
				animationSwitch = !animationSwitch;
		}
		
		
		guiButtonShader.bind();
		guiButtonShader.setUniform("innerAnimationTL", innerAnimationTL);
		filling.render();
		/*guiButtonShader.setUniform("model_transform", fillingTransform.getTransformation());
		animatedTexture.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, fillingVBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0); // positions
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 12); // texCoords
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, Common.RECTANGLE_IBO);
		glDrawElements(GL_TRIANGLES, Common.RECTANGLE_DRAWCOUNT, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);*/
		
		//	End-Filling
		
		guiShader.bind();
		buttonFont.drawString(buttonFontTransform, text, color);
		
	}
}

