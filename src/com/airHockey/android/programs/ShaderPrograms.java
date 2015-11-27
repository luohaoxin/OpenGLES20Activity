package com.airHockey.android.programs;

import static android.opengl.GLES20.glUseProgram;

import com.airHockey.android.util.ShaderHelper;
import com.airHockey.android.util.TextResourceReader;

import android.content.Context;

public abstract class ShaderPrograms {
	// uniform constans
	protected static final String U_MATRIX = "u_Matrix";
	protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
	protected static final String U_COLOR = "u_Color";
	
	// attributes constants
	protected static final String A_POSITION = "a_Position";
	protected static final String A_COLOR = "a_Color";
	protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
	
	protected final int mProgram;
	
	protected ShaderPrograms(Context context, int vertexShaderResourceId, int fragmentShaderReourceId) {
		String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId);
		String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, fragmentShaderReourceId);
		
		mProgram = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
	}
	protected ShaderPrograms(Context context, String vertexShaderSource, String fragmentShaderSource) {
		mProgram = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
	}
	public void useProgram() {
		glUseProgram(mProgram);
	}
}
