package com.tum.ident.calllog;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * The Class CallLogItem.
 */
public class CallLogItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The phone number. */
	private String phoneNumber;
	
	/** The call type. */
	private String callType;
	
	/** The call date. */
	private String callDate;
	
	/** The call duration. */
	private String callDuration;

	/** The phone number id. */
	private long phoneNumberID = 0;
	
	/** The user id. */
	private long userID = 0;

	/**
	 * Gets the phone number id.
	 *
	 * @return the phone number id
	 */
	public long getPhoneNumberID() {
		return phoneNumberID;
	}

	/**
	 * Sets the phone number id.
	 *
	 * @param phoneNumberID the new phone number id
	 */
	public void setPhoneNumberID(long phoneNumberID) {
		this.phoneNumberID = phoneNumberID;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userID the new user id
	 */
	public void setUserID(long userID) {
		this.userID = userID;
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
	 * Gets the call type.
	 *
	 * @return the call type
	 */
	public String getCallType() {
		return callType;
	}

	/**
	 * Gets the call date.
	 *
	 * @return the call date
	 */
	public String getCallDate() {
		return callDate;
	}

	/**
	 * Gets the call duration.
	 *
	 * @return the call duration
	 */
	public String getCallDuration() {
		return callDuration;
	}

	/**
	 * Instantiates a new call log item.
	 *
	 * @param phoneNumber the phone number
	 * @param callType the call type
	 * @param callDate the call date
	 * @param callDuration the call duration
	 */
	public CallLogItem(String phoneNumber, String callType, String callDate,
			String callDuration) {
		this.phoneNumber = phoneNumber;
		this.callType = callType;
		this.callDate = callDate;
		this.callDuration = callDuration;
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
