package com.wkdrabbit.projectweekunit2;




import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class MenuItemOnClickDialog extends DialogFragment {
	
	private EditText etReview;
	private RatingBar ratingBar;
	private OnCompleteAddHistoryListener mListener;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_dialog_fragment, null);
		etReview = view.findViewById(R.id.et_review);
		ratingBar = view.findViewById(R.id.rating_bar_dialog);
		ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			
			}
		});
		builder.setView(view)
				.setTitle("Add To Favorites")
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				})
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mListener.onComplete(etReview.getText().toString(), ratingBar.getProgress());
					}
				});
		
		return builder.create();
	}
	
	public interface OnCompleteAddHistoryListener {
		void onComplete(String review, int rating);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.mListener = (OnCompleteAddHistoryListener)activity;
		} catch (final ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must Implement OnCompleteListener");
		}
	}
	
}
