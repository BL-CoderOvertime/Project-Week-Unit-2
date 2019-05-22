package com.wkdrabbit.projectweekunit2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuItem  implements Parcelable {
	private final int id, resturantId, rating;
	private final double price;
	private final String name, review, restaurantName;
	
	
	public MenuItem(int id, int resturantId, String restaurantName,  String name, double price, int rating, String review){
		this.id = id;
		this.resturantId = resturantId;
		this.restaurantName = restaurantName;
		this.name = name;
		this.price = price;
		this.rating = rating;
		this.review = review;
	}
	
	protected MenuItem(Parcel in) {
		id = in.readInt();
		price = in.readDouble();
		resturantId = in.readInt();
		rating = in.readInt();
		name = in.readString();
		restaurantName = in.readString();
		review = in.readString();
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
	
	public double getPrice() {
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
	
	public JSONObject toJson() {
		JSONObject jsonData = new JSONObject();
		
		try {
			jsonData.put("id", id)
					.put("name", name)
					.put("restaurant_name", restaurantName)
					.put("restaurant_id", resturantId)
					.put("rating", rating)
					.put("review", review)
					.put("timestamp", String.valueOf(System.currentTimeMillis()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonData;
	}
		
		
		/*String jsonString = "{\"user_data\":{\"history\":[{\"id\":\"" +
				String.valueOf(id) + "\",\"name\":\"" +
				name + "\",\"restaurant_id\":\"" +
				String.valueOf(resturantId) + "\",\"restaurant_name\":\"" +
				restaurantName + "\",\"timestamp\":\"" +
				String.valueOf(System.currentTimeMillis()) + "\",\"rating\":\"" +
				String.valueOf(rating) + "\"}]}}";*/
		
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(id);
		dest.writeDouble(price);
		dest.writeInt(resturantId);
		dest.writeInt(rating);
		dest.writeString(name);
		dest.writeString(restaurantName);
		dest.writeString(review);
	}
}
