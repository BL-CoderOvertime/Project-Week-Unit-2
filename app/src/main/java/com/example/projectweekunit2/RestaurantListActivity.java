package com.example.projectweekunit2;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity {
	
	RestaurantListAdapter listAdapter;
	RecyclerView recyclerView;
	
	//TODO: move this to Dao
	ArrayList<Restaurant> restaurants;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		
		recyclerView = findViewById(R.id.restaurant_list_recycler_view);
		restaurants = new ArrayList<>();
		
		//int id, LatLng location, String name, ArrayList<MenuItem> menu, URI imageUri
		restaurants.add(new Restaurant(1,null, "Restaurant 1", new ArrayList<MenuItem>(), null));
		restaurants.add(new Restaurant(2,null, "Restaurant 2", new ArrayList<MenuItem>(), null));
		restaurants.add(new Restaurant(3,null, "Restaurant 3", new ArrayList<MenuItem>(), null));
		restaurants.add(new Restaurant(4,null, "Restaurant 4", new ArrayList<MenuItem>(), null));
		restaurants.add(new Restaurant(5,null, "Restaurant 5", new ArrayList<MenuItem>(), null));
		restaurants.add(new Restaurant(6,null, "Restaurant 6", new ArrayList<MenuItem>(), null));
		restaurants.add(new Restaurant(7,null, "Restaurant 7", new ArrayList<MenuItem>(), null));
		listAdapter = new RestaurantListAdapter(restaurants);
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
