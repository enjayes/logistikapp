package com.tum.ident.identification;

import java.io.Serializable;

import com.tum.ident.userdevice.UserDevice;


/**
 * The Class IdentificationItem.
 */
public class IdentificationItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The method. */
	private  String method = "";
	
	/** The user device. */
	private  UserDevice userDevice = null;
	
	/** The user id. */
	private  String userID = "";
	
	/** The device id. */
	private  String deviceID = "";
	
	/**
	 * Instantiates a new identification item.
	 */
	public IdentificationItem(){
		userDevice = new UserDevice();
	}
	
	/**
	 * Instantiates a new identification item.
	 *
	 * @param deviceID the device id
	 * @param userID the user id
	 */
	public IdentificationItem(String deviceID,String userID){
		this.method = "compare";
		this.deviceID = deviceID;
		this.userID = userID;
		this.userDevice = null;
	}
	
	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * Sets the method.
	 *
	 * @param method the new method
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * Gets the user device.
	 *
	 * @return the user device
	 */
	public UserDevice getUserDevice() {
		return userDevice;
	}
	
	/**
	 * Sets the user device.
	 *
	 * @param userDevice the new user device
	 */
	public void setUserDevice(UserDevice userDevice) {
		this.userDevice = userDevice;
	}
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param userID the new user id
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceID() {
		return deviceID;
	}
	
	/**
	 * Sets the device id.
	 *
	 * @param deviceID the new device id
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	
}
