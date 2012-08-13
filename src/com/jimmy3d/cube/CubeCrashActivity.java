package com.jimmy3d.cube;

import com.jimmy3d.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
/**
 * http://blog.csdn.net/dlnuchunge/article/details/6912044
 * @author Jimmy
 *
 */
public class CubeCrashActivity extends Activity {
	private CubeSurfaceView mSurfaceView;//声明MySurfaceView对象  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.ball_layout);       
        mSurfaceView=new CubeSurfaceView(this);//创建MySurfaceView对象  
        mSurfaceView.requestFocus();//获取焦点  
        mSurfaceView.setFocusableInTouchMode(true);//设置为可触控  

		FrameLayout ll =  (FrameLayout) findViewById(R.id.surface_3dlayout);
		ll.addView(mSurfaceView);
        
    }  
    @Override  
    protected void onPause() {  
        // TODO Auto-generated method stub  
        super.onPause();  
        mSurfaceView.onPause();  
    }  
    @Override  
    protected void onResume() {  
        // TODO Auto-generated method stub  
        super.onResume();  
        mSurfaceView.onResume();  
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
