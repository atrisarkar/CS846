package com.cs846.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetHash {
	
	public static String getHash(String string) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(string.getBytes());
		String encryptedString = new String(messageDigest.digest());
		return encryptedString;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// test string
		String text = "Hello world!";
		System.out.println("Test string = " + text);

		// convert to big integer
		BigInteger bigInt = new BigInteger(text.getBytes());
		System.out.println(bigInt.toString());
		// convert back
		String textBack = new String(bigInt.toByteArray());
		System.out.println("And back = " + textBack);
	}

}
