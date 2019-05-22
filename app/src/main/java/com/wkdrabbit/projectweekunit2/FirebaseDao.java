package com.wkdrabbit.projectweekunit2;

import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class FirebaseDao {
	private static String UserUID = "";
	private static String TAG = "FirebaseDao";
	private static String BASE_URL_USER = "https://foodmato-5bb8a.firebaseio.com/";
	private static String BASE_URL_RESTAURANT = "https://foodmato-5bb8a-c6907-restaurant.firebaseio.com/";
	private static String USER_URL = "";
	private static String MENU_URL = "";
	private static String URL_ENDING = ".json";
	private static String RESTAURANT_CREATE_DATA_URL = BASE_URL_RESTAURANT + "restaurants/" + URL_ENDING;
	private static String RESTAURANT_UPDATE_DATA_URL = BASE_URL_RESTAURANT + "restaurants/" + "%s" + URL_ENDING;
	private static String URL_HISTORY_ENTRY = "/user_data/history/";
	private static String UPDATE_ENTRY = "";
	private static String CREATE_URL = "";
	
	//TODO: Create FirebaseData base and add data that way instead of network adapter. allows for setting custom IDs for objects.
	
	public static void setUserUid(String userUID) {
		UserUID = userUID;
		setURLs();
		
		
	}
	
	public static void setURLs() {
		USER_URL = BASE_URL_USER + UserUID;
		MENU_URL = BASE_URL_USER + UserUID + URL_HISTORY_ENTRY;
		UPDATE_ENTRY = MENU_URL + "%s" + URL_ENDING;
		CREATE_URL = MENU_URL + URL_ENDING;
	}
	
	public static void createRestaurantMenu(final Restaurant restaurant){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = NetworkAdapter.httpRequest(
						String.format(RESTAURANT_CREATE_DATA_URL, restaurant.getFbId()),
						NetworkAdapter.POST,
						restaurant.toJson(),
						Constants.getHeaders(Constants.FIREBASE_WRITE));
				try {
					restaurant.setFbId(new JSONObject(result).getString("name"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	public static void createEntry(final MenuItem menuItem) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = NetworkAdapter.httpRequest(
						String.format(CREATE_URL, menuItem.getId()),
						NetworkAdapter.POST,
						menuItem.toJson(),
						Constants.getHeaders(Constants.FIREBASE_WRITE));
				try {
					menuItem.setId(new JSONObject(result).getString("name"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	public static ArrayList<UserHistoryItem> getUserHistory() {
		ArrayList<UserHistoryItem> userHistoryItems = new ArrayList<>();
		long timestamp = 0;
		String name = "";
		String restaurantName = "";
		String review = "";
		String id = "";
		int rating = 0;
		int restaurantId = 0;
		
		try {
			String results = "";
			results = NetworkAdapter.httpRequest(USER_URL + URL_HISTORY_ENTRY + URL_ENDING, "GET", Constants.getHeaders(Constants.FIREBASE_READ));
			
			JSONObject topJson = new JSONObject(results);
			for (Iterator<String> it = topJson.keys(); it.hasNext(); ) {
				String key = it.next();
				try {
					final JSONObject jsonObject = topJson.getJSONObject(key);
					id = key;
					try {
						timestamp = Long.parseLong(jsonObject.getString("timestamp"));
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					try {
						name = jsonObject.getString("name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						rating = jsonObject.getInt("rating");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						review = jsonObject.getString("review");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						restaurantId = jsonObject.getInt("restaurant_id");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					try {
						restaurantName = jsonObject.getString("restaurant_name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					userHistoryItems.add(new UserHistoryItem(timestamp, restaurantName, name, rating, id, restaurantId, review));
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userHistoryItems;
	}
	
	
	public static void updateEntry(final UserHistoryItem userHistoryItem) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				NetworkAdapter.httpRequest(
						String.format(UPDATE_ENTRY, userHistoryItem.getId()),
						NetworkAdapter.PUT,
						userHistoryItem.toJson(),
						Constants.getHeaders(Constants.FIREBASE_WRITE));
				
			}
		}).start();
	}
	
	public static ArrayList<MenuItem> getRestaurantMenu(Restaurant restaurant) {
		return null;
	}
}