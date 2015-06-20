package com.tum.ident.identification;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;

import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;



/**
 * The Class IdentificationData.
 */
public class IdentificationData {
	
	/** The Constant TAG. */
	@SuppressWarnings("unused")
	private static final String TAG = "IdentificationData";
	
	/** The identification item. */
	private IdentificationItem identificationItem;
	
	/** The context. */
	private Context context = null;
	
	/** The data collected. */
	private boolean dataCollected = false;
	
	/** The answer received. */
	private boolean answerReceived = false;
	
	/** The start time. */
	private long startTime;
	
	/** The stop identification. */
	private boolean stopIdentification = false;
	
	/** The collecting data. */
	private boolean collectingData = false;
	
	/** The data controller. */
	private DataController dataController;

	/**
	 * Instantiates a new identification data.
	 *
	 * @param context the context
	 * @param dataController the data controller
	 */
	public IdentificationData(Context context, DataController dataController) {
		this.dataController = dataController;
		this.context = context;
		identificationItem = new IdentificationItem();
		startTime = java.lang.System.currentTimeMillis();
	}

	/**
	 * On receive ids.
	 *
	 * @param deviceID the device id
	 * @param userID the user id
	 */
	public void onReceiveIds(String deviceID, String userID) {
		identificationItem.setUserID(userID);
		identificationItem.setDeviceID(deviceID);
		identificationItem.getUserDevice().getUser().setGid(userID);
		identificationItem.getUserDevice().getDevice().setGid(deviceID);
		answerReceived = true;
	}

	/**
	 * Collect identification data.
	 *
	 * @param context the context
	 */
	private void collectIdentificationData(Context context) {
		identificationItem.getUserDevice().collectData(context);
		dataCollected = true;
	}

	/**
	 * Checks if is ready.
	 *
	 * @return true, if is ready
	 */
	public boolean isReady(){
		return dataCollected;
	}
	
	/**
	 * Start identification.
	 */
	public void startIdentification() {
		stopIdentification = false;
		new IdentificationAsync().execute();
	}

	/**
	 * Adds the identification data.
	 *
	 * @return true, if successful
	 */
	public boolean addIdentificationData() {
		if (collectingData) {
			return false;
		}
		collectingData = true;
		Bundle parameter = new Bundle();
		parameter.putString("method", "add");
		new CollectAsync().execute(new Bundle(parameter));
		return true;
	}

	/**
	 * Update identification data.
	 *
	 * @return true, if successful
	 */
	public boolean updateIdentificationData() {
		if (collectingData) {
			return false;
		}
		collectingData = true;
		Bundle parameter = new Bundle();
		parameter.putString("method", "update");
		new CollectAsync().execute(new Bundle(parameter));
		return true;
	}

	/**
	 * Stop identification.
	 */
	public void stopIdentification() {
		stopIdentification = true;
	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		if (identificationItem != null) {
			if (dataCollected) {
				return new DataItem("", identificationItem);
			}
		}
		return null;
	}
	
	/**
	 * Gets the identification item.
	 *
	 * @return the identification item
	 */
	public IdentificationItem getIdentificationItem(){
		if(dataCollected){
			return identificationItem;
		}
		else{
			return null;
		}
	}

	/**
	 * The Class IdentificationAsync.
	 */
	private class IdentificationAsync extends AsyncTask<Void, String, String> {
		
		/* 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(Void... params) {
			Looper.prepare();
			do {
				if (answerReceived
						|| java.lang.System.currentTimeMillis() - startTime > 20000) {
					if (identificationItem.getDeviceID().length() > 0
							&& identificationItem.getUserID().length() > 0) {

						IdentificationItem compareItem = new IdentificationItem(identificationItem.getDeviceID(),identificationItem.getUserID());
						dataController.addData("", compareItem);
						break;
					}
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} while (stopIdentification == false);
			Looper.loop();
			return null;
		}

	}

	/**
	 * The Class CollectAsync.
	 */
	private class CollectAsync extends AsyncTask<Bundle, String, String> {
		
		/* 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(Bundle... params) {
			Looper.prepare();
			if (dataCollected == false) {
				collectIdentificationData(context);
			}
			Object methodObject = params[0].get("method");
			if (methodObject != null) {
				identificationItem.setMethod(methodObject.toString());

			}
			if (identificationItem.getMethod().equals("add")) {
				dataController.addData("void", identificationItem);
			} else {
				dataController.addData("", identificationItem);
			}
			collectingData = false;
			Looper.loop();
			return null;
		}

	}

}
