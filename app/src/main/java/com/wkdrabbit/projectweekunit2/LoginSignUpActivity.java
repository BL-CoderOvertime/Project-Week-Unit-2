package com.wkdrabbit.projectweekunit2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginSignUpActivity extends AppCompatActivity {
	
	private static final int RC_SIGN_IN = 3;
	private static String userUID = "";
	private static final String TAG = "LoginSignUpActivity";
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_sign_up);
		context = this;
		Constants.setSharedPrefs(this);
		
		//Gets Fine Location Permission
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSIONS_REQUEST_LOCATION);
		} else {
			setLocation();
		}
		signIn();
	}
	
	public void signIn() {
		
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
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(this, RestaurantListActivity.class);
				startActivity(intent);
				
				
			} else {
				// Sign in failed. If response is null the user canceled the
			}
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == Constants.PERMISSIONS_REQUEST_LOCATION) {
			if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
						return;
					}
				}
				
				FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
				locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
					@Override
					public void onSuccess(Location location) {
						Constants.setLatLon(location.getLatitude(), location.getLongitude());
					}
				});
				
			} else {
				//permission denied
			}
		}
	}
	
	private void setLocation() {
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
				Constants.setLatLon(location.getLatitude(), location.getLongitude());
			}
		});
		
	}
}