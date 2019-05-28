package com.wkdrabbit.projectweekunit2;

import android.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuItemListActivity extends AppCompatActivity implements
		MenuItemAddDialogFragment.OnCompleteListener,
		MenuItemOnClickDialog.OnCompleteAddHistoryListener {
	
	MenuItemListAdapter listAdapter;
	RecyclerView recyclerView;
	ImageView btnAddMenuItem;
	ArrayList<MenuItem> menuItems;
	Restaurant restaurant;
	TextView tvPassData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item_list);
		menuItems = new ArrayList<>();
		restaurant = null;
		btnAddMenuItem = findViewById(R.id.btn_add_menu_item);
		tvPassData = findViewById(R.id.tv_pass_data);
		
		initRecyclerView();
		initToolBar();
		initAdapterList();
	}
	
	
	public void initAdapterList(){
		final Bundle data = this.getIntent().getBundleExtra("bundle_key");
		menuItems = data.getParcelableArrayList("restaurant_key");
		restaurant = data.getParcelable("full_restaurant_key");
		restaurant.setMenu(menuItems);
		listAdapter.updateData(menuItems);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < menuItems.size(); i++) {
					
					double userRating = FirebaseDao.getUserRating(menuItems.get(i));
					if (userRating != -1) {
						menuItems.get(i).setRating(userRating);
						listAdapter.updateData(menuItems);
					}
				}
				
				restaurant.setMenu(menuItems);
				FirebaseDao.updateRestaurant(restaurant);
				listAdapter.updateData(restaurant.getMenu());
			}
		}).start();
	}
	
	public void initRecyclerView() {
		recyclerView = findViewById(R.id.menu_item_recycler_view);
		FragmentManager fragmentManager = this.getFragmentManager();
		listAdapter = new MenuItemListAdapter(menuItems, this, fragmentManager);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		
		recyclerView.setAdapter(listAdapter);
		recyclerView.setLayoutManager(layoutManager);
		
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
		MenuItemAddDialogFragment dialog = new MenuItemAddDialogFragment();
		dialog.show(this.getFragmentManager(), "Dialog");
	}
	
	@Override
	public void onComplete(String menuItemName) {
		
		final String finMenuItemName = menuItemName;
		new Thread(new Runnable() {
			@Override
			public void run() {
				MenuItem addMenuItem = new MenuItem("", restaurant.getId(), restaurant.getName(), finMenuItemName, 0, 0, "", 0);
				restaurant.getMenu().add(addMenuItem);
				FirebaseDao.parseReviews(restaurant);
				restaurant.addToMenu(addMenuItem);
				listAdapter.updateData(menuItems);
			}
		}).start();
		listAdapter.notifyDataSetChanged();
listAdapter.updateData(menuItems);
	}
	
	@Override
	protected void onResume() {
		initAdapterList();
		listAdapter.updateData(menuItems);
		super.onResume();
	}
	
	@Override
	public void onComplete(String review, int rating) {
		int pos = Constants.atomicMenuPos.get();
		
		menuItems.get(pos).setRating(rating);
		menuItems.get(pos).setReview(review);
		
		FirebaseDao.createEntry(menuItems.get(pos));
		
		listAdapter.updateData(menuItems);
		Intent userHistoryIntent = new Intent(this, UserHistoryActivity.class);
		startActivity(userHistoryIntent);
	}
}
