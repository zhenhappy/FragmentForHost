package com.jay.android.fragmentforhost;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class AgeDialog {
	public Dialog showDialog(Context context) {
		final Dialog dialog = new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.dialog_age);
		dialog.show();
		dialog.findViewById(R.id.tv_dlg_age_cancel).setOnClickListener(
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		return dialog;
	}
}
