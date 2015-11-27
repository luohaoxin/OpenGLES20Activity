package com.airHockey.android.programs;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import android.content.Context;
import android.opengl.GLES20;

public class ColorShaderProgram extends ShaderPrograms {
	public static String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 u_Matrix;" +
            "attribute vec4 a_Position;" +
            "void main() {" +
            // the matrix must be included as a modifier of gl_Position
            // Note that the u_Matrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = u_Matrix * a_Position;" +
            "}";

    public static String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 u_Color;" +
            "void main() {" +
            "  gl_FragColor = u_Color;" +
            "}";
	// Uniform locations
	private final int uMatrixLocation;
	private final int uColorLocation;
	
	// Attribute locations
	private final int aPositionLocation;
	//private final int aColorLocation;
	
	public ColorShaderProgram(Context context) {
		super(context, vertexShaderCode, fragmentShaderCode);
		
		uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);
		uColorLocation = glGetUniformLocation(mProgram, U_COLOR);
		aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
		//aColorLocation = glGetAttribLocation(mProgram, A_COLOR);
	}

	public void setUniforms(float[] matrix, float r, float g, float b) {
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		glUniform4f(uColorLocation, r, g, b, 1.0f);
	}
	public void setUniforms(float[] matrix, float color[]) {
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		GLES20.glUniform4fv(uColorLocation, 1,color,0);
	}
	public void setAttribs(int COORDS_PER_VERTEX,int vertexStride,FloatBuffer vertexBuffer)
	{
		GLES20.glEnableVertexAttribArray(aPositionLocation);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

	}
	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}
	
	public int getColorAttributeLocation() {
		//return aColorLocation;
		return uColorLocation;
	}
}
