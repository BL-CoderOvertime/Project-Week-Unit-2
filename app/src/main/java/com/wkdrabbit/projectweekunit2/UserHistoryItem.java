package com.wkdrabbit.projectweekunit2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class UserHistoryItem {
	
	private long timeLastEaten;
	private String id, restaurantName, menuItemName, review;
	private int restaurantId;
	double  rating;
	
	public UserHistoryItem(long timeLastEaten, String restaurantName, String menuItemName, int rating, String id, int restaurantId , String review) {
		this.timeLastEaten = timeLastEaten;
		this.restaurantName = restaurantName;
		this.menuItemName = menuItemName;
		this.rating = rating;
		this.review = review;
		this.id = id;
		this.restaurantId = restaurantId;
	}
	
	public long getTimeLastEaten() {
		return TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - timeLastEaten);
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public String getMenuItemName() {
		return menuItemName;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String getReview() {
		return review;
	}
	
	public String getId() {
		return id;
	}
	
	public JSONObject toJson(){
		JSONObject jsonData = new JSONObject();
		
		try {
			jsonData.put("id", id)
					.put("name", menuItemName)
					.put("restaurant_name", restaurantName)
					.put("restaurant_id", restaurantId)
					.put("rating", rating)
					.put("review", review)
					.put("timestamp", timeLastEaten);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonData;
	}
	
	public int getRestaurantID() {
		return restaurantId;
	}
	
	public void setReview(String reviewText) {
	this.review = reviewText;
	}
	
	public void setRating(double rating) {
	this.rating = rating;
	}
}

