package com.wkdrabbit.projectweekunit2;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuItemAddDialogFragment extends DialogFragment {
	
	private EditText etMenuItemName;
	private OnCompleteListener mListener;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_add_menuitem_dialog_fragment, null);
		etMenuItemName = view.findViewById(R.id.et_menu_item_name);
		
		
		builder.setView(view)
				.setTitle("Add Menu Item").setNeutralButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onComplete("DeleteThisItem");
			}
		})
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				}).setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onComplete(etMenuItemName.getText().toString());
			}
		});
		
	
		
		return builder.create();
	}
	
	public interface OnCompleteListener {
		void onComplete(String menuItemName);
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
