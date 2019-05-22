package com.wkdrabbit.projectweekunit2;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


public class FirebaseDao {
	private static String UserUID = "";
	private static String TAG = "FirebaseDao";
	private static String BASE_URL = "https://foodmato-5bb8a.firebaseio.com/";
	private static String USER_URL = "https://foodmato-5bb8a.firebaseio.com/";
	private static String RESTAURANT_DATA_URL = BASE_URL + "restaurants/";
	private static String URL_ENDING = ".json";
	
	public static void setUserUid(String userUID) {
		UserUID = userUID;
		setUserUrl();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject body = new JSONObject("{\"user_data\": {\"id\":\"UserIdHere\",\"name\":\"UserNameHere\",\"history\": [ {\"id\": \"1\",\"name\": \"Tom\",\"restaurant_id\": \"restid\",\"restaurant_name\": \"Cruise\",\"timestamp\": \"63463464\",\"rating\": \"4\"},{\"id\": \"2\",\"name\": \"Tom1\",\"restaurant_id\": \"restid\",\"restaurant_name\": \"Cruise1\",\"timestamp\": \"63463464\",\"rating\": \"4\"},{\"id\": \"3\",\"name\": \"Tom2\",\"restaurant_id\": \"restid\",\"restaurant_name\": \"Cruise2\",\"timestamp\": \"63463464\",\"rating\": \"4\"}]}}");
					
					String result = NetworkAdapter.httpRequest(USER_URL + URL_ENDING, "POST", body, Constants.getHeaders());
					JSONObject jsonResult = new JSONObject(result);
					String userToken = jsonResult.getString("name");
				
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static void setUserUrl() {
		USER_URL = BASE_URL + UserUID;
	}
}
