package com.tum.ident.userdevice;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * The Class BluetoothItem.
 */
public class BluetoothItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The mac. */
	private String MAC;
	
	/** The name. */
	private String name;
	
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
	 * Gets the mac.
	 *
	 * @return the mac
	 */
	public String getMAC() {
		return MAC;
	}

	/**
	 * Gets the MAC value.
	 *
	 * @return the MAC value
	 */
	public String getMACValue() {
		return "x'" + MAC.toUpperCase(Locale.ENGLISH).replaceAll(":", "") + "'";
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Instantiates a new bluetooth item.
	 *
	 * @param MAC the mac
	 * @param name the name
	 */
	public BluetoothItem(String MAC, String name) {
		this.MAC = MAC;
		this.name = name;
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
