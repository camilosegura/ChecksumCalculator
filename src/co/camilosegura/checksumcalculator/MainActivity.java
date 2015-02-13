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

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Main Activity for this application
 * 
 * @author Camilo segura
 *
 */
public class MainActivity extends Activity implements OnClickListener{
	
	/**
	 * Reference to the IP button that launches 
	 * the input form for
	 * the Internet Protocol headers
	 */
	private Button edtIp;
	/**
	 * Reference to the ICMP button that launches 
	 * the input form for
	 * the Internet Control Message Protocol headers
	 */
	private Button edtIcmp; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        edtIcmp = (Button) findViewById(R.id.icmp);
        edtIp = (Button) findViewById(R.id.ip);
        
        edtIcmp.setOnClickListener(this);
        edtIp.setOnClickListener(this);
        
    }
    
    /**
     * Show selected activity    
     * @param form
     */
    public void showForm(Class<? extends Activity> form) {
    	Intent intent = new Intent(this, form);    	
    	startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(edtIcmp)){
			showForm(Icmp.class);
		}else if (v.equals(edtIp)) {
			showForm(Ip.class);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.help_general:
			this.displayDialog(R.string.help_general_text, R.string.help_general_url);
			break;
		case R.id.help_ip:
			this.displayDialog(R.string.help_ip_text, R.string.help_ip_url);
			break;
		case R.id.help_icmp:
			this.displayDialog(R.string.help_icmp_text, R.string.help_icmp_url);
			break;
		default:
			break;
		}
		
		return true;
	}
	/**
	 * 
	 * @param message
	 * @param url
	 */
	private void displayDialog(int message, int url){
		DialogFragment dialogFragment = DialogManager.newInstance(message, url);
		dialogFragment.show(getFragmentManager(), "help");
	}
}