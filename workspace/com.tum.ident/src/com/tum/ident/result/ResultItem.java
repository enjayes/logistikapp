package com.tum.ident.result;

import java.io.Serializable;



/**
 * The Class ResultItem.
 */
public class ResultItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private  Type type;
	
	/** The result. */
	private  Object result;

	/**
	 * The Enum Type.
	 */
	public enum Type {
		
		/** The id. */
		ID, 
 /** The Match. */
 Match, 
 /** The Error. */
 Error, 
 /** The Value. */
 Value
	}

	/**
	 * Instantiates a new result item.
	 */
	public ResultItem() {
		this.type = Type.Error;
		this.result = null;
	}

	/**
	 * Instantiates a new result item.
	 *
	 * @param type the type
	 * @param result the result
	 */
	public ResultItem(Type type, Object result) {
		this.type = type;
		this.result = result;
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
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(Object result) {
		this.result = result;
	}



}
