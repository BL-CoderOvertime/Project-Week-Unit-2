package com.wkdrabbit.projectweekunit2;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class UserHistoryActivity extends AppCompatActivity implements UserHistoryOnClickDialog.OnCompleteListener {
	
	UserHistoryListAdapter listAdapter;
	RecyclerView recyclerView;
	
	//TODO: move this to Dao
	ArrayList<UserHistoryItem> userHistoryItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_history);
		
		initData();
		initRecyclerView();
		initToolBar();
		
	}
	
	public void initData(){
		
		userHistoryItems = new ArrayList<>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				userHistoryItems = FirebaseDao.getUserHistory();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						initRecyclerView();
						listAdapter.updateData(userHistoryItems);
					}
				});
			}
		}).start();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	public void initToolBar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}
	
	public void initRecyclerView() {
		recyclerView = findViewById(R.id.user_history_recycler_view);
		FragmentManager fragmentManager = this.getFragmentManager();
		
		listAdapter = new UserHistoryListAdapter(userHistoryItems, fragmentManager);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		
		recyclerView.setAdapter(listAdapter);
		recyclerView.setLayoutManager(layoutManager);
	}
	
	@Override
	public void onComplete(String reviewText, double rating) {
		userHistoryItems.get(Constants.atomicHistoryPos.get()).setReview(reviewText);
		userHistoryItems.get(Constants.atomicHistoryPos.get()).setRating(rating);
				FirebaseDao.updateEntry(userHistoryItems.get(Constants.atomicHistoryPos.get()));
		listAdapter.updateData(userHistoryItems);
	}
}
