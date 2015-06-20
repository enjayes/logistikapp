package com.tum.ident.system;

import java.io.Serializable;


/**
 * The Class SDCardItem.
 */
public class SDCardItem  implements Serializable  {
	
	/** The sid. */
	private String SID;
	
	/**
	 * Sets the sid.
	 *
	 * @param id the new sid
	 */
	public void setSID(String id){
		SID = id;
	}
	
	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	public String getCID(){
		return SID;
	}
}
