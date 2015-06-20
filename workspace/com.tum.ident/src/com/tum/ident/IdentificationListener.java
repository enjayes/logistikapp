package com.tum.ident;

import com.tum.ident.data.DataItem;


/**
 * The listener interface for receiving identification events.
 * The class that is interested in processing a identification
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIdentificationListener<code> method. When
 * the identification event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IdentificationEvent
 */
public interface IdentificationListener {

	/**
	 * On receive ids.
	 *
	 * @param deviceID the device id
	 * @param userID the user id
	 */
	public void onReceiveIds(String deviceID, String userID);

	/**
	 * On receive update.
	 */
	public void onReceiveUpdate();

	/**
	 * On receive data submitted.
	 *
	 * @param result the result
	 */
	public void onReceiveDataSubmitted(boolean result);
	
	/**
	 * On receive data item.
	 *
	 * @param item the item
	 */
	public void onReceiveDataItem(DataItem item);
}
