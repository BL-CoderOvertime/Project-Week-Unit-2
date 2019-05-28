package com.wkdrabbit.projectweekunit2;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wkdrabbit.projectweekunit2.util.DiffUtilCallBackUserHistoryItem;

import java.util.ArrayList;

public class UserHistoryListAdapter extends RecyclerView.Adapter<UserHistoryListAdapter.ViewHolder> {
	FragmentManager fragmentManager;
	ArrayList<UserHistoryItem> userHistoryItems;
	
	public UserHistoryListAdapter(ArrayList<UserHistoryItem> data, FragmentManager newFragmentManager){
		userHistoryItems = data;
		fragmentManager = newFragmentManager;
	}
	
	
	class ViewHolder extends RecyclerView.ViewHolder{
		TextView tvRestaurantName, tvMenuItemName, tvLastEaten;
		RatingBar ratingBar;
		
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		tvLastEaten = itemView.findViewById(R.id.user_history_list_time);
		tvMenuItemName = itemView.findViewById(R.id.user_history_item_name);
		tvRestaurantName = itemView.findViewById(R.id.user_history_restaurant_name);
		ratingBar = itemView.findViewById(R.id.user_history_rating_bar);
	}
}

	public void insertData(ArrayList<UserHistoryItem> insertList){
		DiffUtilCallBackUserHistoryItem diffUtilCallBackUserHistoryItem = new DiffUtilCallBackUserHistoryItem(userHistoryItems, insertList);
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallBackUserHistoryItem);
		
		userHistoryItems.addAll(insertList);
		diffResult.dispatchUpdatesTo(this);
	}
	
	public void updateData(ArrayList<UserHistoryItem> newList){
		DiffUtilCallBackUserHistoryItem diffUtilCallBackHistoryItem = new DiffUtilCallBackUserHistoryItem(userHistoryItems, newList);
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallBackHistoryItem);

		userHistoryItems = newList;
		
		this.notifyDataSetChanged();
		diffResult.dispatchUpdatesTo(this);
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
		final int pos = i;
		
		viewHolder.ratingBar.setRating((float)data.getRating());
		viewHolder.tvRestaurantName.setText(data.getRestaurantName());
		viewHolder.tvMenuItemName.setText(data.getMenuItemName());
		viewHolder.tvLastEaten.setText(String.valueOf(data.getTimeLastEaten()) + " Days");
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Constants.atomicHistoryPos.set(pos);
				Constants.LAST_USER_HISTORY_ITEM = userHistoryItems.get(pos);
				showDialog();
			}
		});
	}
	
	public void showDialog(){
		UserHistoryOnClickDialog dialog = new UserHistoryOnClickDialog();
		dialog.show(fragmentManager, "Dialog");
	}
	
	@Override
	public int getItemCount() {
		return userHistoryItems.size();
	}
}
