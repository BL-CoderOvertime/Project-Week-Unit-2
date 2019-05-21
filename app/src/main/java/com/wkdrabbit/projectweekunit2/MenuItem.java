package com.wkdrabbit.projectweekunit2;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem  implements Parcelable {
	private final int id, price, resturantId, rating;
	private final String name;
	
	
	public MenuItem(int id, int resturantId, String name, int price, int rating){
		this.id = id;
		this.resturantId = resturantId;
		this.name = name;
		this.price = price;
		this.rating = rating;
	}
	
	protected MenuItem(Parcel in) {
		id = in.readInt();
		price = in.readInt();
		resturantId = in.readInt();
		rating = in.readInt();
		name = in.readString();
	}
	
	public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
		@Override
		public MenuItem createFromParcel(Parcel in) {
			return new MenuItem(in);
		}
		
		@Override
		public MenuItem[] newArray(int size) {
			return new MenuItem[size];
		}
	};
	
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
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(id);
		dest.writeInt(price);
		dest.writeInt(resturantId);
		dest.writeInt(rating);
		dest.writeString(name);
	}
}
