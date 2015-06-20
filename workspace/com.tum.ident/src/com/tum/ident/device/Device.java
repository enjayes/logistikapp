package com.tum.ident.device;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import com.tum.ident.apps.PackageData;
import com.tum.ident.bluetooth.BluetoothData;
import com.tum.ident.system.SystemData;
import com.tum.ident.userdevice.BluetoothItem;
import com.tum.ident.userdevice.PackageItem;
import com.tum.ident.userdevice.WLANItem;
import com.tum.ident.util.Util;
import com.tum.ident.wlan.WLANData;


/**
 * The Class Device.
 */
public class Device implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The gid. */
	private String gid;
	
	/** The id. */
	private long id = 0;

	/** The device id list. */
	private ArrayList<DeviceIDItem> deviceIDList = new ArrayList<DeviceIDItem>();
	
	/** The bluetooth list. */
	private ArrayList<BluetoothItem> bluetoothList = new ArrayList<BluetoothItem>();
	
	/** The wlan list. */
	private ArrayList<WLANItem> wlanList = new ArrayList<WLANItem>();

	/** The package list. */
	private ArrayList<PackageItem> packageList = new ArrayList<PackageItem>();

	/** The manufacturer. */
	private String manufacturer;
	
	/** The model. */
	private String model;
	
	/** The cpu info. */
	private String cpuInfo;
	
	/** The internal storage size. */
	private String internalStorageSize;
	
	/** The total mem. */
	private String totalMem;

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
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
	 * Gets the internal storage size.
	 *
	 * @return the internal storage size
	 */
	public String getInternalStorageSize() {
		return internalStorageSize;
	}

	/**
	 * Gets the device id list.
	 *
	 * @return the device id list
	 */
	public ArrayList<DeviceIDItem> getDeviceIDList() {
		return deviceIDList;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Gets the cpu info.
	 *
	 * @return the cpu info
	 */
	public String getCpuInfo() {
		return cpuInfo;
	}

	/**
	 * Gets the total mem.
	 *
	 * @return the total mem
	 */
	public String getTotalMem() {
		return totalMem;
	}

	/**
	 * Gets the bluetooth.
	 *
	 * @return the bluetooth
	 */
	public ArrayList<BluetoothItem> getBluetooth() {
		return bluetoothList;
	}

	/**
	 * Gets the wlan.
	 *
	 * @return the wlan
	 */
	public ArrayList<WLANItem> getWLAN() {
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
	 * Collect data.
	 *
	 * @param context the context
	 */
	public void collectData(Context context) {

		deviceIDList.clear();
		deviceIDList.add(SystemData.getDeviceIDs(context));

		bluetoothList.clear();
		bluetoothList.add(BluetoothData.getBluetooth());

		wlanList.clear();
		wlanList.add(WLANData.getWLAN(context));

		manufacturer = SystemData.getManufacturer();
		model = SystemData.getModel();
		cpuInfo = SystemData.getCPUInfo();
		internalStorageSize = SystemData.getInternalStorageSize();
		totalMem = SystemData.getTotalMemory();

		packageList = PackageData.getPackageData(context, true);

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
	 * Gets the bluetooth string.
	 *
	 * @return the bluetooth string
	 */
	public String getBluetoothString() {
		return Util.toStringFilterNewLine(bluetoothList);
	}

}
