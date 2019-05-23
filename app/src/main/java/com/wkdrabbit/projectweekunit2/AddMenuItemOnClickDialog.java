package com.wkdrabbit.projectweekunit2;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AddMenuItemOnClickDialog extends DialogFragment {
	
	private TextView tvMenuItemName;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_dialog_fragment, null);
		
		builder.setView(view)
				.setTitle("Menu Title Here")
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				})
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//TODO: setup on confirm
					}
				});
		tvMenuItemName = view.findViewById(R.id.tv_menu_item_name);
		return builder.create();
	}
	
}
