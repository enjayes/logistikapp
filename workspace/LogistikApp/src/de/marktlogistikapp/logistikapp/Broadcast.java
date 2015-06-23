package de.marktlogistikapp.logistikapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;




/**
 * The Class Broadcast.
 */
public class Broadcast extends BroadcastReceiver  {
	
	/* 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent startintent = new Intent(context, LogistikApp.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(startintent);
	}

}