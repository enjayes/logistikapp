package com.tum.ident.result;

import java.io.Serializable;



/**
 * The Class ResultValueItem.
 */
public class ResultValueItem implements Serializable {
	
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
	
	/** The result. */
	private Type result;

	/**
	 * Instantiates a new result value item.
	 *
	 * @param result the result
	 */
	public ResultValueItem(Type result) {
		this.result = result;
	}

	/**
	 * Instantiates a new result value item.
	 *
	 * @param ok the ok
	 */
	public ResultValueItem(boolean ok) {
		if (ok) {
			this.result = Type.OK;
		} else {
			this.result = Type.Error;
		}
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Type getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(Type result) {
		this.result = result;
	}

	

}
