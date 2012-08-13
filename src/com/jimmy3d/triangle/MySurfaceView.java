package com.jimmy3d.triangle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private MySceneRenderer mysRenderer;
	private float myPreviousY;
	private float myPreviousX;

	public MySurfaceView(Context context) {
		super(context);
		mysRenderer = new MySceneRenderer();
		this.setRenderer(mysRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - myPreviousY;
			float dx = x - myPreviousX;
			mysRenderer.tr.yAngle += dx * TOUCH_SCALE_FACTOR;
			mysRenderer.tr.zAngle += dy * TOUCH_SCALE_FACTOR;
			requestRender();
			break;
		}
		myPreviousX = x;
		myPreviousY = y;
		return true;
	}

}
