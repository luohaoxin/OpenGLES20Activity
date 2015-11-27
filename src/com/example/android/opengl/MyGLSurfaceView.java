/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen. This view
 * can also be used to capture touch events, such as a user interacting with
 * drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

	private final MyGLRenderer mRenderer;
	float preX;
	float preY;
	int widthPixels;
	int heightPixels;

	public MyGLSurfaceView(Context context) {
		super(context);
		widthPixels=getResources().getDisplayMetrics().widthPixels;
		heightPixels=getResources().getDisplayMetrics().heightPixels;
		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		mRenderer = new MyGLRenderer();
		setRenderer(mRenderer);

		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, you are only
		// interested in events where the touch position changed.

		float x = getGLx(e.getX());
		float y = getGLy(e.getY());
		Log.i("luohaoxin", "x:"+x+" y:"+y);
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			MyGLRenderer.mPath.moveTo(x, y);
//			MyGLRenderer.mPath.draw(MyGLRenderer.mProjectionMatrix);
			requestRender();
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			MyGLRenderer.mPath.quadTo(preX, preY, x, y);
//			MyGLRenderer.mPath.draw(MyGLRenderer.mProjectionMatrix);
			requestRender();
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_UP:
			MyGLRenderer.mPath.quadTo(preX, preY, x, y);
//			MyGLRenderer.mPath.draw(MyGLRenderer.mProjectionMatrix);
			requestRender();
			break;
		}
		return true;
	}
	private float getGLx(float x)
	{
		return (x*2.0f/widthPixels-1f);
	}
	private float getGLy(float y)
	{
		return MyGLRenderer.aspectRatio-y*2.0f/widthPixels;
	}
}
