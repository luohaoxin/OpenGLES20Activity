package com.airHockey.android.objects;

import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;

import java.util.ArrayList;
import java.util.List;

import com.airHockey.android.util.Geometry.Circle;
import com.airHockey.android.util.Geometry.Cylinder;
import com.airHockey.android.util.Geometry.Point;

public class ObjectBuilder {

	private static final int FLOAT_PER_VERTEX = 3;
	
	private final float[] mVertexData;
	private int mOffset = 0;
	
	private final List<DrawCommand> mDrawList = new ArrayList<>();
	
	public ObjectBuilder(int sizeInVertices) {
		mVertexData = new float[sizeInVertices * FLOAT_PER_VERTEX];
	}
	
	private static int sizeOfCircleInVertices(int numPoints) {
		return 1 + (numPoints + 1);
	}
	
	private static int sizeOfOpenCylinderInVertices(int numPoints) {
		return (numPoints + 1) * 2;
	}
	
	static GeneratedData createPuck(Cylinder puck, int numPoints) {
		int size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints);
		
		ObjectBuilder builder = new ObjectBuilder(size);
		
		Circle puckTop = new Circle(puck.mCenter.translateY(puck.mHeight / 2.0f), puck.mRadius);
		builder.appendCircle(puckTop, numPoints);
		builder.appendOpenCylinder(puck, numPoints);
		
		return builder.build();
	}
	
	static GeneratedData createMallet(Point center, float radius, float height, int numPoints) {
		int size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints);
		size *= 2;
		
		ObjectBuilder builder = new ObjectBuilder(size);
		
		float baseHeight = height * 0.25f;
		Circle baseCircle = new Circle(center.translateY(-baseHeight), radius);
		Cylinder baseCylinder = new Cylinder(baseCircle.mCenter.translateY(-baseHeight / 2.0f), radius, baseHeight);
		builder.appendCircle(baseCircle, numPoints);
		builder.appendOpenCylinder(baseCylinder, numPoints);
		
		float handleHeight = height * 0.75f;
		float handleRadius = radius / 3.0f;
		Circle handleCircle = new Circle(center.translateY(height * 0.5f), handleRadius);
		Cylinder handleCylinder = new Cylinder(handleCircle.mCenter.translateY(-handleHeight / 2.0f), handleRadius, handleHeight);
		builder.appendCircle(handleCircle, numPoints);
		builder.appendOpenCylinder(handleCylinder, numPoints);
		
		return builder.build();
	}
	
	static interface DrawCommand {
		void draw();
	}
	
	static class GeneratedData {
		final float[] vertexData;
		final List<DrawCommand> drawList;
		
		public GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
			this.vertexData = vertexData;
			this.drawList = drawList;
		}
	}
	
	private GeneratedData build() {
		return new GeneratedData(mVertexData, mDrawList);
	}
	
	private void appendCircle(Circle circle, int numPoints) {
		final int startIndex = mOffset / FLOAT_PER_VERTEX;
		final int numVertices = sizeOfCircleInVertices(numPoints);
		
		mVertexData[mOffset++] = circle.mCenter.x;
		mVertexData[mOffset++] = circle.mCenter.y;
		mVertexData[mOffset++] = circle.mCenter.z;
		
		for (int i = 0; i <= numPoints; i++) {
			float angleInRadians = ((float)i / (float)numPoints) * ((float)Math.PI * 2.0f);
			mVertexData[mOffset++] = circle.mCenter.x + circle.mRadius * (float)Math.cos(angleInRadians);
			mVertexData[mOffset++] = circle.mCenter.y;
			mVertexData[mOffset++] = circle.mCenter.z + circle.mRadius * (float)Math.sin(angleInRadians);
		}
		
		mDrawList.add(new DrawCommand() {
			
			@Override
			public void draw() {
				glDrawArrays(GL_TRIANGLE_FAN, startIndex, numVertices);
			}
		});
	}
	
	private void appendOpenCylinder(Cylinder cylinder, int numPoints) {
		final int startIndex = mOffset / FLOAT_PER_VERTEX;
		final int numVertices = sizeOfOpenCylinderInVertices(numPoints);
		
		final float yStart = cylinder.mCenter.y - cylinder.mHeight / 2.0f;
		final float yEnd = cylinder.mCenter.y + cylinder.mHeight / 2.0f;
		
		for (int i = 0; i <= numPoints; i++) {
			float angleInRadians = ((float)i / (float)numPoints) * ((float)Math.PI * 2.0f);
			float xPosition = cylinder.mCenter.x + cylinder.mRadius * (float)Math.cos(angleInRadians);
			float zPosition = cylinder.mCenter.z + cylinder.mRadius * (float)Math.sin(angleInRadians);
			
			mVertexData[mOffset++] = xPosition;
			mVertexData[mOffset++] = yStart;
			mVertexData[mOffset++] = zPosition;
			
			mVertexData[mOffset++] = xPosition;
			mVertexData[mOffset++] = yEnd;
			mVertexData[mOffset++] = zPosition;
		}
		
		mDrawList.add(new DrawCommand() {
			
			@Override
			public void draw() {
				glDrawArrays(GL_TRIANGLE_STRIP, startIndex, numVertices);
			}
		});
	}
}
