package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.R.dimen;
import com.cettco.buycar.R.drawable;
import com.cettco.buycar.R.id;
import com.cettco.buycar.R.layout;
import com.cettco.buycar.R.menu;
import com.cettco.buycar.fragment.SettingsFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private SlidingMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("Welcome");
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);
        
        //TextView settingsTextView = menu.inflate(context, resource, root)
//        TextView setttingsTextView = (TextView) findViewById(R.id.settings_text);
//        setttingsTextView.setOnClickListener(settingsClickListener);
        LinearLayout settingLayout = (LinearLayout)findViewById(R.id.setting_layout);
        settingLayout.setOnClickListener(settingsClickListener);
        
        
        ImageButton drawerButton = (ImageButton) findViewById(R.id.actionbar_drawer);
        drawerButton.setOnClickListener(drawerClickListener);
       
        LinearLayout cityLayout = (LinearLayout)findViewById(R.id.select_city);
        cityLayout.setOnClickListener(selectCityClickListener);
        
        LinearLayout accountLayout = (LinearLayout)findViewById(R.id.account_layout);
        accountLayout.setOnClickListener(accountClickListener);
       
        ImageButton addImageButton = (ImageButton)findViewById(R.id.addCar_btn);
        addImageButton.setOnClickListener(addCarClickListener);
        
    }
    protected OnClickListener accountClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SignInActivity.class);
			startActivity(intent);
		}
	};
    protected OnClickListener addCarClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CarListActivity.class);
			startActivity(intent);
		}
	};
	
    protected OnClickListener selectCityClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MyBaseListActivity.class);
			ArrayList<String> myArrayList = new ArrayList<String>();
			myArrayList.add("Beijin");
			myArrayList.add("Shanghai");
			myArrayList.add("Guangzhou");
			intent.putStringArrayListExtra("list", myArrayList);
			startActivity(intent);
		}
	};
    protected OnClickListener drawerClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			menu.toggle();
			
		}
	};
	protected OnClickListener settingsClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switchFragment(new SettingsFragment());
			setTitle("Settings");
			menu.toggle();
		}
	};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_carAdd) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void switchFragment(android.support.v4.app.Fragment fragment) {
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
	}
    public void exitClick(View view)
	{
		finish();
	}
}
