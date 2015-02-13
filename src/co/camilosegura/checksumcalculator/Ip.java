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
public class Ip extends Activity{
	/**
	 * 
	 */
	private TextView version;
	/**
	 * 
	 */
	private TextView headerLength;
	/**
	 * 
	 */
	private TextView headerLengthOp;
	/**
	 * 
	 */
	private TextView diffServ;
	/**
	 * 
	 */
	private TextView totalLength;
	/**
	 * 
	 */
	private TextView id;
	/**
	 * 
	 */
	private TextView flags;
	/**
	 * 
	 */
	private TextView fragmentOffset;
	/**
	 * 
	 */
	private TextView ttl;
	/**
	 * 
	 */
	private TextView protocol;
	/**
	 * 
	 */
	private TextView sourceIp;
	/**
	 * 
	 */
	private TextView destIp;
	/**
	 * 
	 */
	private String[] fields = new String[11];
	/**
	 * 
	 */
	private String[] fieldNames = {"Version", "Header Length", "DiffServ", "Total Length", "ID", "Flags", "Fragment Offset", "TTL", "Protocol", "Source IP Address", "Destination IP Address"};
	/**
	 * 
	 */
    private int[] fieldMaxValues = {15, 60, 255, 65535, 65535, 7, 8191, 255, 255};

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ip);

		version = (TextView)findViewById(R.id.version);
		headerLength = (TextView)findViewById(R.id.headerLength);
		diffServ = (TextView)findViewById(R.id.diffServ);
		totalLength = (TextView)findViewById(R.id.totalLength);
		id = (TextView)findViewById(R.id.id);
		flags = (TextView)findViewById(R.id.flags);
		fragmentOffset = (TextView)findViewById(R.id.fragmentOffset);
		ttl = (TextView)findViewById(R.id.ttl);
		protocol = (TextView)findViewById(R.id.protocol);
		sourceIp = (TextView)findViewById(R.id.sourceIP);
		destIp = (TextView)findViewById(R.id.destIP);
		headerLengthOp = (TextView)findViewById(R.id.hlc);

	}
	/**
	 * 
	 * @param view
	 */
	public void calculateIp(View view) {
		getValues();
		Operaciones.setMensaje("");
		Operaciones.validateIp(fields, fieldNames, fieldMaxValues);
        Operaciones.validateHeaderLength(headerLength, headerLengthOp);

		if(Operaciones.getMensaje() == ""){

			int primeraFila = Integer.parseInt(Operaciones.toBin(version, 4)+Operaciones.toBin(headerLengthOp, 4)+Operaciones.toBin(diffServ, 8), 2);
			int segundaFila = Integer.parseInt(Operaciones.toBin(totalLength, 16), 2);
			int terceraFila = Integer.parseInt(Operaciones.toBin(id, 16), 2);
			int cuartaFila = Integer.parseInt(Operaciones.toBin(flags, 3)+Operaciones.toBin(fragmentOffset, 13), 2);
			int quintaFila = Integer.parseInt(Operaciones.toBin(ttl, 8)+Operaciones.toBin(protocol, 8), 2);
			int sextaFila = Integer.parseInt(Operaciones.ipToBin(sourceIp, 0), 2);
			int septimaFila = Integer.parseInt(Operaciones.ipToBin(sourceIp, 1), 2);
			int octavaFila = Integer.parseInt(Operaciones.ipToBin(destIp, 0), 2);
			int novenaFila = Integer.parseInt(Operaciones.ipToBin(destIp, 1), 2);

			int firstSum = primeraFila + segundaFila + terceraFila + cuartaFila + quintaFila + sextaFila + septimaFila + octavaFila + novenaFila;						
			String checksum = Operaciones.calculateCheckSum(firstSum);
			
			String[] sumandos = {Integer.toHexString(primeraFila), Integer.toHexString(segundaFila), Integer.toHexString(terceraFila), Integer.toHexString(cuartaFila), Integer.toHexString(quintaFila), Integer.toHexString(sextaFila), Integer.toHexString(septimaFila), Integer.toHexString(octavaFila), Integer.toHexString(novenaFila)};
			
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
		fields[0] = version.getText().toString();
		fields[1] = headerLength.getText().toString();
		fields[2] = diffServ.getText().toString();
		fields[3] = totalLength.getText().toString();
		fields[4] = id.getText().toString();
		fields[5] = flags.getText().toString();
		fields[6] = fragmentOffset.getText().toString();
		fields[7] = ttl.getText().toString();
		fields[8] = protocol.getText().toString();
		fields[9] = sourceIp.getText().toString();
		fields[10] = destIp.getText().toString();
	}
	/**
	 * 
	 */
	public void showResult() {

		Intent intent = new Intent(this, Result.class);    	
		    	startActivity(intent);
	}

}
