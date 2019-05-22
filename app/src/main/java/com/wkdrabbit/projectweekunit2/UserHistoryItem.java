package com.wkdrabbit.projectweekunit2;

import java.util.concurrent.TimeUnit;

public class UserHistoryItem {
	
	private long timeLastEaten;
	private String id, restaurantName, menuItemName, review;
	private int rating, restaurantID;
	
	public UserHistoryItem(long timeLastEaten, String restaurantName, String menuItemName, int rating, String id, int restaurantId , String review) {
		this.timeLastEaten = timeLastEaten;
		this.restaurantName = restaurantName;
		this.menuItemName = menuItemName;
		this.rating = rating;
		this.review = review;
		this.id = id;
		this.restaurantID = restaurantId;
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
	
	public int getRating() {
		return rating;
	}
	
	public String getReview() {
		return review;
	}
	
	public String getId() {
		return id;
	}
	
	public int getRestaurantID() {
		return restaurantID;
	}
}

