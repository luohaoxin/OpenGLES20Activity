/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class must
 * override the OpenGL ES drawing lifecycle methods:
 * <ul>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

	private static final String TAG = "MyGLRenderer";
	private Square mSquare;
	private GLBitmap mBitmap;
	public static Path mPath;
	public static float aspectRatio=1;
	// mMVPMatrix is an abbreviation for "Model View Projection Matrix"
	private final float[] mMVPMatrix = new float[16];
	public static final float[] mProjectionMatrix = new float[16];
	private final float[] mViewMatrix = new float[16];
	private final float[] mRotationMatrix = new float[16];
	private final float[] mDanweiMatrix = new float[] { 1f, 0, 0, 0, 0, 1f, 0, 0, 0, 0, 1f, 0, 0, 0, 0, 1f };
	private float mAngle;
	private Context mContext;
	
	
	
	public MyGLRenderer(Context c) {
		mContext=c;
		
	}
	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {

		
		// Set the background frame color
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		mBitmap=new GLBitmap(mContext);
		mBitmap.setBitmap(R.drawable.ic_launcher);
		mSquare = new Square(mContext);
		mPath = new Path(mContext);
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		Log.i("luohaoxin", Thread.currentThread().toString());
		long start=System.currentTimeMillis();
		// Draw background color
		// GLES20.glViewport(x, y, width, height);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//		GLES20.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		// Set the camera position (View matrix)
		// Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3f, 0f, 0f, 0f, 0f, 1.0f,
		// 0f);
		//
		// // Calculate the projection and view transformation
		// Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix,
		// 0);
		mBitmap.setPosition(0,0,1000,1000);
		mBitmap.draw(mProjectionMatrix);
		// Draw square
		mSquare.draw(mProjectionMatrix);

		// Create a rotation for the triangle

		// Use the following code to generate constant rotation.
		// Leave this code out when using TouchEvents.
		// long time = SystemClock.uptimeMillis() % 4000L;
		// float angle = 0.090f * ((int) time);

		// Draw triangle
		mPath.draw(mProjectionMatrix);
		Log.i("luohaoxin", ""+(System.currentTimeMillis()-start));
		
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		// Adjust the viewport based on geometry changes,
		// such as screen rotation
		GLES20.glViewport(0, 0, width, height);

		// float ratio = (float) width / height;
		//
		// // this projection matrix is applied to object coordinates
		// // in the onDrawFrame() method为了搞成一个正方形的坐标系
		// Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

		// aspectRatio = width > height ? (float) width / (float) height :
		// (float) height / (float) width;
		//
		// if (width > height) {
		// // Landscape
		// Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f,
		// 1f, -1f, 1f);
		// } else {
		// // Portrait or square
		// Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio,
		// aspectRatio, -1f, 1f);
		// }
		aspectRatio = (float) height / (float) width;
		Matrix.orthoM(mProjectionMatrix, 0, 0f, width,  height,0, -1f, 1f);
//		Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
	}

	/**
	 * Utility method for compiling a OpenGL shader.
	 *
	 * <p>
	 * <strong>Note:</strong> When developing shaders, use the checkGlError()
	 * method to debug shader coding errors.
	 * </p>
	 *
	 * @param type
	 *            - Vertex or fragment shader type.
	 * @param shaderCode
	 *            - String containing the shader code.
	 * @return - Returns an id for the shader.
	 */
	public static int loadShader(int type, String shaderCode) {

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	/**
	 * Utility method for debugging OpenGL calls. Provide the name of the call
	 * just after making it:
	 *
	 * <pre>
	 * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
	 * MyGLRenderer.checkGlError("glGetUniformLocation");
	 * </pre>
	 *
	 * If the operation is not successful, the check throws an error.
	 *
	 * @param glOperation
	 *            - Name of the OpenGL call to check.
	 */
	public static void checkGlError(String glOperation) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e(TAG, glOperation + ": glError " + error);
			throw new RuntimeException(glOperation + ": glError " + error);
		}
	}

	/**
	 * Returns the rotation angle of the triangle shape (mTriangle).
	 *
	 * @return - A float representing the rotation angle.
	 */
	public float getAngle() {
		return mAngle;
	}

	/**
	 * Sets the rotation angle of the triangle shape (mTriangle).
	 */
	public void setAngle(float angle) {
		mAngle = angle;
	}

}