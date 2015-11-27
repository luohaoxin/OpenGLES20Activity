package com.example.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.airHockey.android.programs.ColorShaderProgram;

import android.opengl.GLES20;

public class Path {
    private final FloatBuffer vertexBuffer;
    private int mPositionHandle;
    ColorShaderProgram mColorShaderProgram;
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
    	mColorShaderProgram=new ColorShaderProgram(OpenGLES20Activity.mInstance);
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
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
//        GLES20.glUseProgram(mProgram);
    	vertexBuffer.clear();
        vertexBuffer.put(getFloatArray());
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
        
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(mvpMatrix, color);
        mColorShaderProgram.setAttribs(COORDS_PER_VERTEX, vertexStride, vertexBuffer);

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
