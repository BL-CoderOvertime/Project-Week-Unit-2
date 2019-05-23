package com.wkdrabbit.projectweekunit2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable {
	private int id;
	private String name, fbId;
	private ArrayList<MenuItem> menu = new ArrayList<>();
	private URI imageUri;
	private Bitmap image;
	
	public Restaurant(String fbId, int id, String name, ArrayList<MenuItem> menu, URI imageUri) {
		this.id = id;
		this.name = name;
		this.menu = menu;
		this.imageUri = imageUri;
		
		menu = new ArrayList<>();
	}
	
	public Restaurant(int id, String name, ArrayList<MenuItem> menu, URI imageUri) {
		this("default", id, name, menu, imageUri);
	}
	
	public Restaurant(int id, String name, ArrayList<MenuItem> menu) {
		this("default", id, name, menu, null);
	}
	
	public Restaurant(int id, String name) {
		this(id, name, new ArrayList<MenuItem>(), null);
	}
	
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
	public String getFbId() {
		return fbId;
	}
	
	protected Restaurant(Parcel in) {
		fbId = in.readString();
		id = in.readInt();
		name = in.readString();
		//menu = in.readList(menu, List.class.getClassLoader());
		image = in.readParcelable(Bitmap.class.getClassLoader());
	}
	
	public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
		@Override
		public Restaurant createFromParcel(Parcel in) {
			return new Restaurant(in);
		}
		
		@Override
		public Restaurant[] newArray(int size) {
			return new Restaurant[size];
		}
	};
	
	public int getId() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
	
	public ArrayList<MenuItem> getMenu() {
		return menu;
	}
	
	public URI getImageUri() {
		return imageUri;
	}
	
	public Bitmap getImage() {
		return image;
	}
	
	public JSONObject toJson() {
		JSONArray menuArr = new JSONArray();
		
		for (int i = 0; i < menu.size(); ++i) {
			menuArr.put(menu.get(i).toJson());
		}
		JSONObject jsonBase = new JSONObject();
		try {
			jsonBase.put("name", name);
			jsonBase.put("id", id);
			jsonBase.put("fb_id", fbId);
			jsonBase.put("image_uri", imageUri);
			jsonBase.put("menu", menuArr);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonBase;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public void setMenuFromFB() {
		//menu = FirebaseDao.getRestaurantMenu(this);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(fbId);
		dest.writeInt(id);
		dest.writeString(name);
		//dest.writeList(menu);
		dest.writeParcelable(image, flags);
		
	}
	
	public void addToMenu(MenuItem menuItem){
		
		menu.add(menuItem);
		final Restaurant finRestaurant = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				FirebaseDao.updateRestaurant(finRestaurant);
			}
		}).start();
	}
	
	public void setMenu(ArrayList<MenuItem> menuItems) {
		menu.clear();
		menu.addAll(menuItems);
	}
}
