package com.wkdrabbit.projectweekunit2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuItemListActivity extends AppCompatActivity {
	
	MenuItemListAdapter listAdapter;
	RecyclerView recyclerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item_list);
		
		initSharedPrefs();
		initRecyclerView();
		initToolBar();
	}
	
	public void initSharedPrefs(){
		final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		final SharedPreferences.Editor editor     = sharedPreferences.edit();
		
	}
	
	public void initRecyclerView() {
		recyclerView = findViewById(R.id.menu_item_recycler_view);
		
		Bundle data = this.getIntent().getBundleExtra("bundle_key");
		ArrayList<MenuItem> menuItems = data.getParcelableArrayList("restaurant_key");
		
		listAdapter = new MenuItemListAdapter(menuItems, this);
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
		NavigationView navigationView  = findViewById(R.id.nav_view);
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
}
