package com.tum.ident.calllog;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.TelephonyManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.tum.ident.util.HashGenerator;



//CallLogData, ist für die Abfrage der Anruflisten - und SMS-History-Datenzuständig

/**
 * The Class CallLogData.
 */
public class CallLogData {


	/**
	 * Fix phone number.
	 *
	 * @param ctx the ctx
	 * @param number the number
	 * @return the string
	 */
	public static String fixPhoneNumber(Context ctx, String number)
	{
	    String      fixedNumber = "";
	    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
	    PhoneNumber     phoneNumberProto;
	    TelephonyManager    phoneManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
	    String              curLocale = phoneManager.getNetworkCountryIso().toUpperCase();
	    String              curDCode = String.format("%d", phoneUtil.getCountryCodeForRegion(curLocale));
	    String              ourDCode = "";
	    if(number.indexOf("+") == 0)
	    {
	        int     bIndex = number.indexOf("(");
	        int     hIndex = number.indexOf("-");
	        int     eIndex = number.indexOf(" ");
	        if(bIndex != -1)
	        {
	            ourDCode = number.substring(1, bIndex);
	        }
	        else if(hIndex != -1) 
	        {               
	            ourDCode = number.substring(1, hIndex);
	        }
	        else if(eIndex != -1)
	        {
	            ourDCode = number.substring(1, eIndex);
	        }
	        else
	        {
	            ourDCode = curDCode;
	        }           
	    }
	    else
	    {
	        ourDCode = curDCode;
	    }
	    try 
	    {
	      phoneNumberProto = phoneUtil.parse(number, curLocale);
	    } 
	    catch (NumberParseException e) 
	    {
	      return number;
	    }
	    if(curDCode.compareTo(ourDCode) == 0)
	        fixedNumber = phoneUtil.format(phoneNumberProto, PhoneNumberFormat.NATIONAL);
	    else
	        fixedNumber = phoneUtil.format(phoneNumberProto, PhoneNumberFormat.INTERNATIONAL);
	    return fixedNumber.replace(" ", "");
	}
	
	
	/**
	 * Gets the call log data.
	 *
	 * @param context the context
	 * @return the call log data
	 */
	public static ArrayList<CallLogItem> getCallLogData(Context context) {
		ArrayList<CallLogItem> callLogList = new ArrayList<CallLogItem>();
		Cursor managedCursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null, null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

		while (managedCursor.moveToNext()) {
			String phoneNumber = HashGenerator.hash(fixPhoneNumber(context,managedCursor
					.getString(number)));
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			String callDuration = managedCursor.getString(duration);
			callLogList.add(new CallLogItem(HashGenerator.hash(phoneNumber),
					callType, callDate, callDuration));
		}
		
		 Uri uri = Uri.parse("content://sms");
         Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

         if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                      String body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                                    .toString();
                      String phoneNumber = fixPhoneNumber(context,cursor.getString(cursor.getColumnIndexOrThrow("address"))
                                    .toString());
                      String callDate = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                                    .toString();
                      String callType = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                              .toString();

                      callLogList.add(new CallLogItem(HashGenerator.hash(phoneNumber),
          					callType, callDate, "-1"));

                      cursor.moveToNext();
                }
         }
         cursor.close();

         
         
         
		
		return callLogList;
	}

}
