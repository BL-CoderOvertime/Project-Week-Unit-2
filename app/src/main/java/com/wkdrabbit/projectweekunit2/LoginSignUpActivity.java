package com.wkdrabbit.projectweekunit2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginSignUpActivity extends AppCompatActivity {
	
	private static final int RC_SIGN_IN = 3;
	private static String userUID = "";
	private static final String TAG = "LoginSignUpActivity";
	Context context;
	SignInButton button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login_sign_up);
		context = this;
		button = findViewById(R.id.btn_google_sign_in);
		
		Constants.setSharedPrefs(this);
		
		List<AuthUI.IdpConfig> providers = Arrays.asList(
				new AuthUI.IdpConfig.EmailBuilder().build());

// Create and launch sign-in intent
		startActivityForResult(
				AuthUI.getInstance()
						.createSignInIntentBuilder()
						.setAvailableProviders(providers)
						.build(),
				RC_SIGN_IN);
	}
	
	@Override
	protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == RC_SIGN_IN) {
			IdpResponse response = IdpResponse.fromResultIntent(data);
			
			if (resultCode == RESULT_OK) {
				// Successfully signed in
				FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
				userUID = user.getUid();
				FirebaseDao.setUserUid(userUID);
				
				/*new Thread(new Runnable() {
					@Override
					public void run() {
						Restaurant restaurant = ZomatoApiDao.getRestaurantList().get(2);
						ArrayList<MenuItem> menu = new ArrayList<>();
						
	*//*					menu.add(new MenuItem("FETAE34", restaurant.getId(), restaurant.getName(), "Potato Soup", 8.56,4, "MY REVIEW"));
						menu.add(new MenuItem("FETAE34", restaurant.getId(), restaurant.getName(), "Potato Soup", 8.56,4, "MY REVIEW"));
						menu.add(new MenuItem("FETAE34", restaurant.getId(), restaurant.getName(), "Potato Soup", 8.56,4, "MY REVIEW"));
						menu.add(new MenuItem("FETAE34", restaurant.getId(), restaurant.getName(), "Potato Soup", 8.56,4, "MY REVIEW"));
						menu.add(new MenuItem("FETAE34", restaurant.getId(), restaurant.getName(), "Potato Soup", 8.56,4, "MY REVIEW"));
						
						
						restaurant.setMenu(menu);*//*
	
					//	FirebaseDao.createRestaurantMenu(restaurant);
						
					//	menu = FirebaseDao.getRestaurantMenu(restaurant);
						
					}
				}).start();*/
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						FirebaseDao.createRestaurantMenu(ZomatoApiDao.getRestaurantList().get(2));
					}
				}).start();
				
				Intent intent = new Intent(this, RestaurantListActivity.class);
				startActivity(intent);
			
				
			} else {
				// Sign in failed. If response is null the user canceled the
				// sign-in flow using the back button. Otherwise check
				// response.getError().getErrorCode() and handle the error.
				// ...
			}
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == Constants.PERMISSIONS_REQUEST_LOCATION) {
			if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				getLocation();
			} else {
				//permission denied
			}
		}
	}
	
	private void getLocation() {
		//check again before using permission
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
		}
		
		FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
		locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
			@Override
			public void onSuccess(Location location) {
				
				final Location finalLocation = location;
				Constants.setLatLon(location.getLatitude(), location.getLongitude());
			}
		});
		
	}
}