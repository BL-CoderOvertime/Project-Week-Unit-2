package com.example.projectweekunit2;

public class MenuItem {
	private final int id, price, resturantId, rating;
	private final String name;
	
	
	public MenuItem(int id, int resturantId, String name, int price, int rating) {
		this.id = id;
		this.resturantId = resturantId;
		this.name = name;
		this.price = price;
		this.rating = rating;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getResturantId() {
		return resturantId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRating() {
		return rating;
	}
}
