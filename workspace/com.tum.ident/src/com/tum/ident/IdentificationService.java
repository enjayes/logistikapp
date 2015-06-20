package com.tum.ident;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.SurfaceHolder;

import com.tum.ident.R;
import com.tum.ident.data.DataController;
import com.tum.ident.network.NetworkClient;
import com.tum.ident.storage.StorageHandler;
import com.tum.ident.update.UpdateActivity;
import com.tum.ident.util.HashGenerator;


/**
 * The Class IdentificationService.
 */
public class IdentificationService extends Service {

	/** The tag. */
	private static String TAG = "IdentificationService";

	/** The data controller. */
	private static DataController dataController;

	/** The surface holder. */
	private static SurfaceHolder surfaceHolder;

	/** The listener. */
	private static IdentificationListener listener = null;

	/** The service running. */
	private static boolean serviceRunning = false;

	/** The my service binder. */
	private static IdentificationService myServiceBinder;

	/** The m binder. */
	private final IBinder mBinder = new MyBinder();
	
	/** The out messenger. */
	@SuppressWarnings("unused")
	private Messenger outMessenger;
	
	/** The Identification connection. */
	private static ServiceConnection IdentificationConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			myServiceBinder = ((IdentificationService.MyBinder) binder)
					.getService();

			Log.d("ServiceConnection", "connected");

		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			Log.d("ServiceConnection", "disconnected");
			myServiceBinder = null;
		}
	};

	
	/** The handler. */
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			//Bundle data = message.getData();
		}
	};

	/**
	 * Bind.
	 *
	 * @param context the context
	 */
	public static void bind(Context context) {
		Intent intent = null;
		intent = new Intent(context, IdentificationService.class);
		Messenger messenger = new Messenger(handler);
		intent.putExtra("MESSENGER", messenger);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.bindService(intent, IdentificationConnection,
				Context.BIND_AUTO_CREATE);
	}

	/**
	 * Unbind.
	 *
	 * @param context the context
	 */
	public static void unbind(Context context) {
		if (myServiceBinder != null) {
			context.unbindService(IdentificationConnection);
			myServiceBinder = null;
		}
	}
	
	
	/**
	 * Sets the listener.
	 *
	 * @param l the new listener
	 */
	public static void setListener(IdentificationListener l) {
		listener = l;
	}

	/**
	 * Sets the surface holder.
	 *
	 * @param s the new surface holder
	 */
	public static void setSurfaceHolder(SurfaceHolder s) {
		surfaceHolder = s;
	}

	/**
	 * Sets the server url.
	 *
	 * @param s the new server url
	 */
	public static void setServerURL(String s) {
		IdentificationConfiguration.config.serverURL = s;
	}

	/**
	 * Send data.
	 *
	 * @param notification the notification
	 */
	public static void sendData(boolean notification) {
		if (dataController != null) {
			dataController.sendDataRunnable(notification);
		}
	}

	/* 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	/**
	 * Gets the data.
	 *
	 * @param type the type
	 * @return the data
	 */
	public static DataController getData(String type) {
		return dataController;
	}
	
	/**
	 * Start identification.
	 */
	private void startIdentification() {

		Log.v(TAG, "Service started...");
		Context context = getApplicationContext();
		NetworkClient.setContext(context);
		StorageHandler.setContext(context);
		HashGenerator.setHashLength(IdentificationConfiguration.hashLength);

		IdentificationConfiguration.init();
		if (IdentificationConfiguration.config.serverURL.length() > 0) {
			Log.v(TAG, "context: " + context);
			if (serviceRunning == false || dataController == null) {
				Log.v(TAG, "new DataController!!!!");
				dataController = new DataController(
						IdentificationConfiguration.config.serverURL, context,
						surfaceHolder, listener);
				serviceRunning = true;
				dataController.setService(this);
			} else {
				Log.v(TAG, "setParameter!!!!");
				dataController.setParameter(
						IdentificationConfiguration.config.serverURL, context,
						surfaceHolder, listener);
				dataController.setService(this);
				serviceRunning = true;
			}
		}
	}

	/* 
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		if (intent != null
				&& IdentificationConfiguration.config.serverURL.length() > 0) {
			startIdentification();
		}
	}

	/* 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		if (intent != null
				&& IdentificationConfiguration.config.serverURL.length() > 0) {
			startIdentification();
		}
		return START_STICKY;
	}

	/* 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		Log.v(TAG, "Service destroyed!!!");
		dataController.release();
		dataController = null;
		super.onDestroy();

		Intent intent = null;
		intent = new Intent(this, IdentificationService.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);

	}
	
	/* 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		Bundle extras = arg0.getExtras();
		Log.d("service", "onBind");
		// Get messager from the Activity
		if (extras != null) {
			Log.v(TAG, "onBind with extra");
			outMessenger = (Messenger) extras.get("MESSENGER");
		}

		startIdentification();

		return mBinder;
	}

	/**
	 * The Class MyBinder.
	 */
	private class MyBinder extends Binder {
		
		/**
		 * Gets the service.
		 *
		 * @return the service
		 */
		IdentificationService getService() {
			return IdentificationService.this;
		}
	}
	
//Just for Evaluation-App-----------------------------
	
	/**
 * Gets the data contoller.
 *
 * @return the data contoller
 */
public static DataController getDataContoller(){ 
		return dataController;
	}

	/**
	 * Debug notification.
	 *
	 * @param id the id
	 * @param text the text
	 */
	@SuppressLint("InlinedApi")
	@SuppressWarnings("deprecation")
	public void debugNotification(int id, String text) {
		Notification notification = new Notification(R.drawable.notification,
				getText(R.string.app_name), System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, UpdateActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notificationIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this, getText(R.string.app_name), text,
				pendingIntent);
		startForeground(id, notification);
	}
	
	/**
	 * Check update notification.
	 *
	 * @param update the update
	 */
	@SuppressLint("InlinedApi")
	@SuppressWarnings("deprecation")
	public void checkUpdateNotification(boolean update) {

		if (update) {
			Notification notification = new Notification(
					R.drawable.notification, getText(R.string.app_name),
					System.currentTimeMillis());
			Intent notificationIntent = new Intent(this, UpdateActivity.class);
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			notificationIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(this, getText(R.string.app_name),
					"An update is available, click to download it. ",
					pendingIntent);
			startForeground(2014, notification);
		} else {
			Notification notification = new Notification(
					R.drawable.notification_gray, getText(R.string.app_name),
					System.currentTimeMillis());
			Intent notificationIntent = new Intent(this,
					IdentificationService.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(this, getText(R.string.app_name),
					"No updates avalaible.", pendingIntent);
			startForeground(2014, notification);
		}
	}


	/*
	 * @Override public int onStartCommand(Intent intent, int flags, int
	 * startId) { return Service.START_STICKY; }
	 */
}