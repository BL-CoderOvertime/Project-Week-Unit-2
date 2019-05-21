package com.example.projectweekunit2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserHistoryListAdapter extends RecyclerView.Adapter<UserHistoryListAdapter.ViewHolder> {
	
	ArrayList<UserHistoryItem> userHistoryItems;
	
	public UserHistoryListAdapter(ArrayList<UserHistoryItem> data){
		userHistoryItems = data;
	}
	
	
	class ViewHolder extends RecyclerView.ViewHolder{
		TextView tvRestaurantName, tvMenuItemName, tvLastEaten;
		ImageView ivRating;
		
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		tvLastEaten = itemView.findViewById(R.id.user_history_list_time);
		tvMenuItemName = itemView.findViewById(R.id.user_history_item_name);
		tvRestaurantName = itemView.findViewById(R.id.user_history_restaurant_name);
		//ivRating = itemView.findViewById(R.id.user_review);
	}
}
	
	
	
	@NonNull
	@Override
	public UserHistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_list_content, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(@NonNull UserHistoryListAdapter.ViewHolder viewHolder, int i) {
		final UserHistoryItem data = userHistoryItems.get(i);
		
		viewHolder.tvRestaurantName.setText(data.getRestaurantName());
		viewHolder.tvMenuItemName.setText(data.getMenuItemName());
		viewHolder.tvLastEaten.setText(String.valueOf(data.getTimeLastEaten()) + " Days");
		//viewHolder.ivRating.setImageBitmap(Constants.parseRatingToBitmap(data.getRating()));
		
		//TODO: possibly set up onClickListeners for some unknown functionality
	}
	
	@Override
	public int getItemCount() {
		return userHistoryItems.size();
	}
}
