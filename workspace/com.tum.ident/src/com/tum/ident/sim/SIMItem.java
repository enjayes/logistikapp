package com.tum.ident.sim;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * The Class SIMItem.
 */
public class SIMItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The imsi string. */
	private String imsiString;
	
	/** The sim id. */
	private String simID;
	
	/** The phone number. */
	private String phoneNumber;
	
	/** The id. */
	private long id;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the imsi string.
	 *
	 * @return the imsi string
	 */
	public String getImsiString() {
		return imsiString;
	}

	/**
	 * Sets the imsi string.
	 *
	 * @param imsiString the new imsi string
	 */
	public void setImsiString(String imsiString) {
		this.imsiString = imsiString;
	}

	/**
	 * Gets the sim id.
	 *
	 * @return the sim id
	 */
	public String getSimID() {
		return simID;
	}

	/**
	 * Sets the sim id.
	 *
	 * @param simID the new sim id
	 */
	public void setSimID(String simID) {
		this.simID = simID;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Instantiates a new SIM item.
	 *
	 * @param imsiString the imsi string
	 * @param simID the sim id
	 * @param phoneNumber the phone number
	 */
	public SIMItem(String imsiString, String simID, String phoneNumber) {
		this.imsiString = imsiString;
		this.simID = simID;
		this.phoneNumber = phoneNumber;
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
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
