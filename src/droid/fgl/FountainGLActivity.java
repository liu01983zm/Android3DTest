package droid.fgl;

import droid.fgl.FountainGLRenderer;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

public class FountainGLActivity extends Activity 
{
   	FountainGLRenderer mRenderer = null;
	MenuItem[] mMenuList = new MenuItem[10]; //options menu
   	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
		getWindow().setFlags(0xFFFFFFFF, //hide status bar and keep phone awake
				LayoutParams.FLAG_FULLSCREEN|LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		super.onCreate(savedInstanceState);

		//onCreate is called when phone orientation changes
		//no need to recreate render class
		if (mRenderer == null)
			mRenderer = new FountainGLRenderer(this); //openGL surface
		
		//retrieve options
		SharedPreferences sp = getSharedPreferences("FountainGL", 0);
		mRenderer.ShowBall = sp.getBoolean("ShowBall", mRenderer.ShowBall);
		mRenderer.ShowFountain = sp.getBoolean("ShowFountain", mRenderer.ShowFountain);
		mRenderer.ShowFloor = sp.getBoolean("ShowFloor", mRenderer.ShowFloor);
		mRenderer.ShowPool = sp.getBoolean("ShowPool", mRenderer.ShowPool);
		mRenderer.ShowFPS = sp.getBoolean("ShowFPS", mRenderer.ShowFPS);
		mRenderer.UseTiltAngle = sp.getBoolean("UseTiltAngle", mRenderer.UseTiltAngle); 
		mRenderer.RotateScene = sp.getBoolean("RotateScene", mRenderer.RotateScene);
		
		//calculate angle and position of camera
		mRenderer.SwapCenter();
		//mRenderer.SwapCenter();
		
	}

	//this method called every time menu is shown
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) 
	{
		menu.clear(); //reset menu
		//set menu items based on current settings
		/*mMenuList[0] = menu.add((mRenderer.ShowBall?"Hide":"Show")+" Ball");
		mMenuList[1] = menu.add((mRenderer.ShowFloor?"Hide":"Show")+" Floor");
		mMenuList[2] = menu.add((mRenderer.ShowFountain?"Hide":"Show")+" Fountain");
		mMenuList[3] = menu.add((mRenderer.ShowPool?"Hide":"Show")+" Pool");
		mMenuList[4] = menu.add("Rotate "+(mRenderer.RotateScene?"Camera":"Scene"));
		mMenuList[5] = menu.add("Use "+(mRenderer.UseTiltAngle?"Touch":"Tilt")+" Angle"); 
		mMenuList[6] = menu.add((mRenderer.MultiBillboard?"Single":"Multi")+" Billboard");
		mMenuList[7] = menu.add((mRenderer.ShowFPS?"Hide":"Show")+" FPS");
		mMenuList[8] = menu.add(mRenderer.Paused?"Unpause":"Pause");
		mMenuList[9] = menu.add("Exit");
		*/Log.i("activity menu","menu create------------");
		return super.onCreateOptionsMenu(menu);
	}
   
	//listener for menu item clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{	/*		
		if (item == mMenuList[0]) //Show\Hide Ball
			mRenderer.ShowBall = !mRenderer.ShowBall;
		else if (item == mMenuList[1]) //Show\Hide Floor
			mRenderer.ShowFloor = !mRenderer.ShowFloor;
		else if (item == mMenuList[2]) //Show\Hide Fountain
			mRenderer.ShowFountain = !mRenderer.ShowFountain;
		else if (item == mMenuList[3]) //Show\Hide Pool
			mRenderer.ShowPool = !mRenderer.ShowPool;			
		else if (item == mMenuList[4]) //Rotate Camera\Scene
			mRenderer.SwapCenter();
		else if (item == mMenuList[5]) //Use Touch\Tilt Angle
			mRenderer.UseTiltAngle = !mRenderer.UseTiltAngle;
		else if (item == mMenuList[6]) //Single\Multi Billboard
			mRenderer.MultiBillboard = !mRenderer.MultiBillboard;
		else if (item == mMenuList[7]) //Show\Hide FPS
			mRenderer.SetShowFPS(!mRenderer.ShowFPS);
		else if (item == mMenuList[8]) //Pause\Unpause
			mRenderer.Paused = !mRenderer.Paused;
		else if (item == mMenuList[9]) //Exit
			finish();
		*/
		//store options 
		getSharedPreferences("FountainGL", 0).edit()
		 .putBoolean("ShowBall", mRenderer.ShowBall)
		 .putBoolean("ShowFountain", mRenderer.ShowFountain)
		 .putBoolean("ShowPool", mRenderer.ShowPool)
		 .putBoolean("ShowFloor", mRenderer.ShowFloor)
		 .putBoolean("ShowFPS", mRenderer.ShowFPS)
		 .putBoolean("UseTiltAngle", mRenderer.UseTiltAngle)
		 .putBoolean("RotateScene", mRenderer.RotateScene)
		 .commit();
	
   		return super.onOptionsItemSelected(item);	
	}
}