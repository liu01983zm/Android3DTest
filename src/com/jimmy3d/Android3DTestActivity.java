package com.jimmy3d;

import com.jimmy3d.ball.Activity_GL_Ball;
import com.jimmy3d.cricle.Activity_GL_Cylinder;
import com.jimmy3d.cube.CubeCrashActivity;
import com.jimmy3d.solar.SolarActivity;
import com.jimmy3d.triangle.Activity_GL_Triangle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
/**
 * 
 * OpenGL ES 2.0 Reference http://www.khronos.org/opengles/sdk/docs/man/
 * EGL接口介绍（转） EGL interface introduction :http://www.cnitblog.com/zouzheng/archive/2011/05/30/74326.html
 * 引路蜂移动软件 OpenGL ES 开发教程 http://www.imobilebbs.com/wordpress/?page_id=2376
 * */
public class Android3DTestActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	LinearLayout  rootLayout = null;
	public static final int TEMPLATE_ID = 0;
	public static final int BALL_ID =  1;
	public static final int CYLINDER_ID = 2;//Cylinder
	public static final int TRIANGLE_ID = 3;
	public static final int PYRAMID_ID = 4;//pyramid
	public static final int CUBE_ID = 5;
	public static final int SOLAR_ID =6;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        rootLayout = (LinearLayout) this.findViewById(R.id.main_liner);
        Button btn0 = new Button(this);
        btn0.setId(TEMPLATE_ID);
        btn0.setText("Template 3D");
        btn0.setOnClickListener(this);
        rootLayout.addView(btn0);
        
        Button btn1 = new Button(this);
        btn1.setId(BALL_ID);
        btn1.setText("Ball");
        btn1.setOnClickListener(this);
        rootLayout.addView(btn1);
        
        Button btn2 = new Button(this);
        btn2.setId(CYLINDER_ID);
        btn2.setText("Cylinder");
        btn2.setOnClickListener(this);
        rootLayout.addView(btn2);
        
        Button btn3 = new Button(this);
        btn3.setId(TRIANGLE_ID);
        btn3.setText("Triangle");
        btn3.setOnClickListener(this);
        rootLayout.addView(btn3);
        
        Button btn4 = new Button(this);
        btn4.setId(PYRAMID_ID);
        btn4.setText("Pyramid");
        btn4.setOnClickListener(this);
        rootLayout.addView(btn4);
        
        Button btn5 = new Button(this);
        btn5.setId(CUBE_ID);
        btn5.setText("Cube Crash");
        btn5.setOnClickListener(this);
        rootLayout.addView(btn5);
        
        Button btn6 = new Button(this);
        btn6.setId(SOLAR_ID);
        btn6.setText("Solar System");
        btn6.setOnClickListener(this);
        rootLayout.addView(btn6);
        
        
    }

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()){
		case TEMPLATE_ID:
			intent.setClass(getApplicationContext(),Template3D.class);
			break;
		case BALL_ID:
			intent.setClass(getApplicationContext(),Activity_GL_Ball.class);
			break;
		case CYLINDER_ID:
			intent.setClass(getApplicationContext(),Activity_GL_Cylinder.class);
			break;
		case TRIANGLE_ID:
			intent.setClass(getApplicationContext(),Activity_GL_Triangle.class);
			break;
		case PYRAMID_ID:
			//intent.setClass(getApplicationContext(),Activity_GL_Triangle.class);
			break;
		case CUBE_ID:
			intent.setClass(getApplicationContext(),CubeCrashActivity.class);
			break;
		case SOLAR_ID:
			intent.setClass(getApplicationContext(),SolarActivity.class);
			break;
		}
		startActivity(intent);
		
	}
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if(keyCode==KeyEvent.KEYCODE_BACK){
	    		finish();
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
}