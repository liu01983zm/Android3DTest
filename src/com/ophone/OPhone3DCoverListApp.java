package com.ophone;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author Yong
 *
 */
public class OPhone3DCoverListApp extends ListActivity {
	private final String[] mpItems = { "Start Show", "Help"};
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		this.setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mpItems));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		
		switch(position) {
		case 0:
			intent.setClass(this, OPhone3DCoverShow.class);
			break;
		case 1:
			//help
			intent.setClass(this, HelpScreen.class);
			break;
			default:
				//enter help
				intent.setClass(this, HelpScreen.class);
				break;
		}
		
		
		this.startActivity(intent);
	}
}
