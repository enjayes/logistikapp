package com.tum.ident.userdevice;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * The Class WLANItem.
 */
public class WLANItem implements Serializable {
	
	
	/**
	 * The Enum WLANType.
	 */
	public enum WLANType {
		
		/** The Device. */
		Device, 
 /** The Configured. */
 Configured, 
 /** The Connected. */
 Connected, 
 /** The Scanned. */
 Scanned
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private long id = 0;
	
	/** The ssid. */
	private String SSID;
	
	/** The bssid. */
	private String BSSID;
	
	/** The type. */
	private WLANType type = null;

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public WLANType getType() {
		return type;
	}

	/**
	 * Gets the bssid.
	 *
	 * @return the bssid
	 */
	public String getBSSID() {
		return BSSID;
	}

	/**
	 * Sets the bssid.
	 *
	 * @param bSSID the new bssid
	 */
	public void setBSSID(String bSSID) {
		BSSID = bSSID;
	}

	/**
	 * Gets the ssid.
	 *
	 * @return the ssid
	 */
	public String getSSID() {
		return SSID;
	}

	/**
	 * Gets the BSSID value.
	 *
	 * @return the BSSID value
	 */
	public String getBSSIDValue() {
		return "x'" + BSSID.toUpperCase(Locale.ENGLISH).replaceAll(":", "")
				+ "'";
	}

	/**
	 * Instantiates a new WLAN item.
	 *
	 * @param SSID the ssid
	 * @param BSSID the bssid
	 * @param type the type
	 */
	public WLANItem(String SSID, String BSSID, WLANType type) {
		this.SSID = SSID;
		this.BSSID = BSSID;
		this.type = type;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE).replaceAll("BSSID=,", "");
	}

}
