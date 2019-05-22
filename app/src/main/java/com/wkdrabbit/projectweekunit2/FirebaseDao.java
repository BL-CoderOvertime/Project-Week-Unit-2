package com.wkdrabbit.projectweekunit2;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class FirebaseDao {
	private static String UserUID = "";
	private static String TAG = "FirebaseDao";
	private static String BASE_URL = "https://foodmato-5bb8a.firebaseio.com/";
	private static String USER_URL = "";
	private static String MENU_URL = "";
	private static String RESTAURANT_DATA_URL = BASE_URL + "restaurants/";
	private static String URL_ENDING = ".json";
	private static String URL_HISTORY_ENTRY = "/user_data/history/";
	private static String UPDATE_ENTRY = "";
	private static String CREATE_URL = "";
	
	public static void setUserUid(String userUID) {
		UserUID = userUID;
		setURLs();
		
		
	}
	
	public static void setURLs() {
		USER_URL = BASE_URL + UserUID;
		MENU_URL = BASE_URL + UserUID + URL_HISTORY_ENTRY;
		UPDATE_ENTRY = MENU_URL + "%s" + URL_ENDING;
		CREATE_URL = MENU_URL + URL_ENDING;
	}
	
	/*public ArrayList<UserHistoryItem> getUserHistory(){
		String results = NetworkAdapter.httpRequest(USER_URL + URL_ENDING, "GET");
		USER_TOKEN = new JSONObject(results).getString("id");
	}*/
	
	
	
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
	
	
	/*
	public static void writeHistoryItemToFirebase(JSONObject body) {
		
		final JSONObject finalBody = body;
		new Thread(new Runnable() {
			JSONObject addedBody;
			
			@Override
			public void run() {
				String results = NetworkAdapter.httpRequest(USER_URL + URL_HISTORY_ENTRY + URL_ENDING, "GET", Constants.getHeaders(Constants.FIREBASE_READ));
				try {
					JSONArray jsonArray = new JSONArray(new JSONObject(results).getJSONArray("history"));
					jsonArray.put(finalBody);
					addedBody = new JSONObject().put("history", jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				if (addedBody == null) {
					addedBody = finalBody;
				}
				
				NetworkAdapter.httpRequest(USER_URL + URL_HISTORY_ENTRY + URL_ENDING, "PUT", addedBody, Constants.getHeaders(Constants.FIREBASE_WRITE));
			}
		}).start();
	}
	*/
	
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
	
	
	
	public static void updateEntry(final MenuItem menuItem) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				String result = NetworkAdapter.httpRequest(
						String.format(UPDATE_ENTRY, menuItem.getId()),
						NetworkAdapter.PUT,
						menuItem.toJson(),
						Constants.getHeaders(Constants.FIREBASE_WRITE));
				
				// could check result for successful update
			}
		}).start();
	}
	
}