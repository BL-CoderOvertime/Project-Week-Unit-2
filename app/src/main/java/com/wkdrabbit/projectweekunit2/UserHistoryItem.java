package com.wkdrabbit.projectweekunit2;

import java.util.concurrent.TimeUnit;

public class UserHistoryItem {
	
	private long timeLastEaten;
	private String restaurantName, menuItemName;
	private int rating;
	
	public UserHistoryItem(long timeLastEaten, String restaurantName, String menuItemName, int rating) {
		this.timeLastEaten = timeLastEaten;
		this.restaurantName = restaurantName;
		this.menuItemName = menuItemName;
		this.rating = rating;
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
}

