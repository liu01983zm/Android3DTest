package com.jimmy3d.triangle;

import com.jimmy3d.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Activity_GL_Triangle extends Activity {
	private MySurfaceView mySurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mySurfaceView = new MySurfaceView(this);
		mySurfaceView.requestFocus();
		mySurfaceView.setFocusableInTouchMode(true);
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.main_liner);
		ll.addView(mySurfaceView);

	}

	@Override
	protected void onPause() {

		super.onPause();
		mySurfaceView.onPause();
	}

	@Override
	protected void onResume() {

		super.onResume();
		mySurfaceView.onResume();
	}
}