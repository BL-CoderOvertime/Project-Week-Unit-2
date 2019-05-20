package com.example.projectweekunit2;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MenuItemListActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item_list);
		
		Toolbar toolbar                = findViewById(R.id.toolbar);
		DrawerLayout drawerLayout      = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}
}
