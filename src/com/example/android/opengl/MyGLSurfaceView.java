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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
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
	public static int widthPixels;
	public static int heightPixels;

	private Bitmap mSignatureBitmap;
	private Canvas mSignatureCanvas;
    private Path path;  
    Paint paint;
	public MyGLSurfaceView(Context context) {
		super(context);
		widthPixels=getResources().getDisplayMetrics().widthPixels;
		heightPixels=getResources().getDisplayMetrics().heightPixels;
		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		mRenderer = new MyGLRenderer(context);
		setRenderer(mRenderer);

		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
		mSignatureBitmap= Bitmap.createBitmap(widthPixels,heightPixels, Config.ARGB_8888);
		mSignatureCanvas = new Canvas();
		mSignatureCanvas.setBitmap(mSignatureBitmap);
		path=new Path();
		paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);  
        paint.setStrokeWidth(3);  
        //反锯齿  
        paint.setAntiAlias(true);  
        paint.setDither(true); 
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, you are only
		// interested in events where the touch position changed.

		float x = e.getX();
		float y = e.getY();
		Log.i("luohaoxin", "x:"+x+" y:"+y);
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(x, y);
//			mSignatureCanvas.drawPath(path, paint);
			MyGLRenderer.mPath.moveTo(x, y);
			requestRender();
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(preX, preY, x, y);
//			mSignatureCanvas.drawPath(path, paint);
			MyGLRenderer.mPath.quadTo(preX, preY, x, y);
			requestRender();
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_UP:
			path=new Path();
			MyGLRenderer.mPath.quadTo(preX, preY, x, y);
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
