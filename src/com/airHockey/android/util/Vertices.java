package com.airHockey.android.util;

public interface Vertices {

	public static final float[] tableVerticesWithTriangles = {
			// Triangle 1
			-0.5f,	 	-0.5f,
			0.5f,		0.5f,
			-0.5f,		0.5f,
			
			// Triangle 2
			-0.5f,	 	-0.5f,
			0.5f,		-0.5f,
			0.5f,		0.5f,
			
			// Line 1
			-0.5f,		.0f,
			0.5f,		.0f,
			
			// Mallets
			.0f,			-.25f,
			.0f,			.25f
	};
	
	public static final float[] tableVerticesWithTriangleFan = {
			// triangle fans
			0.0f,		0.0f,
			-.5f,		-.5f,
			0.5f,		-.5f,
			0.5f,		0.5f,
			-.5f,		0.5f,
			-.5f,		-.5f,
			
			// Line 1
			-.5f,		0.0f,
			0.5f,		0.0f,
						
			// Mallets
			0.0f,		-.25f,
			0.0f,		.25f
	};
	
	public static final float[] tableVerticesWithColors = {
			// Order of coordinates: X, Y, R, G, B
			0.0f,		0.0f,		1.0f,		1.0f,		1.0f,
			-.5f,		-.5f,		0.7f,		0.7f,		0.7f,
			0.5f,		-.5f,		0.7f,		0.7f,		0.7f,
			0.5f,		0.5f,		0.7f,		0.7f,		0.7f,
			-.5f,		0.5f,		0.7f,		0.7f,		0.7f,
			-.5f,		-.5f,		0.7f,		0.7f,		0.7f,
			
			// Line 1
			-.5f,		0.0f,		1.0f,		0.0f,		0.0f,
			0.5f,		0.0f,		1.0f,		0.0f,		0.0f,
						
			// Mallets
			0.0f,		-.25f,		0.0f,		0.0f,		1.0f,
			0.0f,		.25f,		1.0f,		0.0f,		0.0f
	};
	
	public static final float[] tableVerticesWithProjection = {
			// Order of coordinates: X, Y, R, G, B
			0.0f,		0.0f,		1.0f,		1.0f,		1.0f,
			-.5f,		-.8f,		0.7f,		0.7f,		0.7f,
			0.5f,		-.8f,		0.7f,		0.7f,		0.7f,
			0.5f,		0.8f,		0.7f,		0.7f,		0.7f,
			-.5f,		0.8f,		0.7f,		0.7f,		0.7f,
			-.5f,		-.8f,		0.7f,		0.7f,		0.7f,
			
			// Line 1
			-.5f,		0.0f,		1.0f,		0.0f,		0.0f,
			0.5f,		0.0f,		1.0f,		0.0f,		0.0f,
						
			// Mallets
			0.0f,		-.4f,		0.0f,		0.0f,		1.0f,
			0.0f,		0.4f,		1.0f,		0.0f,		0.0f
	};
	
	public static final float[] tableVerticesWithThirdDimension = {
			// Order of coordinates: X, Y, Z, W, R, G, B
			0.0f,		0.0f,		0.0f,		1.5f,		1.0f,		1.0f,		1.0f,
			-.5f,		-.8f,		0.0f,		1.0f,		0.7f,		0.7f,		0.7f,
			0.5f,		-.8f,		0.0f,		1.0f,		0.7f,		0.7f,		0.7f,
			0.5f,		0.8f,		0.0f,		2.0f,		0.7f,		0.7f,		0.7f,
			-.5f,		0.8f,		0.0f,		2.0f,		0.7f,		0.7f,		0.7f,
			-.5f,		-.8f,		0.0f,		1.0f,		0.7f,		0.7f,		0.7f,
			
			// Line 1
			-.5f,		0.0f,		0.0f,		1.5f,		1.0f,		0.0f,		0.0f,
			0.5f,		0.0f,		0.0f,		1.5f,		1.0f,		0.0f,		0.0f,
						
			// Mallets
			0.0f,		-.4f,		0.0f,		1.25f,		0.0f,		0.0f,		1.0f,
			0.0f,		0.4f,		0.0f,		1.75f,		1.0f,		0.0f,		0.0f
	};
}
