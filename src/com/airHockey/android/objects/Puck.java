package com.airHockey.android.objects;

import java.util.List;

import com.airHockey.android.data.VertexArray;
import com.airHockey.android.objects.ObjectBuilder.DrawCommand;
import com.airHockey.android.objects.ObjectBuilder.GeneratedData;
import com.airHockey.android.programs.ColorShaderProgram;
import com.airHockey.android.util.Geometry.Cylinder;
import com.airHockey.android.util.Geometry.Point;

public class Puck {

	private static final int POSITION_COMPONENT_COUNT = 3;
	
	public final float mRadius;
	public final float mHeight;
	
	private final VertexArray mVertexArray;
	private final List<DrawCommand> mDrawList;
	
	public Puck(float radius, float height, int numPointsAroundPuck) {
		Point puckCenter = new Point(.0f, .0f, .0f);
		Cylinder puck = new Cylinder(puckCenter, radius, height);
		GeneratedData puckData = ObjectBuilder.createPuck(puck, numPointsAroundPuck);
		
		this.mRadius = radius;
		this.mHeight = height;
		this.mVertexArray = new VertexArray(puckData.vertexData);
		this.mDrawList = puckData.drawList;
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
