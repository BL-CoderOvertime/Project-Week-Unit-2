package com.wkdrabbit.projectweekunit2;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuItem  implements Parcelable {
	private  int resturantId, totalRatings;
	private  double price, rating;
	private  String name, review, restaurantName, id;
	
	
	public MenuItem(String id, int resturantId, String restaurantName,  String name, double price, double rating, String review, int totalRatings){
		this.id = id;
		this.resturantId = resturantId;
		this.restaurantName = restaurantName;
		this.name = name;
		this.price = price;
		this.rating = rating;
		this.review = review;
		this.totalRatings = totalRatings;
	}
	
	protected MenuItem(Parcel in) {
		id = in.readString();
		price = in.readDouble();
		resturantId = in.readInt();
		rating = in.readDouble();
		name = in.readString();
		restaurantName = in.readString();
		review = in.readString();
		totalRatings = in.readInt();
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
	
	public double getRating() {
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
					.put("total_ratings", totalRatings)
					.put("review", review)
					.put("price", price)
					.put("timestamp", String.valueOf(System.currentTimeMillis()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonData;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(id);
		dest.writeDouble(price);
		dest.writeInt(resturantId);
		dest.writeDouble(rating);
		dest.writeString(name);
		dest.writeString(restaurantName);
		dest.writeString(review);
		dest.writeInt(totalRatings);
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public void setRating(double rating){
		this.rating = rating;
	}
	
	public int getTotalRatings() {
	return totalRatings;
	}
	
	public void setTotalRatings(int totalRatings) {
	this.totalRatings = totalRatings;
	}
}
