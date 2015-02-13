/**
 * ChecksumCalculator
 * Copyright (C) 2015  Camilo Segura
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License,
 * with the "Linking Exception", which can be found at the license.txt
 * file in this program.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */
package co.camilosegura.checksumcalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
/**
 * 
 * @author Camilo Segura
 *
 */
public class DialogManager extends DialogFragment{
	/**
	 * 
	 * @param message
	 * @param url
	 * @return
	 */
	static DialogManager newInstance(int message, int url){
		DialogManager dialogManager = new DialogManager();		
		Bundle args = new Bundle();
		args.putInt("message", message);
		args.putInt("url", url);
		dialogManager.setArguments(args);		
		return dialogManager;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstance){
		final int messageId = getArguments().getInt("message");
		final int url = getArguments().getInt("url");
		return new AlertDialog.Builder(getActivity())
		.setMessage(messageId)
		.setNegativeButton(getString(R.string.close), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		})
		.setPositiveButton(getString(R.string.go), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(getString(url));
				Intent webPage = new Intent(Intent.ACTION_VIEW);
				webPage.setData(uri);
				startActivity(webPage);
			}
		})
		.create();
	}
	
}
