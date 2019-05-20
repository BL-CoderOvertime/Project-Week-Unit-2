package com.example.projectweekunit2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class UserHistoryActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_history);
		
		Toolbar toolbar                = findViewById(R.id.toolbar);
		DrawerLayout drawerLayout      = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}
	
}
