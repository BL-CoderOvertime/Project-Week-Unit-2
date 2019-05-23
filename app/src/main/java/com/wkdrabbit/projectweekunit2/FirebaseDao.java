package com.wkdrabbit.projectweekunit2;

import org.json.JSONArray;
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
	private static final String RESTAURANT_GET_ALL_URL = RESTAURANT_CREATE_DATA_URL;
	
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
				updateRestaurant(restaurant);
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
	
	
	public static void updateRestaurant(final Restaurant restaurant) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				NetworkAdapter.httpRequest(
						String.format(RESTAURANT_UPDATE_DATA_URL, restaurant.getFbId()),
						NetworkAdapter.PUT,
						restaurant.toJson(),
						Constants.getHeaders(Constants.FIREBASE_WRITE));
				
			}
		}).start();
	}
	
	public static Restaurant pullRestaurantFromFireBase(Restaurant restaurant) {
		
		//String id, int resturantId, String restaurantName,  String name, double price, int rating, String review
		
		boolean hasBeenFound = false;
		String FbId = "";
		String name = "";
		int id = 0;
		int restaurantId = 0;
		int rating = 0;
		
		try {
			String results = "";
			results = NetworkAdapter.httpRequest(RESTAURANT_GET_ALL_URL, "GET", Constants.getHeaders(Constants.FIREBASE_READ));
			
			
			JSONObject topJson = new JSONObject(results);
			for (Iterator<String> it = topJson.keys(); it.hasNext(); ) {
				String key = it.next();
				try {
					final JSONObject jsonObject = topJson.getJSONObject(key);
					FbId = key;
					
					try {
						id = jsonObject.getInt("id");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					if(id == restaurant.getId()){
						
						hasBeenFound = true;
						ArrayList<MenuItem> menuToAdd = new ArrayList<>();
						restaurant.setFbId(key);
						JSONArray menu = jsonObject.getJSONArray("menu");
						
						for(int i = 0; i < menu.length(); ++i){
							
							String menuId = "";
							String menuName = "";
							double price = 0;
							int menuRating = 0;
							int menuRestaurantId = 0;
							String menuRestaurantName = "";
							
							JSONObject menuItem = menu.getJSONObject(i);
							
							try {
								menuId = menuItem.getString("id");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							try {
								menuName = menuItem.getString("name");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							try {
								price = menuItem.getDouble("price");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							try {
								menuRating = menuItem.getInt("rating");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							try {
								menuRestaurantId = menuItem.getInt("restaurant_id");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							try {
								menuRestaurantName = menuItem.getString("restaurant_name");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							
							menuToAdd.add(new MenuItem(menuId, menuRestaurantId , menuRestaurantName, menuName, price, menuRating, ""));
						}
						restaurant.setMenu(menuToAdd);
						
						return restaurant;
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(!hasBeenFound){
			createRestaurantMenu(restaurant);}
				/*try {
					final JSONObject jsonObject = topJson.getJSONObject(i);
					try {
						id = jsonObject.getString("id");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						price = Double.parseDouble(jsonObject.getString("price"));
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
					
					menuItems.add(new MenuItem(id, restaurantId, restaurantName, name, price, rating, review));
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restaurant;
	}
}