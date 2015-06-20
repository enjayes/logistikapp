package com.tum.ident.sim;

import android.content.Context;
import android.telephony.TelephonyManager;
//import android.util.Log;


import com.tum.ident.util.HashGenerator;


/**
 * The Class SIMData.
 */
public class SIMData {

	/**
	 * Gets the SIM data.
	 *
	 * @param context the context
	 * @return the SIM data
	 */
	public static SIMItem getSIMData(Context context) {
		TelephonyManager phoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return new SIMItem(HashGenerator.hash(phoneManager.getSubscriberId()),
				HashGenerator.hash(phoneManager.getSimSerialNumber()),
				HashGenerator.hash(phoneManager.getLine1Number()));
	}

	/**
	 * Gets the phone number.
	 *
	 * @param context the context
	 * @return the phone number
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager phoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return HashGenerator.hash(phoneManager.getLine1Number());
	}

	/**
	 * Gets the carrier name.
	 *
	 * @param context the context
	 * @return the carrier name
	 */
	public static String getCarrierName(Context context) {
		TelephonyManager phoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return HashGenerator.hash(phoneManager.getNetworkOperatorName());
	}

}
