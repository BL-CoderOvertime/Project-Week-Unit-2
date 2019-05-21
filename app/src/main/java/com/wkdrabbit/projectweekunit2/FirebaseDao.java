package com.wkdrabbit.projectweekunit2;

public class FirebaseDao {
	private static String UserUID = "";
	private static final String TAG = "FirebaseDao";
	private static final String BASE_URL = "https://foodmato.firebaseio.com/";
	
	
	
	public static void setUserUid(String userUID) {
		UserUID = userUID;
	}
}
