package com.tum.ident.result;

import android.util.Log;

import com.tum.ident.IdentificationListener;



/**
 * The Class ResultHandler.
 */
public class ResultHandler implements ResultListener {

	/** The tag. */
	private final String TAG = "ResultHandler";
	
	/** The listener. */
	private IdentificationListener listener = null;
	
	/** The internal listener. */
	private IdentificationListener internalListener = null;

	/**
	 * Instantiates a new result handler.
	 *
	 * @param listener the listener
	 * @param internalListener the internal listener
	 */
	public ResultHandler(IdentificationListener listener,
			IdentificationListener internalListener) {
		this.listener = listener;
		this.internalListener = internalListener;
	}

	/**
	 * Gets the return value.
	 *
	 * @param resultItem the result item
	 * @return the return value
	 */
	public boolean getReturnValue(ResultItem resultItem) {
		boolean result = false;
		if (resultItem != null) {
			if (resultItem.getType() == ResultItem.Type.Error) {
				result = false;
			} else if (resultItem.getType() == ResultItem.Type.ID) {
				ResultIDItem resultIDItem = (ResultIDItem) resultItem.getResult();
				Log.v(TAG, "resultIDItem.getType() = " + resultIDItem.getType());
				if (resultIDItem.getType() != ResultIDItem.Type.Error) {
					Log.v(TAG, "resultIDItem.getDeviceID() = "
							+ resultIDItem.getDeviceID());
					Log.v(TAG, "resultIDItem.getUserID() = " + resultIDItem.getUserID());
					if (resultIDItem.getDeviceID() != null
							&& resultIDItem.getUserID() != null) {
						result = true;
					}
				}
			} else if (resultItem.getType() == ResultItem.Type.Match) {
				ResultMatchItem resultMatchItem = (ResultMatchItem) resultItem.getResult();
				if (resultMatchItem.getType() != ResultMatchItem.Type.Error) {
					result = true;
				}
			} else if (resultItem.getType() == ResultItem.Type.Value) {
				ResultValueItem resultValueItem = (ResultValueItem) resultItem.getResult();
				if (resultValueItem.getResult() != ResultValueItem.Type.Error) {
					result = true;
				} else {
					Log.v("DEBUG",
							"resultValueItem.getResult() == ResultValueItem.Type.Error");
				}
			}
		}
		Log.v(TAG, "result = " + result);

		return result;
	}

	/* 
	 * @see com.tum.ident.result.ResultListener#onReceive(com.tum.ident.result.ResultItem)
	 */
	/* 
	 * @see com.tum.ident.result.ResultListener#onReceive(com.tum.ident.result.ResultItem)
	 */
	@Override
	public boolean onReceive(ResultItem resultItem) {
		Log.v(TAG, "ResultHandler.onReceive!!");
		boolean result = false;
		if (resultItem != null) {
			if (resultItem.getType() == ResultItem.Type.Error) {
				result = false;

			} else if (resultItem.getType() == ResultItem.Type.ID) {
				ResultIDItem resultIDItem = (ResultIDItem) resultItem.getResult();
				if (resultIDItem.getType() != ResultIDItem.Type.Error) {
					Log.v(TAG, "internalListener -> resultIDItem.getDeviceID() = "
							+ resultIDItem.getDeviceID());
					Log.v(TAG, "internalListener -> resultIDItem.getUserID() = "
							+ resultIDItem.getUserID());
					internalListener.onReceiveIds(resultIDItem.getDeviceID(),
							resultIDItem.getUserID());
					if (listener != null) {
						listener.onReceiveIds(resultIDItem.getDeviceID(),
								resultIDItem.getUserID());
					}
					result = true;
				}
			} else if (resultItem.getType() == ResultItem.Type.Match) {
				ResultMatchItem resultMatchItem = (ResultMatchItem) resultItem.getResult();
				if (resultMatchItem.getType() != ResultMatchItem.Type.Error) {
					result = true;
				}

			} else if (resultItem.getType() == ResultItem.Type.Value) {
				ResultValueItem resultValueItem = (ResultValueItem) resultItem.getResult();
				if (resultValueItem.getResult() != ResultValueItem.Type.Error) {
					result = true;
				}

			}
		}
		return result;
	}

	/* 
	 * @see com.tum.ident.result.ResultListener#onReceive(byte[])
	 */
	/* 
	 * @see com.tum.ident.result.ResultListener#onReceive(byte[])
	 */
	@Override
	public void onReceive(byte[] data) {

	}

	/* 
	 * @see com.tum.ident.result.ResultListener#onReceive(java.lang.String)
	 */
	/* 
	 * @see com.tum.ident.result.ResultListener#onReceive(java.lang.String)
	 */
	@Override
	public void onReceive(String result) {

	}
}
