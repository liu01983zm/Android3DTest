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
	 * ����ʵ�ֵ���Ⱦ��
	 */
	private OPhone3DCoverShowRenderer mRenderer;
	/**
	 * ��¼�ϴδ���λ�õ�����
	 */
	private float mPreviousX, mPreviousY;

	public MyGLSurfaceView(Context context) {
		super(context);
		// ������Ⱦ��
		mRenderer = new OPhone3DCoverShowRenderer(context);
		setRenderer(mRenderer);
		// ������ȾģʽΪ������Ⱦ
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	public void onPause() {
		super.onPause();
	}
	
	public void onResume() {
		super.onResume();
	}
	
	
	/**
	 * ��Ӧ�����¼�
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
	 * ��Ӧ�����¼�
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
