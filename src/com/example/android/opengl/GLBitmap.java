package com.example.android.opengl;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

import com.airHockey.android.util.TextureHelper;

import android.opengl.GLES20;

public class GLBitmap {
	 private final String vertexShaderCode =
	            // This matrix member variable provides a hook to manipulate
	            // the coordinates of the objects that use this vertex shader
	            "uniform mat4 uMVPMatrix;" +
	            "attribute vec4 vPosition;" +
	            "void main() {" +
	            // the matrix must be included as a modifier of gl_Position
	            // Note that the uMVPMatrix factor *must be first* in order
	            // for the matrix multiplication product to be correct.
	            "  gl_Position = uMVPMatrix * vPosition;" +
	            "}";

	    private final String fragmentShaderCode =
	            "precision mediump float;" +
	            "uniform vec4 vColor;" +
	            "void main() {" +
	            "  gl_FragColor = vColor;" +
	            "}";


	    private final int mProgram;
	    private int mPositionHandle;
	    private int mColorHandle;
	    private int mMVPMatrixHandle;
	    
	    //-----
	    private int uMatrixLocation;
		private int uTextureUnitLocation;
		// attribute locations
		private int aPositionLocation;
		private int aTextureCoordinatesLocation;
		protected static final String U_MATRIX = "u_Matrix";
		protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
		protected static final String U_COLOR = "u_Color";
		// attributes constants
		protected static final String A_POSITION = "a_Position";
		protected static final String A_COLOR = "a_Color";
		protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
		private int mTexture;
		//------

	    // number of coordinates per vertex in this array
	    static final int COORDS_PER_VERTEX = 3;
	    static float triangleCoords[] = {
	            // in counterclockwise order:
	            0.0f,  0.622008459f, 0.0f,   // top
	           -0.5f, -0.311004243f, 0.0f,   // bottom left
	            0.5f, -0.311004243f, 0.0f    // bottom right
	    };
	    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
	    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

	    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

	    /**
	     * Sets up the drawing object data for use in an OpenGL ES context.
	     */
	    public GLBitmap() {
	        // prepare shaders and OpenGL program
	        int vertexShader = MyGLRenderer.loadShader(
	                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
	        int fragmentShader = MyGLRenderer.loadShader(
	                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

	        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
	        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
	        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
	        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
	        uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);
			uTextureUnitLocation = glGetUniformLocation(mProgram, U_TEXTURE_UNIT);
			
			// Retrieve attribute locations for the shader program
			aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
			aTextureCoordinatesLocation = glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES);
	    }

	    /**
	     * Encapsulates the OpenGL ES instructions for drawing this shape.
	     *
	     * @param mvpMatrix - The Model View Project matrix in which to draw
	     * this shape.
	     */
	    public void draw(float[] matrix) {
	        // Add program to OpenGL environment
	        GLES20.glUseProgram(mProgram);
	        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
			
			// This method specifies which texture unit a texture object is bound to when glBindTexture() is called.
			glActiveTexture(GL_TEXTURE0);
			
			mTexture = TextureHelper.loadTexture(OpenGLES20Activity.mInstance, R.drawable.ic_launcher);
			// Bind the texture to this unit
			glBindTexture(GL_TEXTURE_2D, mTexture);
			
			// Tell the texture uniform samper to use this texture in the shader by telling it to read from texture unit 0.
			glUniform1i(uTextureUnitLocation, 0);
	       
	    }
 
}
