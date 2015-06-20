package com.tum.ident.result;

import java.io.Serializable;
import java.util.ArrayList;

import com.tum.ident.device.DeviceMatch;
import com.tum.ident.user.UserMatch;


/**
 * The Class ResultMatchItem.
 */
public class ResultMatchItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum Type.
	 */
	public enum Type {
		
		/** The ok. */
		OK, 
 /** The Error. */
 Error
	}

	/** The type. */
	private Type type;
	
	/** The user match list. */
	private ArrayList<UserMatch> userMatchList;
	
	/** The device match list. */
	private ArrayList<DeviceMatch> deviceMatchList;
	
	/** The phone number. */
	private String phoneNumber;
	
	/**
	 * Instantiates a new result match item.
	 *
	 * @param type the type
	 * @param deviceMatchList the device match list
	 * @param userMatchList the user match list
	 */
	public ResultMatchItem(Type type, ArrayList<DeviceMatch> deviceMatchList, ArrayList<UserMatch> userMatchList) {
		this.type = type;
		this.userMatchList = userMatchList;
		this.deviceMatchList = deviceMatchList;
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
	 * Gets the user match list.
	 *
	 * @return the user match list
	 */
	public ArrayList<UserMatch> getUserMatchList() {
		return userMatchList;
	}

	/**
	 * Sets the user match list.
	 *
	 * @param userMatchList the new user match list
	 */
	public void setUserMatchList(ArrayList<UserMatch> userMatchList) {
		this.userMatchList = userMatchList;
	}

	/**
	 * Gets the device match list.
	 *
	 * @return the device match list
	 */
	public ArrayList<DeviceMatch> getDeviceMatchList() {
		return deviceMatchList;
	}

	/**
	 * Sets the device match list.
	 *
	 * @param deviceMatchList the new device match list
	 */
	public void setDeviceMatchList(ArrayList<DeviceMatch> deviceMatchList) {
		this.deviceMatchList = deviceMatchList;
	}
	
	

}
