package com.airHockey.android.programs;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

import com.airHockey.android.data.VertexArray;
import com.example.android.opengl.R;

import android.content.Context;

public class TextureShaderProgram extends ShaderPrograms {
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * VertexArray.BYTES_PER_FLOAT;

	// uniform locations
	private int uMatrixLocation;
	private int uTextureUnitLocation;
	// attribute locations
	private int aPositionLocation;
	private int aTextureCoordinatesLocation;
	
	VertexArray mVertexArray;
	
	public TextureShaderProgram(Context context) {
		super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
		mVertexArray=new VertexArray(24);
		// Retrieve uniform locations for the shader program
		uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);
		uTextureUnitLocation = glGetUniformLocation(mProgram, U_TEXTURE_UNIT);
		
		// Retrieve attribute locations for the shader program
		aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
		aTextureCoordinatesLocation = glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES);
	}
	
	public void setUniforms(float[] matrix, int textureId) {
		// Pass the matrix into the shader program
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		
		// This method specifies which texture unit a texture object is bound to when glBindTexture() is called.
		glActiveTexture(GL_TEXTURE0);
		
		// Bind the texture to this unit
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		// Tell the texture uniform samper to use this texture in the shader by telling it to read from texture unit 0.
		glUniform1i(uTextureUnitLocation, 0);
	}
	public void setAttribs(float[] VERTEX_DATA)
	{
		mVertexArray.putVertexData(VERTEX_DATA);
		mVertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, STRIDE);
		mVertexArray.setVertexAttribPointer(2, aTextureCoordinatesLocation, TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
		
	}
	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}
	
	public int getTextureCoordinatesAttributeLocation() {
		return aTextureCoordinatesLocation;
	}
}
