package com.example.projectweekunit2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
	
	ArrayList<Restaurant> restaurants;
	
	public RestaurantListAdapter(ArrayList<Restaurant> restaurants){
		this.restaurants = restaurants;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		TextView tvRestaurantName, tvDistanceTo;
		ImageView ivRestaurantLogo;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvRestaurantName = itemView.findViewById(R.id.restaurant_list_name);
			tvDistanceTo = itemView.findViewById(R.id.restaurant_list_distance);
			ivRestaurantLogo = itemView.findViewById(R.id.restaurant_logo);
		}
	}
	@NonNull
	@Override
	public RestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_content, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder viewHolder, int i) {
		final Restaurant data = restaurants.get(i);
		//TODO: setup method for calculating the distance to restaurant
		//viewHolder.tvDistanceTo.setText(data.getDistanceTo());
		viewHolder.tvRestaurantName.setText(data.getName());
		viewHolder.ivRestaurantLogo.setImageBitmap(data.getImage());
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MenuItemListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("restaurant_key", data);
				intent.putExtras(bundle);
				v.getContext().startActivity(intent);
			}
		});
		
	}
	
	@Override
	public int getItemCount() {
		return restaurants.size();
	}
}
