package com.tum.ident.result;

import java.io.Serializable;



/**
 * The Class ResultIDItem.
 */
public class ResultIDItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum Type.
	 */
	public enum Type {
		
		/** The Insert. */
		Insert, 
 /** The Update. */
 Update, 
 /** The Error. */
 Error
	}

	/** The type. */
	private  Type type;
	
	/** The device id. */
	private  String deviceID;
	
	/** The user id. */
	private  String userID;

	/**
	 * Instantiates a new result id item.
	 *
	 * @param type the type
	 * @param deviceID the device id
	 * @param userID the user id
	 */
	public ResultIDItem(Type type, String deviceID, String userID) {
		this.type = type;
		this.deviceID = deviceID;
		this.userID = userID;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
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

	
	
}