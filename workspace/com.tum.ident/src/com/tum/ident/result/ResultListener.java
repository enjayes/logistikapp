package com.tum.ident.result;



/**
 * The listener interface for receiving result events.
 * The class that is interested in processing a result
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addResultListener<code> method. When
 * the result event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ResultEvent
 */
public interface ResultListener {
	
	/**
	 * On receive.
	 *
	 * @param resultItem the result item
	 * @return true, if successful
	 */
	public boolean onReceive(ResultItem resultItem);

	/**
	 * On receive.
	 *
	 * @param data the data
	 */
	public void onReceive(byte[] data);

	/**
	 * On receive.
	 *
	 * @param result the result
	 */
	public void onReceive(String result);
}
