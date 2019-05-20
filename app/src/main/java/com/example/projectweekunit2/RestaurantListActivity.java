package com.example.projectweekunit2;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
		
		ArrayList<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(new MenuItem(1,1,"Potato Soup 1", 4, 5));
		menuItems.add(new MenuItem(1,1,"Potato Soup 2", 4, 5));
		menuItems.add(new MenuItem(1,1,"Potato Soup 3", 4, 5));
		menuItems.add(new MenuItem(1,1,"Potato Soup 4", 4, 5));
		menuItems.add(new MenuItem(1,1,"Potato Soup 5", 4, 5));
		menuItems.add(new MenuItem(1,1,"Potato Soup 6", 4, 5));
		menuItems.add(new MenuItem(1,1,"Potato Soup 7", 4, 5));
		
		
		restaurants.add(new Restaurant(1,null, "Restaurant 1", menuItems, null));
		restaurants.add(new Restaurant(2,null, "Restaurant 2", menuItems, null));
		restaurants.add(new Restaurant(3,null, "Restaurant 3", menuItems, null));
		restaurants.add(new Restaurant(4,null, "Restaurant 4", menuItems, null));
		restaurants.add(new Restaurant(5,null, "Restaurant 5", menuItems, null));
		restaurants.add(new Restaurant(6,null, "Restaurant 6", menuItems, null));
		restaurants.add(new Restaurant(7,null, "Restaurant 7", menuItems, null));
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
		drawerLayout.setVisibility(View.VISIBLE);
		
		
	}
}
