package com.example.android.opengl;

import com.airHockey.android.objects.Table;
import com.airHockey.android.programs.TextureShaderProgram;
import com.airHockey.android.util.TextureHelper;

public class GLBitmap {
	private TextureShaderProgram mTextureProgram;
	private int mTexture;
	private Table mTable;
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
		mTable = new Table();
		mTextureProgram.useProgram();
		mTextureProgram.setUniforms(matrix, mTexture);
		mTable.bindData(mTextureProgram);
		mTable.draw();
	}

}
