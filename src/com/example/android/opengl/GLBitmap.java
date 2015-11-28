package com.example.android.opengl;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

import com.airHockey.android.programs.TextureShaderProgram;
import com.airHockey.android.util.TextureHelper;

public class GLBitmap {
//	private static final float[] VERTEX_DATA = {
//			// Order of coordinates: X, Y, S, T
//			
//			// Triangle Fan
//			0.0f,		0.0f,		0.5f,		0.5f,
//			-.5f,		-.8f,		0.0f,		1f,
//			0.5f,		-.8f,		1.0f,		1f,
//			0.5f,		0.8f,		1.0f,		0f,
//			-.5f,		0.8f,		0.0f,		0f,
//			-.5f,		-.8f,		0.0f,		1f
//	};
	private static final float[] VERTEX_DATA = {
			// Order of coordinates: X, Y, S, T
			
			// Triangle Fan
			50f,		50f,		0.5f,		0.5f,
			0f,		100f,		0.0f,		1f,
			100f,		100f,		1.0f,		1f,
			100f,		0f,		1.0f,		0f,
			0f,		0f,		0.0f,		0f,
			0f,		100f,		0.0f,		1f
	};
	private TextureShaderProgram mTextureProgram;
	private int mTexture;
	public GLBitmap() {
		mTextureProgram = new TextureShaderProgram(OpenGLES20Activity.mInstance);
		mTexture = TextureHelper.loadTexture(OpenGLES20Activity.mInstance, R.drawable.ic_launcher);
	}

	/**
	 * Encapsulates the OpenGL ES instructions for drawing this shape.
	 *
	 * @param mvpMatrix
	 *            - The Model View Project matrix in which to draw this shape.
	 */
	public void draw(float[] matrix) {
		mTextureProgram.useProgram();
		mTextureProgram.setUniforms(matrix, mTexture);
		mTextureProgram.setAttribs(VERTEX_DATA);
		
		
//		mTable.bindData(mTextureProgram);
//		mTable.draw();
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
	}

}
