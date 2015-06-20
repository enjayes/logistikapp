package com.tum.ident.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tum.ident.IdentificationListener;
import com.tum.ident.IdentificationService;
import java.io.Serializable;
import com.tum.ident.data.DataItem;



/**
 * The Class Broadcast.
 */
public class Broadcast extends BroadcastReceiver implements
		IdentificationListener {
	
	/* 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		IdentificationService.setListener(this);
		Intent startintent = new Intent(context, IdentificationService.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(startintent);
	}

	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveIds(java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceiveIds(String deviceID, String userID) {
	}

	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveUpdate()
	 */
	@Override
	public void onReceiveUpdate() {
	}

	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveDataSubmitted(boolean)
	 */
	@Override
	public void onReceiveDataSubmitted(boolean result) {
	}
	
	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveDataItem(com.tum.ident.data.DataItem)
	 */
	@Override
	public void onReceiveDataItem(DataItem item) {
	}
}