package de.marktlogistikapp.logistikapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

public class Besucherschein extends Application {
	
	 private Besucherschein instance;
	  private PowerManager.WakeLock wakeLock;
	  private OnScreenOffReceiver onScreenOffReceiver;
	
	public void onCreate() {
	    super.onCreate();
	    instance = this;
	    registerKioskModeScreenOffReceiver();
	    startKioskService();  // add this
	  }

	  private void registerKioskModeScreenOffReceiver() {
	    // register screen off receiver
	    final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
	    onScreenOffReceiver = new OnScreenOffReceiver();
	    registerReceiver(onScreenOffReceiver, filter);
	  }

	  private void startKioskService() { // ... and this method
		  startService(new Intent(this, KioskService.class));
	  }
	  
	  public PowerManager.WakeLock getWakeLock() {
	    if(wakeLock == null) {
	      // lazy loading: first call, create wakeLock via PowerManager.
	      PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	      wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
	    }
	    return wakeLock;
	  }
}
