package com.wkdrabbit.projectweekunit2;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wkdrabbit.projectweekunit2.util.DiffUtilCallbackMenuItem;

import java.util.ArrayList;

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.ViewHolder> implements MenuItemOnClickDialog.OnCompleteAddHistoryListener {
	
	ArrayList<MenuItem> menuItems;
	Activity activity;
	FragmentManager fragmentManager;
	int lastPos = 0;
	
	public MenuItemListAdapter(ArrayList<MenuItem> menuItems, Activity activity, FragmentManager fragmentManager) {
		this.menuItems = menuItems;
		this.activity = activity;
		this.fragmentManager = fragmentManager;
	}
	
	
	public void insertData(ArrayList<MenuItem> insertList){
		DiffUtilCallbackMenuItem diffUtilCallbackMenuItem = new DiffUtilCallbackMenuItem(menuItems, insertList);
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallbackMenuItem);
		
		menuItems.addAll(insertList);
		diffResult.dispatchUpdatesTo(this);
	}
	
	public void updateData(ArrayList<MenuItem> newList){
		DiffUtilCallbackMenuItem diffUtilCallbackMenuItem = new DiffUtilCallbackMenuItem(menuItems, newList);
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallbackMenuItem);
		
		menuItems.clear();
		menuItems.addAll(newList);
		diffResult.dispatchUpdatesTo(this);
	}
	
	@Override
	public void onComplete(String review, int rating) {
		menuItems.get(lastPos).setReview(review);
		menuItems.get(lastPos).setRating(rating);
		
		FirebaseDao.createEntry(menuItems.get(Constants.LAST_MENU_ITEM_POS));
		
		Intent favIntent = new Intent(activity.getApplicationContext(), UserHistoryActivity.class);
		activity.getApplicationContext().startActivity(favIntent);
	}
	
	class ViewHolder extends RecyclerView.ViewHolder {
		
		TextView tvMenuItemName, tvPrice;
		RatingBar ratingBar;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvMenuItemName = itemView.findViewById(R.id.menu_list_name);
			//tvPrice = itemView.findViewById(R.id.menu_list_price);
			ratingBar = itemView.findViewById(R.id.menu_list_rating);
			//TODO: get handle for imageView rating
		}
		
	}
	
	@NonNull
	@Override
	public MenuItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_content, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(@NonNull MenuItemListAdapter.ViewHolder viewHolder, int i) {
		Constants.LAST_MENU_ITEM_POS = i;
		
		MenuItem data = menuItems.get(i);
		//viewHolder.tvPrice.setText(String.valueOf(data.getPrice()));
		viewHolder.tvMenuItemName.setText(data.getName());
		viewHolder.ratingBar.setRating(Math.round(data.getRating()));
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}
	public void showDialog(){
		MenuItemOnClickDialog dialog = new MenuItemOnClickDialog();
		dialog.show(fragmentManager, "Dialog");
	}
	
	@Override
	public int getItemCount() {
		return menuItems.size();
	}
}
