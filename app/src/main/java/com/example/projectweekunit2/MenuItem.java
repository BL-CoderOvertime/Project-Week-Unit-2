package com.example.projectweekunit2;

public class MenuItem {
	private final int id, price, resturantId;
	private final String name;
	
	public MenuItem(int id, int resturantId, String name) {
		this(id,resturantId,name,0);
	}
	
	public MenuItem(int id, int resturantId, String name, int price) {
		this.id = id;
		this.resturantId = resturantId;
		this.name = name;
		this.price = price;
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
}
