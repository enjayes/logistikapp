package com.tum.ident.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The Class HashGenerator.
 */
public class HashGenerator {

	/** The hash length. */
	private static int hashLength = 32;

	/**
	 * Gets the hash length.
	 *
	 * @return the hash length
	 */
	public static int getHashLength() {
		return hashLength;
	}

	/**
	 * Sets the hash length.
	 *
	 * @param l the new hash length
	 */
	public static void setHashLength(int l) {
		hashLength = l;
	}

	/** The Constant hexArray. */
	final protected static char[] hexArray = "0123456789abcdef".toCharArray();

	/**
	 * Bytes to hex.
	 *
	 * @param bytes the bytes
	 * @return the string
	 */
	public static String bytesToHex(byte[] bytes) {
		if (bytes != null) {
			if (bytes.length > 0) {
				char[] hexChars = new char[bytes.length * 2];
				for (int j = 0; j < bytes.length; j++) {
					int v = bytes[j] & 0xFF;
					hexChars[j * 2] = hexArray[v >>> 4];
					hexChars[j * 2 + 1] = hexArray[v & 0x0F];
				}
				return new String(hexChars);
			}
		}
		return "";
	}

	/**
	 * Hash.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String hash(String text) {
		if (text == null) {
			text = "void";
		} else if (text.equals("")) {
			text = "empty";
		}
		MessageDigest md;
		byte[] shahash = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] textBytes = text.getBytes("iso-8859-1");
			if (textBytes != null) {
				if (textBytes.length > 0) {
					md.update(textBytes, 0, textBytes.length);
					shahash = md.digest();
					if (shahash != null) {
						String hex = bytesToHex(shahash);
						if (hex != null) {
							if (hex.length() > hashLength) {
								return hex.substring(0, hashLength);
							} else {
								return hex;
							}
						}
					}
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * public static String hashBouncy(String text){ if(text==null){ text =
	 * "void"; } DigestSHA3 md = new DigestSHA3(224); String body = text; //todo
	 * try { md.update(body.getBytes("UTF-8")); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } byte[] digest =
	 * md.digest(); return Hex.toHexString(digest).substring(0,hashLength); }
	 */

}
