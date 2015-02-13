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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author Camilo Segura
 *
 */
 
public class Icmp extends Activity{

	/**
	 * 
	 */
	private TextView type;
	/**
	 * 
	 */
	private TextView code;
	/**
	 * 
	 */
	private TextView rest;
	/**
	 * 
	 */
	int mask = Integer.parseInt("00000000000000001111111111111111", 2);
	/**
	 * 
	 */
	private String[] fields = new String[3];
	/**
	 * 
	 */
	private String[] fieldNames = {"type", "code", "rest"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.icmp);
		type = (TextView)findViewById(R.id.type);
		code = (TextView)findViewById(R.id.code);
		rest = (TextView)findViewById(R.id.rest);
	}
	/**
	 * 
	 * @param view
	 */
	public void calculateIcmp(View view) {
		int primeraFila = 0;
		int sumaSiguientes = 0;

		getValues();
		Operaciones.setMensaje("");
		Operaciones.validateIcmp(fields, fieldNames);
		if(Operaciones.getMensaje() == ""){

			primeraFila = Integer.parseInt(Operaciones.toBin(type, 8)+Operaciones.toBin(code, 8), 2);
			sumaSiguientes = Operaciones.sumResto(rest);

			int firstSum = primeraFila + sumaSiguientes;

			String checksum = Operaciones.calculateCheckSum(firstSum);

			String[] siguientesHexa = Operaciones.getSumRestHexa();
			String[] sumandos = new String[siguientesHexa.length + 1];
			sumandos[0] = Integer.toHexString(primeraFila);
			
			for(int i = 0; i < siguientesHexa.length; i++){
				sumandos[i + 1] = siguientesHexa[i];
			}
			
			Operaciones.setResult(sumandos, Integer.toHexString(firstSum), checksum);
			showResult();

		}else{
			Toast.makeText(this, Operaciones.getMensaje(),
					Toast.LENGTH_LONG).show();
		}
		
	}
	/**
	 * 
	 */
	private void getValues(){
		fields[0] = type.getText().toString();
		fields[1] = code.getText().toString();
		fields[2] = rest.getText().toString();
	}
	/**
	 * 
	 */
	public void showResult() {

		Intent intent = new Intent(this, Result.class);    	
		startActivity(intent);
	}
}
