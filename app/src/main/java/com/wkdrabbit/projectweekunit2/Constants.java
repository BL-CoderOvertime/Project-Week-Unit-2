package com.wkdrabbit.projectweekunit2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Constants {
	
	public static SharedPreferences prefs;
	public static SharedPreferences.Editor editor;
	public static final int PERMISSIONS_REQUEST_LOCATION = 3;
	public static final int FIREBASE_WRITE = 1;
	public static final int FIREBASE_READ = 2;
	public static final int ZOMATO = 3;
	public static int LAST_MENU_ITEM_POS = 0;
	public static double LAT = 35;
	public static double LON = -85;
	public static final String[] MENU_RATING_ONE_STAR = {"horrible", "worst", "pathetic"};
	public static final String[] MENU_RATING_TWO_STAR = {"bad", "not good"};
	public static final String[] MENU_RATING_THREE_STAR = {"mediocre", "boring"};
	public static final String[] MENU_RATING_FOUR_STAR = {"good", "tasty"};
	public static final String[] MENU_RATING_FIVE_STAR = {"amazing", "fantastic", "mind blowing", "awesome", "spectacular", "delicious", "great"};
	
	
	
	public static void setLatLon(double lat, double lon){
		LAT = lat;
		LON = lon;
	}
	
	public static Bitmap parseRatingToBitmap(int rating){
		//TODO: put logic here
		return null;
	}
	
	public static void setSharedPrefs(Context context){
		prefs = context.getSharedPreferences("MyPref", MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	//TODO: pull out userKey before implementing other DAOS
	public static Map<String, String> getHeaders(int type) {
		Map<String, String> params = new HashMap<>();
		switch(type){
			case FIREBASE_WRITE:
				params.put("Content-Type", "application/json");
				break;
			case FIREBASE_READ:
				break;
			case ZOMATO:
				params.put("Content-Type", "application/json");
				params.put("user-key", "d2f58e6f0bdd23c307abbdcb31605ffd");
				break;
		}
		return params;
	}
}
