package com.bharathmg.irctc;

import com.bharathmg.irctc.adapter.TabHomeAdapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

public class Home extends ActionBarActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabHomeAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "PNR", "Seat Availability", "Train Schedule" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		mAdapter = new TabHomeAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab arg0, android.support.v4.app.FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab arg0, android.support.v4.app.FragmentTransaction arg1) {
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.support.v4.app.FragmentTransaction arg1) {
	}

}
