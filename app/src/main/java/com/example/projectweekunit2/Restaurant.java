package com.example.projectweekunit2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

public class Restaurant implements Parcelable {
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
	
	protected Restaurant(Parcel in) {
		id = in.readInt();
		location = in.readParcelable(LatLng.class.getClassLoader());
		name = in.readString();
		image = in.readParcelable(Bitmap.class.getClassLoader());
	}
	
	public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
		@Override
		public Restaurant createFromParcel(Parcel in) {
			return new Restaurant(in);
		}
		
		@Override
		public Restaurant[] newArray(int size) {
			return new Restaurant[size];
		}
	};
	
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
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeParcelable(location, flags);
		dest.writeString(name);
		dest.writeParcelable(image, flags);
	}
	
}
