package com.wkdrabbit.projectweekunit2;

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
		String URL = BASE_URL + "search?count=20&lat=" + Constants.LAT + "&lon" + Constants.LON + "&radius=500&sort=real_distance&order=asc";
		
		final String result = NetworkAdapter.httpRequest(URL, "GET", Constants.getHeaders(Constants.ZOMATO));
		try {
			JSONArray jsonArray = new JSONObject(result).getJSONArray("restaurants");
			
			for (int i = 0; i < jsonArray.length(); ++i) {
				restaurantResults.add(new Restaurant(Integer.parseInt(jsonArray.getJSONObject(i).getJSONObject("restaurant").getString("id")), jsonArray.getJSONObject(i).getJSONObject("restaurant").getString("name")));
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
