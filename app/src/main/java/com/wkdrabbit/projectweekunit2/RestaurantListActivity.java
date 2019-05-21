package com.wkdrabbit.projectweekunit2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity {
	
	RestaurantListAdapter listAdapter;
	RecyclerView recyclerView;
	
	//TODO: move this to Dao
	final ArrayList<Restaurant> restaurants = new ArrayList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		
		Constants.setSharedPrefs(this);
		
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSIONS_REQUEST_LOCATION);
		} else {
			getLocation();
		}
		
		//initTempData();
		initRecyclerView();
		initToolBar();
		
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					restaurants.addAll(ZomatoApiDao.getRestaurantList());
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							listAdapter.notifyDataSetChanged();
						}
					});
				}
			}).start();
	}
	
	public void initTempData() {
		ArrayList<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(new MenuItem(1, 1, "Potato Soup 1", 4, 5));
		menuItems.add(new MenuItem(1, 1, "Potato Soup 2", 4, 5));
		menuItems.add(new MenuItem(1, 1, "Potato Soup 3", 4, 5));
		menuItems.add(new MenuItem(1, 1, "Potato Soup 4", 4, 5));
		menuItems.add(new MenuItem(1, 1, "Potato Soup 5", 4, 5));
		menuItems.add(new MenuItem(1, 1, "Potato Soup 6", 4, 5));
		menuItems.add(new MenuItem(1, 1, "Potato Soup 7", 4, 5));
		
		
		restaurants.add(new Restaurant(1, "Restaurant 1", menuItems, null));
		restaurants.add(new Restaurant(2, "Restaurant 2", menuItems, null));
		restaurants.add(new Restaurant(3, "Restaurant 3", menuItems, null));
		restaurants.add(new Restaurant(4, "Restaurant 4", menuItems, null));
		restaurants.add(new Restaurant(5, "Restaurant 5", menuItems, null));
		restaurants.add(new Restaurant(6, "Restaurant 6", menuItems, null));
		restaurants.add(new Restaurant(7, "Restaurant 7", menuItems, null));
	}
	
	public void initRecyclerView() {
		recyclerView = findViewById(R.id.restaurant_list_recycler_view);
		
		listAdapter = new RestaurantListAdapter(restaurants);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		
		recyclerView.setAdapter(listAdapter);
		recyclerView.setLayoutManager(layoutManager);
	}
	
	public void initToolBar() {
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
		
		toolbarLogic();
	}
	
	public void toolbarLogic() {
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull android.view.MenuItem menuItem) {
				menuItem.setChecked(true);
				Toast.makeText(getBaseContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
				
				switch (menuItem.getItemId()) {
					case R.id.favorite_menu_items:
						Intent intent = new Intent(getApplicationContext(), UserHistoryActivity.class);
						startActivity(intent);
						break;
				}
				
				return true;
			}
		});
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == Constants.PERMISSIONS_REQUEST_LOCATION) {
			if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				getLocation();
			} else {
				//permission denied
			}
		}
	}
	
	private void getLocation() {
		//check again before using permission
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
		}
		
		FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
		locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
			@Override
			public void onSuccess(Location location) {
				
				final Location finalLocation = location;
				Constants.setLatLon(location.getLatitude(), location.getLongitude());
			}
		});
		
	}
	
}
