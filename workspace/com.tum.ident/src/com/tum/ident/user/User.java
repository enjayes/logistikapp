package com.tum.ident.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.content.Context;

import com.tum.ident.accounts.AccountData;
import com.tum.ident.accounts.AccountItem;
import com.tum.ident.apps.PackageData;
import com.tum.ident.bluetooth.BluetoothData;
import com.tum.ident.calllog.CallLogData;
import com.tum.ident.calllog.CallLogItem;
import com.tum.ident.contacts.ContactData;
import com.tum.ident.contacts.ContactItem;
import com.tum.ident.data.DataController;
import com.tum.ident.sim.SIMData;
import com.tum.ident.sim.SIMItem;
import com.tum.ident.userdevice.BluetoothItem;
import com.tum.ident.userdevice.PackageItem;
import com.tum.ident.userdevice.WLANItem;
import com.tum.ident.util.HashGenerator;
import com.tum.ident.util.Util;
import com.tum.ident.wlan.WLANData;


/**
 * The Class User.
 */
public class User implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The tag. */
	@SuppressWarnings("unused")
	transient private final String TAG = "DataController";

	/** The gid. */
	private String gid;
	
	/** The id. */
	private long id = 0;

	/** The user name. */
	private String userName;
	
	/** The contact list. */
	private ArrayList<ContactItem> contactList = new ArrayList<ContactItem>();
	
	/** The call log list. */
	private ArrayList<CallLogItem> callLogList = new ArrayList<CallLogItem>();
	
	/** The bluetooth list. */
	private ArrayList<BluetoothItem> bluetoothList = new ArrayList<BluetoothItem>();
	
	/** The wlan list. */
	private ArrayList<WLANItem> wlanList = new ArrayList<WLANItem>();
	
	/** The package list. */
	private ArrayList<PackageItem> packageList = new ArrayList<PackageItem>();
	
	/** The account list. */
	private ArrayList<AccountItem> accountList = new ArrayList<AccountItem>();
	
	/** The phone number list. */
	private ArrayList<String> phoneNumberList = new ArrayList<String>();
	
	/** The sim list. */
	private ArrayList<SIMItem> simList = new ArrayList<SIMItem>();
	
	/** The carrier list. */
	private ArrayList<String> carrierList = new ArrayList<String>();

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the gid.
	 *
	 * @param gid the new gid
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * Gets the gid.
	 *
	 * @return the gid
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the carrier list.
	 *
	 * @return the carrier list
	 */
	public ArrayList<String> getCarrierList() {
		return carrierList;
	}

	/**
	 * Gets the SIM list.
	 *
	 * @return the SIM list
	 */
	public ArrayList<SIMItem> getSIMList() {
		return simList;
	}

	/**
	 * Gets the account list.
	 *
	 * @return the account list
	 */
	public ArrayList<AccountItem> getAccountList() {
		return accountList;
	}

	/**
	 * Gets the contact list.
	 *
	 * @return the contact list
	 */
	public ArrayList<ContactItem> getContactList() {
		return contactList;
	}

	/**
	 * Gets the call log list.
	 *
	 * @return the call log list
	 */
	public ArrayList<CallLogItem> getCallLogList() {
		return callLogList;
	}

	/**
	 * Gets the bluetooth list.
	 *
	 * @return the bluetooth list
	 */
	public ArrayList<BluetoothItem> getBluetoothList() {
		return bluetoothList;
	}

	/**
	 * Gets the WLAN list.
	 *
	 * @return the WLAN list
	 */
	public ArrayList<WLANItem> getWLANList() {
		return wlanList;
	}

	/**
	 * Gets the package list.
	 *
	 * @return the package list
	 */
	public ArrayList<PackageItem> getPackageList() {
		return packageList;
	}

	/**
	 * Gets the phone number list.
	 *
	 * @return the phone number list
	 */
	public ArrayList<String> getPhoneNumberList() {
		return phoneNumberList;
	}

	/**
	 * Gets the user name from account.
	 *
	 * @param accountName the account name
	 * @return the user name from account
	 */
	private String getUserNameFromAccount(String accountName) {
		String name = accountName;
		if (accountName.equals("Sync") || accountName.equals("Office")
				|| accountName.equals("WhatsApp")) {
			name = "";
		} else {
			accountName = accountName + "@";
			String[] parts = accountName.split("@");
			if (parts.length >= 1) {
				name = parts[0];
				name = name.replaceAll("\\.", " ");
				name = name.replaceAll("_", " ");
				name = name.replaceAll("-", " ");
				name = name.replaceAll("	", " ");
				name = name.toLowerCase(Locale.getDefault());
				if (name.indexOf(" ") == -1) {
					name = "";
				}
			}
		}
		return name;
	}

	/**
	 * Extract user name.
	 */
	private void extractUserName() {
		String name = "";
		HashMap<String, Long> map = new HashMap<String, Long>();
		for (AccountItem accountItem : accountList) {
			String extracted = getUserNameFromAccount(accountItem.getName());
			if (extracted.length() > 0) {
				Long counter = map.get(extracted);
				if (counter == null) {
					map.put(extracted, Long.valueOf(0));
				} else {
					map.put(extracted, counter + 1);
				}
			}
		}
		long max = -1;
		Iterator<Map.Entry<String, Long>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Long> pairs = it.next();
			if (max == -1 || pairs.getValue().longValue() > max) {
				max = pairs.getValue().longValue();
				name = pairs.getKey();
			}
			it.remove();
		}
		if (name != null) {
			if (name.length() > 0) {
				StringBuilder result = new StringBuilder(name.length());
				String[] words = name.split("\\s");
				for (int i = 0; i < words.length; ++i) {
					if (i > 0) {
						result.append(" ");
					}
					if (words[i].length() > 0) {
						if (words[i].length() > 1) {
							result.append(
									Character.toUpperCase(words[i].charAt(0)))
									.append(words[i].substring(1));
						} else {
							result.append(words[i].charAt(0));
						}
					}
				}
				userName = result.toString();
			}
		}
	}

	/**
	 * Gets the SIM list string.
	 *
	 * @return the SIM list string
	 */
	public String getSIMListString() {
		return Util.toStringFilterNewLine(simList);
	}

	/**
	 * Gets the carrier list string.
	 *
	 * @return the carrier list string
	 */
	public String getCarrierListString() {
		return Util.toStringFilterNewLine(carrierList);
	}

	/**
	 * Gets the call log string.
	 *
	 * @return the call log string
	 */
	public String getCallLogString() {
		return Util.toStringFilterNewLine(callLogList);
	}

	/**
	 * Gets the contact string.
	 *
	 * @return the contact string
	 */
	public String getContactString() {
		return Util.toStringFilterNewLine(contactList);
	}

	/**
	 * Gets the account list string.
	 *
	 * @return the account list string
	 */
	public String getAccountListString() {
		return Util.toStringFilterNewLine(accountList);
	}

	/**
	 * Gets the bluetooth string.
	 *
	 * @return the bluetooth string
	 */
	public String getBluetoothString() {
		return Util.toStringFilterNewLine(bluetoothList);
	}

	/**
	 * Gets the WLAN string.
	 *
	 * @return the WLAN string
	 */
	public String getWLANString() {
		return Util.toStringFilterNewLine(wlanList);
	}

	/**
	 * Gets the package string.
	 *
	 * @return the package string
	 */
	public String getPackageString() {
		return Util.toStringFilterNewLine(packageList);
	}

	/**
	 * Collect data.
	 *
	 * @param context the context
	 */
	public void collectData(Context context) {

		simList.clear();
		simList.add(SIMData.getSIMData(context));

		carrierList.clear();
		carrierList.add(SIMData.getCarrierName(context));
		phoneNumberList.clear();
		phoneNumberList.add(SIMData.getPhoneNumber(context));

		callLogList = CallLogData.getCallLogData(context);

		contactList = ContactData.getContactData(context);

		bluetoothList = BluetoothData.getBluetoothData();

		wlanList = WLANData.getWLANData(context);

		packageList = PackageData.getPackageData(context, false);

		accountList = AccountData.getAccountData(context);

		extractUserName();
		if (accountList != null) {
			if (accountList.size() > 0) {
				if (userName != null) {
					if (userName.equals("")) {
						userName = accountList.get(0).getName();
					}
				} else {
					userName = accountList.get(0).getName();
				}
			}
		}
		if (userName == null) {
			userName = "";
		}

		DataController.setUserName(userName);//todo
		
		userName = HashGenerator.hash(userName);

		for (AccountItem account : accountList) {
			account.setName(HashGenerator.hash(account.getName()));
			// Log.v(TAG,account.toString());
		}

	}

}
