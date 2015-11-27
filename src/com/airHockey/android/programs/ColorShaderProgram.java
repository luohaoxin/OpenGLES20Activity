package com.airHockey.android.programs;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import com.example.android.opengl.R;

import android.content.Context;

public class ColorShaderProgram extends ShaderPrograms {

	// Uniform locations
	private final int uMatrixLocation;
	private final int uColorLocation;
	
	// Attribute locations
	private final int aPositionLocation;
	//private final int aColorLocation;
	
	public ColorShaderProgram(Context context) {
		super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
		
		uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);
		uColorLocation = glGetUniformLocation(mProgram, U_COLOR);
		aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
		//aColorLocation = glGetAttribLocation(mProgram, A_COLOR);
	}

	public void setUniforms(float[] matrix, float r, float g, float b) {
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		glUniform4f(uColorLocation, r, g, b, 1.0f);
	}
	
	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}
	
	public int getColorAttributeLocation() {
		//return aColorLocation;
		return uColorLocation;
	}
}
