package com.jay.android.fragmentforhost;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class SexDialog {
	public Dialog showDialog(Context context) {
		final Dialog dialog = new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.dialog_sex);
		dialog.show();
		dialog.findViewById(R.id.tv_dlg_sex_cancel).setOnClickListener(
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		return dialog;
	}
}
