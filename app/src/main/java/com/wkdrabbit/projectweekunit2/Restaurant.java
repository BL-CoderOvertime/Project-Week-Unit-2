package com.wkdrabbit.projectweekunit2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.util.ArrayList;

public class Restaurant implements Parcelable {
	private int id;
	private String name;
	private ArrayList<MenuItem> menu;
	private URI imageUri;
	private Bitmap image;
	
	public Restaurant(int id, String name, ArrayList<MenuItem> menu, URI imageUri) {
		this.id = id;
		this.name = name;
		this.menu = menu;
		this.imageUri = imageUri;
	}
	
	public Restaurant(int id, String name){
		this(id,name, new ArrayList<MenuItem>(), null);
	}
	
	protected Restaurant(Parcel in) {
		id = in.readInt();
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
		dest.writeString(name);
		dest.writeParcelable(image, flags);
	}
	
}
