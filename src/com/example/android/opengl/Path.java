package com.example.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

public class Path {
	private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 u_Matrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            // the matrix must be included as a modifier of gl_Position
            // Note that the u_Matrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = u_Matrix * vPosition;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    private ArrayList<Float> pathList=new ArrayList<Float>();
//    static float pathCoords[] = {
//            // in counterclockwise order:
//            0.0f,  0.622008459f,   // top
//           -0.5f, -0.311004243f,   // bottom left
//            0.5f, -0.311004243f    // bottom right
//    };
//    private final int vertexCount = pathCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Path() {
    	pathList.add(0f);
    	pathList.add(0.622008459f);
    	pathList.add(-0.5f);
    	pathList.add(-0.5f);
    	pathList.add(0.5f);
    	pathList.add(-0.311004243f);
    	
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                4*1500);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        vertexBuffer.clear();
        vertexBuffer.put(getFloatArray());
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES20.glLineWidth(4);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, pathList.size() / COORDS_PER_VERTEX);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    private float[] getFloatArray()
    {
    	float[] result=new float[pathList.size()];
    	for (int i = 0; i < pathList.size(); i++) {
			result[i]=pathList.get(i);
		}
    	return result;
    }
    public void moveTo(float x,float y)
    {
    	pathList.clear();
    	pathList.add(x);
    	pathList.add(y);
    }
    public void quadTo(float preX,float preY,float x,float y)
    {
    	pathList.add(x);
    	pathList.add(y);
    }
}
