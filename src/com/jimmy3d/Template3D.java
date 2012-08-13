package com.jimmy3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jimmy3d.cube.CubeSurfaceView;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

//EGL interface introduction :http://www.cnitblog.com/zouzheng/archive/2011/05/30/74326.html
public class Template3D extends Activity {
	private static final String LOG_TAG = Template3D.class.getSimpleName();
	TemplateGLSurfaceView mSurfaceView;
	/*
                 Y ^         ^Z
                   |        /
                   |       /
                   |      /
                   |     /
                   |    /
                   |   /
                   |  /
                   | /
                   |/
                   |-------------------> X
                   D Coordinate System  (three dimensional reference system ) 非右手定则
	 */
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		    
        mSurfaceView=new TemplateGLSurfaceView(this);//创建MySurfaceView对象  
        mSurfaceView.requestFocus();//获取焦点  
        mSurfaceView.setFocusableInTouchMode(true);//设置为可触控  
        setContentView(mSurfaceView);   
		
	}
   
   class TemplateGLSurfaceView extends GLSurfaceView{
     private TemplateRenderer _renderer;
	public TemplateGLSurfaceView(Context context) {
		super(context);
		_renderer = new TemplateRenderer();
		setRenderer(_renderer);
	}
	 public boolean onTouchEvent(final MotionEvent event) {
	        queueEvent(new Runnable() {
	            public void run() {
	               // _renderer.setColor(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f);
	            }
	        });
	        return true;
	    }	   
   }
   
   class TemplateRenderer implements Renderer{
    private float _red = 0.2f;
    private float _green = 0.9f;
    private float _blue = 0.2f;
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
        gl.glClearColor(_red, _green, _blue, 1.0f);//set this color to clear the window 
		gl.glClearDepthf(0.0f); //clear swap buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);// use set color clear window
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0,0, width, height);
//		GLU.gluLookAt(gl, eyeX, eyeY, eyeZ, 
//				centerX, centerY, centerZ,
//				upX, upY, upZ);
		GLU.gluLookAt(gl, 0, 0, 20, 0, 0, 0, 0, 1, 0);
		//GLU.gluperspective 
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(_red, _green, _blue, 1.0f);//set this color to clear the window 
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// use set color clear window
	}
	 public void setColor(float r, float g, float b) {
	        _red = r;
	        _green = g;
	        _blue = b;
	    }   
   }
}
