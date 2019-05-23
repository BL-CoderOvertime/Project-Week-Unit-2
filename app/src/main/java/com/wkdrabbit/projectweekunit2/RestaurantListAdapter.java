package com.wkdrabbit.projectweekunit2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
	public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder viewHolder, final int i) {
		final Restaurant data = restaurants.get(i);
		//TODO: setup method for calculating the distance to restaurant
		//viewHolder.tvDistanceTo.setText(data.getDistanceTo());
		viewHolder.tvRestaurantName.setText(data.getName());
		//viewHolder.ivRestaurantLogo.setImageBitmap(data.getImage());
		viewHolder.tvDistanceTo.setText(String.valueOf(Math.round(data.getDistanceTo())) + "m");
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						restaurants.set(i,FirebaseDao.pullRestaurantFromFireBase(restaurants.get(i)));
						Intent intent = new Intent(v.getContext(), MenuItemListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putParcelable("full_restaurant_key", restaurants.get(i));
						bundle.putParcelableArrayList("restaurant_key", data.getMenu());
						intent.putExtra("bundle_key",bundle);
						v.getContext().startActivity(intent);
					}
				}).start();

			}
		});
		
	}
	
	@Override
	public int getItemCount() {
		return restaurants.size();
	}
}
