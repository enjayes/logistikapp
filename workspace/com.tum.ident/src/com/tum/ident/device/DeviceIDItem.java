package com.tum.ident.device;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * The Class DeviceIDItem.
 */
public class DeviceIDItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The gsf android id. */
	private String gsfAndroidID;
	
	/** The imei string. */
	private String imeiString;
	
	/** The serial num. */
	private String serialNum;
	
	/** The android id. */
	private String androidID;

	/**
	 * Gets the gsf android id.
	 *
	 * @return the gsf android id
	 */
	public String getGsfAndroidID() {
		return gsfAndroidID;
	}

	/**
	 * Gets the imei string.
	 *
	 * @return the imei string
	 */
	public String getImeiString() {
		return imeiString;
	}

	/**
	 * Gets the serial num.
	 *
	 * @return the serial num
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * Gets the android id.
	 *
	 * @return the android id
	 */
	public String getAndroidID() {
		return androidID;
	}

	/**
	 * Instantiates a new device id item.
	 *
	 * @param gsfAndroidID the gsf android id
	 * @param imeiString the imei string
	 * @param serialNum the serial num
	 * @param androidID the android id
	 */
	public DeviceIDItem(String gsfAndroidID, String imeiString,
			String serialNum, String androidID) {
		this.gsfAndroidID = gsfAndroidID;
		this.imeiString = imeiString;
		this.serialNum = serialNum;
		this.androidID = androidID;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
