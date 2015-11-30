package com.example.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.airHockey.android.programs.ColorShaderProgram;

import android.content.Context;
import android.opengl.GLES20;

public class Path {
	private final FloatBuffer vertexBuffer;
	private int mPositionHandle;
	ColorShaderProgram mColorShaderProgram;
	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 2;
	private ArrayList<Float> pathList = new ArrayList<Float>();
	// static float pathCoords[] = {
	// // in counterclockwise order:
	// 0.0f, 0.622008459f, // top
	// -0.5f, -0.311004243f, // bottom left
	// 0.5f, -0.311004243f // bottom right
	// };
	// private final int vertexCount = pathCoords.length / COORDS_PER_VERTEX;
	private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per
															// vertex

	float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

	/**
	 * Sets up the drawing object data for use in an OpenGL ES context.
	 */
	public Path(Context context) {
		mColorShaderProgram = new ColorShaderProgram(context);
		pathList.add(0f);
		pathList.add(0.622008459f);
		pathList.add(-0.5f);
		pathList.add(-0.5f);
		pathList.add(0.5f);
		pathList.add(-0.311004243f);

		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(
				// (number of coordinate values * 4 bytes per float)
				4 * 1500000);
		// use the device hardware's native byte order
		bb.order(ByteOrder.nativeOrder());

		// create a floating point buffer from the ByteBuffer
		vertexBuffer = bb.asFloatBuffer();
	}

	/**
	 * Encapsulates the OpenGL ES instructions for drawing this shape.
	 *
	 * @param mvpMatrix
	 *            - The Model View Project matrix in which to draw this shape.
	 */
	public void draw(float[] mvpMatrix) {
//		int[] fb = new int[1];
//		int[] rb = new int[1];
//		int[] bufferImg = new int[1];
//		GLES20.glGenFramebuffers(1, fb, 0);
//		System.out.println("ORZCanvas.CreateBuffer()framebuffer:" + fb[0]);
//		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fb[0]);
//		GLES20.glGenRenderbuffers(1, rb, 0);
//		System.out.println("ORZCanvas.CreateBuffer()renderbuffer:" + rb[0]);
//		GLES20.glBindFramebuffer(GLES20.GL_RENDERBUFFER, rb[0]);
//		GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT, MyGLSurfaceView.widthPixels,
//				MyGLSurfaceView.heightPixels);
//		GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER,
//				rb[0]);
//		GLES20.glGenTextures(1, bufferImg, 0);
//		System.out.println("ORZCanvas.CreateBuffer()bufferimg:" + bufferImg[0]);
//		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bufferImg[0]);
//		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA4, MyGLSurfaceView.widthPixels,
//				MyGLSurfaceView.heightPixels, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
//		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
//		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
//		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
//		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
//		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D,
//				bufferImg[0], 0);
//		int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
		
		
//		IntBuffer intBuffer;
//		GLES20.glGenBuffers(1, intBuffer);;
//		GLES20.glBindBuffer(target, buffer);
		
		// Add program to OpenGL environment
		// GLES20.glUseProgram(mProgram);
		vertexBuffer.clear();
		vertexBuffer.put(getFloatArray());
		// set the buffer to read the first coordinate
		vertexBuffer.position(0);

		mColorShaderProgram.useProgram();
		mColorShaderProgram.setUniforms(mvpMatrix, color);
		mColorShaderProgram.setAttribs(COORDS_PER_VERTEX, vertexStride, vertexBuffer);
		// GLES20.glFramebufferRenderbuffer(target, attachment,
		// renderbuffertarget, renderbuffer);
		// Draw the triangle
		GLES20.glLineWidth(4);
		GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, pathList.size() / COORDS_PER_VERTEX);

		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}

	private float[] getFloatArray() {
		float[] result = new float[pathList.size()];
		for (int i = 0; i < pathList.size(); i++) {
			result[i] = pathList.get(i);
		}
		return result;
	}

	public void moveTo(float x, float y) {
		 pathList.clear();
		pathList.add(x);
		pathList.add(y);
	}

	public void quadTo(float preX, float preY, float x, float y) {
		pathList.add(x);
		pathList.add(y);
	}
}
