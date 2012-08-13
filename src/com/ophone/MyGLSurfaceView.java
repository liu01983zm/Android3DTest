package com.ophone;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;
//aritcle:http://www.ophonesdn.com/article/show/138
//code: http://www.ophonesdn.com/forum/thread-832-1-1.html
public class MyGLSurfaceView extends GLSurfaceView {
	private final float MIN_DIS = 16;
	/**
	 * 具体实现的渲染器
	 */
	private OPhone3DCoverShowRenderer mRenderer;
	/**
	 * 记录上次触屏位置的坐标
	 */
	private float mPreviousX, mPreviousY;

	public MyGLSurfaceView(Context context) {
		super(context);
		// 设置渲染器
		mRenderer = new OPhone3DCoverShowRenderer(context);
		setRenderer(mRenderer);
		// 设置渲染模式为主动渲染
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	public void onPause() {
		super.onPause();
	}
	
	public void onResume() {
		super.onResume();
	}
	
	
	/**
	 * 响应触屏事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;

			if(dx > MIN_DIS) {
				queueEvent(new Runnable() {
	                 // This method will be called on the rendering
	                 // thread:
	                 public void run() {
	                	 mRenderer.slideLeft();
	                 }});
			} else if(dx < -MIN_DIS) {
				queueEvent(new Runnable() {
	                 // This method will be called on the rendering
	                 // thread:
	                 public void run() {
	                	 mRenderer.slideRight();
	                 }});
			} else {
				//do nothing
			}
		}
		mPreviousX = x;
		return true;
	}
	
	/**
	 * 响应按键事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            queueEvent(new Runnable() {
                // This method will be called on the rendering
                // thread:
                public void run() {
                	mRenderer.slideLeft();
                }});
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
        	queueEvent(new Runnable() {
                // This method will be called on the rendering
                // thread:
                public void run() {
                	mRenderer.slideRight();
                }});
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
