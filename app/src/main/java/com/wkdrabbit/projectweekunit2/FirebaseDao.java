package com.wkdrabbit.projectweekunit2;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


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
		
	
	}
	
	public static void setUserUrl() {
		//USER_URL = BASE_URL + UserUID;
	}
	
	/*public ArrayList<UserHistoryItem> getUserHistory(){
		String results = NetworkAdapter.httpRequest(USER_URL + URL_ENDING, "GET");
		USER_TOKEN = new JSONObject(results).getString("id");
	}*/
	
	
	public static void writeToFirebase(MenuItem menuItem){
		writeToFirebase(menuItem.toJson());
	}
	
	public static void writeToFirebase(JSONObject body){
		
		final JSONObject finalBody = body;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String results = "";
					String userToken = Constants.prefs.getString("user_token", "default");
					if(userToken.equals("default")){
						NetworkAdapter.httpRequest(USER_URL + URL_ENDING, "POST", finalBody, Constants.getHeaders(Constants.FIREBASE_WRITE));
					} else{results = NetworkAdapter.httpRequest(USER_URL + userToken + URL_ENDING);
						JSONObject jsonResult = new JSONObject(results);
						userToken = jsonResult.getString("name");
						Constants.editor.putString("user_token", userToken);
						Constants.editor.commit();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
}
