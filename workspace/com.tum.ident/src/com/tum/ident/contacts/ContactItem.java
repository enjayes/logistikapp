package com.tum.ident.contacts;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * The Class ContactItem.
 */
public class ContactItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id contact. */
	private String idContact;
	
	/** The phone number. */
	private String phoneNumber;
	
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
	 * Gets the id contact.
	 *
	 * @return the id contact
	 */
	public String getIdContact() {
		return idContact;
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
	 * Instantiates a new contact item.
	 *
	 * @param idContact the id contact
	 * @param phoneNumber the phone number
	 */
	public ContactItem(String idContact, String phoneNumber) {
		this.idContact = idContact;
		this.phoneNumber = phoneNumber; // todo: Normalize Phone Number
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
