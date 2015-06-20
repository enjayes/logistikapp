package com.tum.ident.contacts;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;


//import android.util.Log;






import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.tum.ident.util.HashGenerator;


/**
 * The Class ContactData.
 */
public class ContactData {
	// private static final String TAG = "ContactData";

	
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
	 * Gets the contact data.
	 *
	 * @param context the context
	 * @return the contact data
	 */
	public static ArrayList<ContactItem> getContactData(Context context) {
		ArrayList<ContactItem> contactList = new ArrayList<ContactItem>();
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(Phone.CONTENT_URI,
					null, null, null, null);
			int contactIdIdx = cursor.getColumnIndex(BaseColumns._ID);
			int phoneNumberIdx = cursor.getColumnIndex(Phone.NUMBER);
			cursor.moveToFirst();
			do {
				contactList.add(new ContactItem(HashGenerator.hash(cursor
						.getString(contactIdIdx)), HashGenerator.hash(cursor
						.getString(phoneNumberIdx))));
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}


		try
		{
			String ClsSimPhonename = null; 
			String ClsSimphoneNo = null;

			Uri simUri = Uri.parse("content://icc/adn"); 
			Cursor cursorSim = context.getContentResolver().query(simUri,null,null,null,null);

			while (cursorSim.moveToNext()) 
			{      
				ClsSimPhonename =cursorSim.getString(cursorSim.getColumnIndex("name"));
				ClsSimphoneNo = cursorSim.getString(cursorSim.getColumnIndex("number"));
				ClsSimphoneNo.replaceAll("\\D","");
				ClsSimphoneNo.replaceAll("&", "");
				ClsSimphoneNo = fixPhoneNumber(context,ClsSimphoneNo);
				ClsSimPhonename=ClsSimPhonename.replace("|","");
				contactList.add(new ContactItem(HashGenerator.hash(ClsSimPhonename), HashGenerator.hash(ClsSimphoneNo)));
			}        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		
		return contactList;

	}
}
