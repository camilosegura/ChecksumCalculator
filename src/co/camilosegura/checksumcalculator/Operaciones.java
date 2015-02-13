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

import android.util.Log;
import android.widget.TextView;
/**
 * 
 * @author Camilo segura
 *
 */
public class Operaciones {

	/**
	 * 
	 */
	private static String mensaje = "";
	/**
	 * 
	 */
	private static int mask = Integer.parseInt("00000000000000001111111111111111", 2);
	/**
	 * 
	 */
	private static String result = "";
	/**
	 * 
	 */		
	private static String sums = "";
	/**
	 * 
	 */
	private static String[] sumRestHexa; 
	/**
	 * 
	 * @param mensajeIni
	 */
	public static void setMensaje(String mensajeIni) {
		mensaje = mensajeIni;
	}
	/**
	 * 
	 * @return
	 */
	public static String getMensaje() {
		return mensaje;
	}
	/**
	 * 
	 * @return
	 */
	public static String[] getSumRestHexa() {
		return sumRestHexa;
	}
	/**
	 * 
	 * @param decimal
	 * @param digitos
	 * @return
	 */
	public static String toBin(TextView decimal, int digitos) {

		return strToBin(decimal.getText().toString(), digitos) ;

	}
	/**
	 * 
	 * @param ip
	 * @param parte
	 * @return
	 */
	public static String ipToBin(TextView ip, int parte) {

		String[] octetos = ip.getText().toString().split("\\.");		
		String bin = "";

		if(parte == 0){
			bin = strToBin(octetos[0], 8)+strToBin(octetos[1], 8);
		}else{
			bin = strToBin(octetos[2], 8)+strToBin(octetos[3], 8);
		}

		return bin;			
	}
	/**
	 * 
	 * @param decimal
	 * @param digitos
	 * @return
	 */
	public static String strToBin(String decimal, int digitos) {

		String bin = Integer.toBinaryString(Integer.parseInt(decimal));

		while(bin.length() < digitos){
			bin = '0'+bin;
		}

		return bin;
	}
	/**
	 * 
	 * @param decimal
	 * @return
	 */
	public static int sumResto(TextView decimal) {
		int suma = 0;
		String dosOctetos = "";

		String hexa = Long.toHexString(Long.parseLong(decimal.getText().toString()));
		sumRestHexa = new String[hexa.length()/4];
		int i = 0;

		while(hexa.length() > 0){
			dosOctetos = hexa.substring(0, 4);
			hexa = hexa.substring(4);
			sumRestHexa[i] = dosOctetos;

			suma += Integer.parseInt(dosOctetos, 16);

			i++;
		}

		return suma;
	}
	/**
	 * 
	 * @param fields
	 * @param fieldName
	 * @param fieldMaxValues
	 */
	public static void validateIp(String[] fields, String[] fieldName, int[] fieldMaxValues){
		validateEmpty(fields, fieldName);

		for(int i = 9; i < fields.length; i++){
			String[] octetos = fields[i].split("\\.");
			if(octetos.length != 4){
				mensaje += "El campo "+fieldName[i]+" No es una IP valida, debe ser del tipo 255.255.255.255 en decimal\n";

			}else{
				if(Integer.parseInt(octetos[0]) > 255 || Integer.parseInt(octetos[1]) > 255 || Integer.parseInt(octetos[2]) > 255 || Integer.parseInt(octetos[3]) > 255){
					mensaje += "Los Octetos del campo "+fieldName[i]+" No son validos\n";

				}
			}
		}

		for(int i = 0; i < fieldMaxValues.length; i++){
			validateValue(fields[i], fieldName[i], fieldMaxValues[i]);
		}
	}
	/**
	 * 
	 * @param fields
	 * @param fieldName
	 */
	public static void validateEmpty(String[] fields, String[] fieldName) {

		for(int i = 0; i < fields.length; i++){
			if(fields[i].replaceAll(" ", "") == ""){
				mensaje += "El campo "+fieldName[i]+" No puede estar vacio\n";

			}
		}

	}
	/**
	 * 
	 * @param fields
	 * @param fieldName
	 */
	public static void validateIcmp(String[] fields, String[] fieldName){
		validateEmpty(fields, fieldName);
		String resto =  "";
		int residuo = 0;

		if(fields[2].replaceAll(" ", "") != ""){
			resto =  Long.toHexString(Long.parseLong(fields[2]));
			residuo = resto.length()%4;
			Log.d("sss", "conversion: " + resto);
			if(residuo != 0){
				mensaje += "El campo rest tiene una longitud invalida";
			}
		}
		for(int i = 0; i< 2; i++){
			validateValue(fields[i], fieldName[i], 255);
		}

	}
	/**
	 * 
	 * @param field
	 * @param fieldName
	 * @param maxValue
	 */
	public static void validateValue(String field, String fieldName, int maxValue){
		if(field.replaceAll(" ", "") != ""){
			if(Integer.parseInt(field) > maxValue){
				mensaje += "El campo "+fieldName+" debe ser menor a "+ Integer.toString(maxValue)+"\n";
			}
		}

	}
	/**
	 * 
	 * @param sum
	 * @return
	 */
	public static String calculateCheckSum(int sum) {
		String checkSum = "";		
		int sumWC = 0;
		int carry = 0;		

		sums = "";
		carry = sum >> 16;

		while(carry > 0){
			sumWC = sum & mask;		
			sum = sumWC + carry;

			sumWcarry(sum, sumWC, carry);

			carry = sum >> 16;
		}
		checkSum = Integer.toHexString(~sum & mask);

		return checkSum;
	}
	/**
	 * 
	 * @param sumandos
	 * @param firstSum
	 * @param checksum
	 */
	public static void setResult(String[] sumandos, String firstSum, String checksum) {
		result = "";
		for(int i = 0; i < sumandos.length; i++){
			while(sumandos[i].length() < 4){
				sumandos[i] = "0"+sumandos[i];
			}
			result += sumandos[i] + "\n";
		}		
		result += "______\n";
		result += firstSum +"\n";
		result += sums;

		result += "\n Checksum = "+checksum + "\n";

		result = result.toUpperCase();
	}
	/**
	 * 
	 * @return
	 */
	public static String getResult() {
		return result;
	}
	/**
	 * 
	 */
	private static void sumWcarry(int sum, int sumWC, int carry){
		String sumWCHexa = "";
		String sumHexa = "";
		sumWCHexa = Integer.toHexString(sumWC);
		sumHexa = Integer.toHexString(sum);

		while(sumWCHexa.length() < 4){
			sumWCHexa = "0"+sumWCHexa;
		}

		while(sumHexa.length() < 4){
			sumHexa = "0"+sumHexa;
		}

		sums += "\n"+sumWCHexa+"\n";
		sums += Integer.toHexString(carry)+"\n";
		sums += "_____\n";
		sums += sumHexa;
	}
	/**
	 * 
	 * @param headerLength
	 * @param headerLengthOp
	 */
	public static void validateHeaderLength(TextView headerLength, TextView headerLengthOp) {
		int hL = 0;
		if(headerLength.getText().toString().replaceAll(" ", "") != ""){
			hL = Integer.parseInt(headerLength.getText().toString());
			if(hL%4 == 0 && hL/4 > 4){
				headerLengthOp.setText((Integer.toString(hL/4)));
			}else {
				mensaje +="El campo Header Length No es correcto\n";
			}
		}
	}   

}
