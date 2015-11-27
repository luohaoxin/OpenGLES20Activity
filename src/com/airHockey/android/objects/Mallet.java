package com.airHockey.android.objects;

import java.util.List;

import com.airHockey.android.data.VertexArray;
import com.airHockey.android.objects.ObjectBuilder.DrawCommand;
import com.airHockey.android.objects.ObjectBuilder.GeneratedData;
import com.airHockey.android.programs.ColorShaderProgram;
import com.airHockey.android.util.Geometry.Point;

public class Mallet {
	private static final int POSITION_COMPONENT_COUNT = 3;
	
	public final float mRadius;
	public final float mHeight;
	
	private final VertexArray mVertexArray;
	private final List<DrawCommand> mDrawList;
	
	public Mallet(float radius, float height, int numPointsAroundMallet) {
		Point malletCenter = new Point(.0f, .0f, .0f);
		GeneratedData malletData = ObjectBuilder.createMallet(malletCenter, radius, height, numPointsAroundMallet);
		
		this.mRadius = radius;
		this.mHeight = height;
		this.mVertexArray = new VertexArray(malletData.vertexData);
		this.mDrawList = malletData.drawList;
	}
	
	public void bindData(ColorShaderProgram colorProgram) {
		int positionLocation = colorProgram.getPositionAttributeLocation();
		mVertexArray.setVertexAttribPointer(0, positionLocation, POSITION_COMPONENT_COUNT, 0);
	}
	
	public void draw() {
		for (DrawCommand next : mDrawList) {
			next.draw();
		}
	}
}
