package com.example.projectweekunit2;

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
	public static double LAT = 0;
	public static double LON = 0;
	
	
	
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
	public static Map<String, String> getHeaders() {
		Map<String, String> params = new HashMap<>();
		params.put("user-key", "d2f58e6f0bdd23c307abbdcb31605ffd");
		params.put("Content-Type", "application/json");
		return params;
	}
}
