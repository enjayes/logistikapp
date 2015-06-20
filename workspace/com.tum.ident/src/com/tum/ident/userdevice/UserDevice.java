package com.tum.ident.userdevice;

import java.io.Serializable;

import android.content.Context;

import com.tum.ident.bluetooth.BluetoothData;
import com.tum.ident.device.Device;
import com.tum.ident.user.User;
import com.tum.ident.wlan.WLANData;



/**
 * The Class UserDevice.
 */
public class UserDevice implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The user. */
	private User user = new User();
	
	/** The device. */
	private Device device = new Device();

	/**
	 * Gets the user gid.
	 *
	 * @return the user gid
	 */
	public String getUserGid() {
		return user.getGid();
	}

	/**
	 * Gets the device gid.
	 *
	 * @return the device gid
	 */
	public String getDeviceGid() {
		return device.getGid();
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public long getUserId() {
		return user.getId();
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public long getDeviceId() {
		return device.getId();
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * Collect data.
	 *
	 * @param context the context
	 */
	public void collectData(Context context) {

		BluetoothData.init();
		WLANData.init(context);

		user.collectData(context);
		device.collectData(context);

		BluetoothData.finish();
		WLANData.finish();
	}
}
