package de.marktlogistikapp.logistikapp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class KioskService extends Service {

	  private static final long INTERVAL = 100;//TimeUnit.SECONDS.toMillis(2); // periodic interval to check in seconds -> 2 seconds
	  private static final String TAG = KioskService.class.getSimpleName();
	  private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";

	  private Thread t = null;
	  private Context ctx = null;
	  private boolean running = false;
	  ActivityManager am;
	  String AppName;
	  @Override
	  public void onDestroy() {
	    Log.i(TAG, "Stopping service 'KioskService'");
	    running =false;
	    super.onDestroy();
	  }

	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    Log.i(TAG, "Starting service 'KioskService'");
	    running = true;
	    ctx = this;
	    am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
	    AppName = ctx.getApplicationContext().getPackageName();
	    // start a thread that periodically checks if your app is in the foreground
	    t = new Thread(new Runnable() {
	      @Override
	      public void run() {
	        do {
	          handleKioskMode();
	          try {
	            Thread.sleep(INTERVAL);
	          } catch (InterruptedException e) {
	            Log.i(TAG, "Thread interrupted: 'KioskService'");
	          }
	        }while(running);
	        stopSelf();
	      }
	    });

	    t.start();
	    return Service.START_NOT_STICKY;
	  }

	  private void handleKioskMode() {
	    // is Kiosk Mode active? 
		
	      if(isKioskModeActive(ctx)) {
	        // is App in background?
	      if(isInBackground()) {
	        restoreApp(); // restore!
	      }
	    }
	  }

	  private boolean isInBackground() {
	    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
	    ComponentName componentInfo = taskInfo.get(0).topActivity;
	    return (!AppName.equals(componentInfo.getPackageName()));
	  }

	  private void restoreApp() {
		//todo
	    // Restart activity
		/*
	    Intent i = new Intent(ctx, LogistikApp.class);
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    ctx.startActivity(i);
	    */
	    return;
	  }
	  
	  public boolean isKioskModeActive(final Context context) {
	    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
	    return true;// sp.getBoolean(PREF_KIOSK_MODE, false);
	  }

	  @Override
	  public IBinder onBind(Intent intent) {
	    return null;
	  }
	}