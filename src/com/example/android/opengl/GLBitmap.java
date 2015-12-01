package com.example.android.opengl;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

import com.airHockey.android.programs.TextureShaderProgram;
import com.airHockey.android.util.TextureHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GLBitmap {
	// private static final float[] VERTEX_DATA = {
	// // Order of coordinates: X, Y, S, T
	//
	// // Triangle Fan
	// 0.0f, 0.0f, 0.5f, 0.5f,
	// -.5f, -.8f, 0.0f, 1f,
	// 0.5f, -.8f, 1.0f, 1f,
	// 0.5f, 0.8f, 1.0f, 0f,
	// -.5f, 0.8f, 0.0f, 0f,
	// -.5f, -.8f, 0.0f, 1f
	// };
	private static final float[] VERTEX_DATA = {
			// Order of coordinates: X, Y, S, T

			// Triangle Fan
			0f, 0f, 0.5f, 0.5f, 0f, 0f, 0.0f, 1f, 0f, 0f, 1.0f, 1f, 0f, 0f, 1.0f, 0f, 0f, 0f, 0.0f, 0f, 0f, 0f, 0.0f,
			1f };
	private TextureShaderProgram mTextureProgram;
	private int mTexture;
	private Context mContext;
	private Bitmap mBitmap;

	public GLBitmap(Context context) {
		mContext = context;
		mTextureProgram = new TextureShaderProgram(context);
		// mTexture = TextureHelper.loadTexture(context,
		// R.drawable.ic_launcher);
	}

	public void setBitmap(int resourceId) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
		mTexture = TextureHelper.loadTexture(mContext, mBitmap);
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap=bitmap;
		mTexture = TextureHelper.loadTexture(mContext, bitmap);
	}
	public void setTexture(int id)
	{
		mTexture=id;
	}
	public void setPosition(int left, int top, int right, int bottom) {
		VERTEX_DATA[0] = (left + right) / 2f;
		VERTEX_DATA[1] = (top + bottom) / 2f;
		VERTEX_DATA[4] = left;
		VERTEX_DATA[5] = bottom;

		VERTEX_DATA[8] = right;
		VERTEX_DATA[9] = bottom;

		VERTEX_DATA[12] = right;
		VERTEX_DATA[13] = top;

		VERTEX_DATA[16] = left;
		VERTEX_DATA[17] = top;

		VERTEX_DATA[20] = left;
		VERTEX_DATA[21] = bottom;
	}

	public void setPosition(int left, int top) {
		 setPosition(left, top, left+mBitmap.getWidth(), top+mBitmap.getHeight());
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

		// mTable.bindData(mTextureProgram);
		// mTable.draw();
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
	}

}
