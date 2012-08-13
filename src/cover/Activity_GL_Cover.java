package cover;

import com.jimmy3d.R;
import android.app.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Activity_GL_Cover extends Activity {
	/** Called when the activity is first created. */

	//MySurfaceView mGLSurfaceView;
	CoverGLSurfaceView mGLSurfaceView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ball_layout);
	
		//mGLSurfaceView = new MySurfaceView(this);
		mGLSurfaceView = new CoverGLSurfaceView(this);
		mGLSurfaceView.requestFocus();// 获取焦点
		mGLSurfaceView.setFocusableInTouchMode(true);// 设置为可触控

		FrameLayout ll =  (FrameLayout) findViewById(R.id.surface_3dlayout);
		ll.addView(mGLSurfaceView);

		ToggleButton tb = (ToggleButton) this.findViewById(R.id.tbtn_off);
		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

			}
		});
		ToggleButton tb_stopRoate = (ToggleButton) this.findViewById(R.id.tbtn_stop_roate);
		tb_stopRoate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
				
				}else{
				
				}
			}
		});
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
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if(keyCode==KeyEvent.KEYCODE_BACK){
	    		finish();
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
}
