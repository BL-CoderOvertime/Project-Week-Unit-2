package com.wkdrabbit.projectweekunit2;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ZomatoApiDao {
	public static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
	public static final String REVIEW_URL = BASE_URL + "reviews?res_id=%d&count=1000";
	
	public static ArrayList<Restaurant> getRestaurantList() {
		ArrayList<Restaurant> restaurantResults = new ArrayList<>();
		
		while(Constants.LAT == 0 && Constants.LON == 0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String URL = BASE_URL + "search?count=20&lat=" + Constants.LAT + "&lon=" + Constants.LON + "&radius=500&sort=real_distance&order=asc";
		
		int id = 0;
		float distanceTo;
		double lat, lon = 0;
		String name = "";
		Location userLoc = new Location("");
		userLoc.setLongitude(Constants.LON);
		userLoc.setLatitude(Constants.LAT);
		
		Location restLoc = new Location("");
		
		final String result = NetworkAdapter.httpRequest(URL, "GET", Constants.getHeaders(Constants.ZOMATO));
		try {
			JSONArray jsonArray = new JSONObject(result).getJSONArray("restaurants");
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject jsonRest = jsonArray.getJSONObject(i).getJSONObject("restaurant");
				restLoc.setLatitude(Double.parseDouble(jsonRest.getJSONObject("location").getString("latitude")));
				restLoc.setLongitude(Double.parseDouble(jsonRest.getJSONObject("location").getString("longitude")));
				distanceTo = userLoc.distanceTo(restLoc);
				name = jsonRest.getString("name");
				id = Integer.parseInt(jsonRest.getString("id"));
				
				restaurantResults.add(new Restaurant(id, name, distanceTo));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurantResults;
	}
	
	public static ArrayList<Review> getReviews(Restaurant restaurant) {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String URL = String.format(REVIEW_URL, restaurant.getId());
		final String result = NetworkAdapter.httpRequest(URL, "GET", Constants.getHeaders(Constants.ZOMATO));
		try {
			JSONArray jsonArray = new JSONObject(result).getJSONArray("user_reviews");
			
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject jsonReviewObject = jsonArray.getJSONObject(i).getJSONObject("review");
				
				reviews.add(new Review(jsonReviewObject.getString("review_text"), jsonReviewObject.getInt("rating")));
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return reviews;
	}
}
