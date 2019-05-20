package com.example.projectweekunit2;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

public class Restaurant implements Serializable {
	private int id;
	private LatLng location;
	private String name;
	private ArrayList<MenuItem> menu;
	private URI imageUri;
	private Bitmap image;
	
	public Restaurant(int id, LatLng location, String name, ArrayList<MenuItem> menu, URI imageUri) {
		this.id = id;
		this.location = location;
		this.name = name;
		this.menu = menu;
		this.imageUri = imageUri;
	}
	
	public int getId() {
		return id;
	}
	
	public LatLng getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<MenuItem> getMenu() {
		return menu;
	}
	
	public URI getImageUri() {
		return imageUri;
	}
	
	public Bitmap getImage() {
		return image;
	}
}
