package com.wkdrabbit.projectweekunit2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuItem  implements Parcelable {
	private  int resturantId, rating;
	private  double price;
	private  String name, review, restaurantName, id;
	
	
	public MenuItem(String id, int resturantId, String restaurantName,  String name, double price, int rating, String review){
		this.id = id;
		this.resturantId = resturantId;
		this.restaurantName = restaurantName;
		this.name = name;
		this.price = price;
		this.rating = rating;
		this.review = review;
	}
	
	protected MenuItem(Parcel in) {
		id = in.readString();
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
	
	public String getId() {
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
	
	public void setId(String id){
		this.id = id;
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
					.put("price", price)
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
		
		dest.writeString(id);
		dest.writeDouble(price);
		dest.writeInt(resturantId);
		dest.writeInt(rating);
		dest.writeString(name);
		dest.writeString(restaurantName);
		dest.writeString(review);
	}
	

}
