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
import android.os.Bundle;
import android.widget.TextView;
/**
 * 
 * @author Camilo Segura
 *
 */
public class Result extends Activity {
	/**
	 * 
	 */
	private TextView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
			
		result = (TextView)findViewById(R.id.result);
		result.setText(Operaciones.getResult());
	}
}
