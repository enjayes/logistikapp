package com.tum.ident.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;
import com.tum.ident.storage.StorageHandler;



/**
 * The Class BatteryData.
 */
public class BatteryData extends BroadcastReceiver {

	/** The Constant TAG. */
	@SuppressWarnings("unused")
	private final static String TAG = "BatteryData";

	/** The battery. */
	private BatteryItemList battery = new BatteryItemList();

	/** The context. */
	private Context context;

	/** The data controller. */
	private DataController dataController;

	/** The received. */
	private boolean received = false;

	/**
	 * Instantiates a new battery data.
	 *
	 * @param context the context
	 * @param dataController the data controller
	 */
	public BatteryData(Context context, DataController dataController) {
		this.dataController = dataController;
		this.context = context;
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		load();
		this.context.registerReceiver(this, filter);
		//Log.v(TAG, "registerReceiver");
	}

	/**
	 * Gets the battery item list.
	 *
	 * @return the battery item list
	 */
	public BatteryItemList getBatteryItemList() {
		return battery;
	}

	/**
	 * Gets the battery string.
	 *
	 * @return the battery string
	 */
	public String getBatteryString() {
		if (battery != null) {
			return battery.toString();
		}
		return "";
	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		//Log.v(TAG, "batteryData.getDataItem");
		save();
		return new DataItem("", battery);
	}

	/* 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (battery == null) {
			battery = new BatteryItemList();
		}
		//Log.v(TAG, "onReceive!!!");
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
		//Log.v(TAG, "level: " + level);
		//Log.v(TAG, "scale: " + scale);

		int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
		boolean present = intent.getExtras().getBoolean(
				BatteryManager.EXTRA_PRESENT);
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
		int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,
				0);
		if (battery.update(level, scale, plugged, present, status, temperature)) {
			save();
			dataController.addData("", battery);
			received = true;
		} else if (received == false) {
			dataController.addData("", battery);
			received = true;
		}
	}

	/**
	 * Load.
	 */
	public void load() {

		String fileName = "battery.ser";

		battery = null;
		battery = (BatteryItemList) StorageHandler.loadObject(fileName);
		if (battery == null) {
			battery = new BatteryItemList();
		}

	}
	
	/**
	 * Save.
	 */
	public void save() {
		
		String fileName = "battery.ser";

		StorageHandler.saveObject(battery, fileName);

	}

}
