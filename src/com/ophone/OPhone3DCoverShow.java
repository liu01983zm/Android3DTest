package com.ophone;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OPhone3DCoverShow extends Activity {
	private GLSurfaceView mGLSurfaceView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGLSurfaceView = new MyGLSurfaceView(this);
		setContentView(mGLSurfaceView);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLSurfaceView.onPause();
	}
}