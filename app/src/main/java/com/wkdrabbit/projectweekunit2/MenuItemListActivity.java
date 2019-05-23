package com.wkdrabbit.projectweekunit2;

import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wkdrabbit.projectweekunit2.util.MenuItemListAdapter;

import java.util.ArrayList;

public class MenuItemListActivity extends AppCompatActivity implements AddMenuItemOnClickDialog.OnCompleteListener {
	
	MenuItemListAdapter listAdapter;
	RecyclerView recyclerView;
	Button btnAddMenuItem;
	ArrayList<MenuItem> menuItems;
	Restaurant restaurant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item_list);
		menuItems = new ArrayList<>();
		restaurant = null;
		btnAddMenuItem = findViewById(R.id.btn_add_menu_item);
		
		initSharedPrefs();
		initRecyclerView();
		initToolBar();
	}
	
	public void initSharedPrefs() {
		final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPreferences.edit();
		
	}
	
	public void initRecyclerView() {
		recyclerView = findViewById(R.id.menu_item_recycler_view);
		listAdapter = new MenuItemListAdapter(menuItems, this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		
		recyclerView.setAdapter(listAdapter);
		recyclerView.setLayoutManager(layoutManager);
		
		listAdapter.updateData(menuItems);
		
		
		final Bundle data = this.getIntent().getBundleExtra("bundle_key");
		menuItems = data.getParcelableArrayList("restaurant_key");
		restaurant = data.getParcelable("full_restaurant_key");
		restaurant.setMenu(menuItems);
		listAdapter.updateData(menuItems);
		
		btnAddMenuItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAddMenuItemDialog();
			}
		});
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
	
	public void showAddMenuItemDialog() {
		AddMenuItemOnClickDialog dialog = new AddMenuItemOnClickDialog();
		dialog.show(this.getFragmentManager(), "Dialog");
	}
	
	@Override
	public void onComplete(String menuItemName) {
		MenuItem addMenuItem = new MenuItem("",restaurant.getId(),restaurant.getName(),menuItemName,0,0,"");
		//String id, int resturantId, String restaurantName,  String name, double price, int rating, String review
		menuItems.add(addMenuItem);
		restaurant.addToMenu(addMenuItem);
		listAdapter.updateData(menuItems);
	}
}
