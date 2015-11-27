package com.airHockey.android.objects;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

import com.airHockey.android.data.VertexArray;
import com.airHockey.android.programs.TextureShaderProgram;

public class Table {

	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * VertexArray.BYTES_PER_FLOAT;
	
	private static final float[] VERTEX_DATA = {
			// Order of coordinates: X, Y, S, T
			
			// Triangle Fan
			0.0f,		0.0f,		0.5f,		0.5f,
			-.5f,		-.8f,		0.0f,		0.9f,
			0.5f,		-.8f,		1.0f,		0.9f,
			0.5f,		0.8f,		1.0f,		0.1f,
			-.5f,		0.8f,		0.0f,		0.1f,
			-.5f,		-.8f,		0.0f,		0.9f
	};
	
	private final VertexArray mVertexArray;
	
	public Table() {
		mVertexArray = new VertexArray(VERTEX_DATA);
	}
	
	public void bindData(TextureShaderProgram textureProgram) {
		int position = textureProgram.getPositionAttributeLocation();
		mVertexArray.setVertexAttribPointer(0, position, POSITION_COMPONENT_COUNT, STRIDE);
		
		int textureCoordinate = textureProgram.getTextureCoordinatesAttributeLocation();
		mVertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT, textureCoordinate, TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
	}
	
	public void draw() {
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
	}
}
