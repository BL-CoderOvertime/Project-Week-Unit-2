package com.wkdrabbit.projectweekunit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ZomatoApiDao {
	public static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
	//pass Search_url lat, long
	public static final String SEARCH_URL = BASE_URL  + "search?count=20&lat=%1$,.6f&lon=%1$,.6f&radius=500&sort=real_distance&order=asc";
	
	public static ArrayList<Restaurant> getRestaurantList(){
		ArrayList<Restaurant> restaurantResults = new ArrayList<>();
		String URL = BASE_URL + "search?count=20&lat="+ Constants.LAT +"&lon"+ Constants.LON + "&radius=500&sort=real_distance&order=asc";
		
		final String result = NetworkAdapter.httpRequest(URL, "GET", Constants.getHeaders(Constants.ZOMATO));
		try {
			JSONArray jsonArray = new JSONObject(result).getJSONArray("restaurants");
			
			for(int i = 0; i < jsonArray.length(); ++i) {
				restaurantResults.add(new Restaurant(Integer.parseInt(jsonArray.getJSONObject(i).getJSONObject("restaurant").getString("id")), jsonArray.getJSONObject(i).getJSONObject("restaurant").getString("name")));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurantResults;
	}
}
