package com.example.projectweekunit2;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

public class MenuItemListActivity extends AppCompatActivity {
	
	MenuItemListAdapter listAdapter;
	RecyclerView recyclerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item_list);
		
		recyclerView = findViewById(R.id.menu_item_recycler_view);
		
		Bundle data = this.getIntent().getBundleExtra("bundle_key");
		 ArrayList<MenuItem> menuItems = data.getParcelableArrayList("restaurant_key");
		
		listAdapter = new MenuItemListAdapter(menuItems);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		
		recyclerView.setAdapter(listAdapter);
		recyclerView.setLayoutManager(layoutManager);
		
		Toolbar toolbar                = findViewById(R.id.toolbar);
		DrawerLayout drawerLayout      = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}
}
