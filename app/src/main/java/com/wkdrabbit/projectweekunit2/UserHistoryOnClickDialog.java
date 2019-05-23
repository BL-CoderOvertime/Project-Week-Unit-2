package com.wkdrabbit.projectweekunit2;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class UserHistoryOnClickDialog extends DialogFragment {
	private EditText etUserHistoryReview;
	private OnCompleteListener mListener;
	private RatingBar mRatingBar;
	private TextView tvRestName,tvMenuItemName;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_dialog_user_history_fragment, null);
		etUserHistoryReview = view.findViewById(R.id.user_history_et_review_dialog);
		mRatingBar = view.findViewById(R.id.user_history_rating_bar_dialog);
		tvRestName = view.findViewById(R.id.user_history_tv_rest_name);
		tvMenuItemName = view.findViewById(R.id.user_history_tv_menuitem_name);
		
		
		etUserHistoryReview.setText(Constants.LAST_USER_HISTORY_ITEM.getReview());
		mRatingBar.setRating(Math.round(Constants.LAST_USER_HISTORY_ITEM.getRating()*2)/2);
		tvMenuItemName.setText(Constants.LAST_USER_HISTORY_ITEM.getMenuItemName());
		tvRestName.setText(Constants.LAST_USER_HISTORY_ITEM.getRestaurantName());
		
		builder.setView(view)
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				}).setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onComplete(etUserHistoryReview.getText().toString(), mRatingBar.getRating());
			}
		});
		
		
		
		return builder.create();
	}
	
	public interface OnCompleteListener {
		void onComplete(String reviewText, double rating);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.mListener = (OnCompleteListener)activity;
		} catch (final ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must Implement OnCompleteListener");
		}
	}
}
