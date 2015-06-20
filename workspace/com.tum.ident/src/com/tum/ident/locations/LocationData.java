package com.tum.ident.locations;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;
import com.tum.ident.storage.StorageHandler;



/**
 * The Class LocationData.
 */
public class LocationData implements Runnable, LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {

	/** The Constant TAG. */
	private static final String TAG = "LocationData";
	
	/** The area list. */
	private LocationAreaList areaList = new LocationAreaList();

	/** The send locations. */
	private boolean sendLocations = true;

	/** The latitude. */
	private double latitude;
	
	/** The longitude. */
	private double longitude;

	/** The context. */
	private Context context;
	
	/** The data controller. */
	private DataController dataController;
	
	/** The location manager. */
	private LocationManager locationManager;
	
	/** The location client. */
	private LocationClient locationClient;
	
	/** The listening. */
	private  boolean listening = false;
	
	/** The connected. */
	private  boolean connected = false;

	/** The last location. */
	private  Location lastLocation = null;
	
	/** The location received. */
	private  boolean locationReceived = false;

	/** The was gp senabled. */
	private  boolean wasGPSenabled = false;

	/** The location client listening. */
	private  boolean locationClientListening = false;
	
	/** The location manager listening. */
	private  boolean locationManagerListening = false;

	/** The running. */
	private boolean running = false;
	
	/** The current time. */
	private long currentTime;
	
	/** The start time. */
	private long startTime;

	/** The Constant REQUEST. */
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(0).setFastestInterval(0)
			.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

	/**
	 * Instantiates a new location data.
	 *
	 * @param context the context
	 * @param dataController the data controller
	 */
	public LocationData(Context context, DataController dataController) {
		this.context = context;
		this.dataController = dataController;
		latitude = 0;
		longitude = 0;
		locationClient = new LocationClient(context, this, this);
		locationClient.connect();
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		loadLocations();
		registerListener(false);
	}

	/**
	 * Register listener.
	 *
	 * @param force the force
	 */
	public void registerListener(boolean force) {
		toggleGPS(true);
		if (locationManagerListening == false) {

			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
			locationManagerListening = true;
			Log.v(TAG, "registerListener: " + LocationManager.GPS_PROVIDER);
		}
		if (locationClientListening == false) {
			if (connected) {
				locationClient.requestLocationUpdates(REQUEST, this);
				locationClientListening = true;
				Log.v(TAG, "registerListener (google): " + REQUEST);
			}
		}
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 0, 0,this);
		currentTime = System.currentTimeMillis();
		startTime = currentTime;
		if (running == false) {
			running = true;
			lastLocation = null;
			locationReceived = false;
			new Thread(this).start();
		}

	}

	/**
	 * Unregister listener.
	 */
	public void unregisterListener() {
		Log.v(TAG, "unregisterListeners");
		saveLocations();
		if (locationManagerListening) {
			locationManager.removeUpdates(this);
			locationManagerListening = false;
		}
		if (locationClientListening) {

			if (connected) {
				locationClient.removeLocationUpdates(this);
			}
			locationClientListening = false;
		}
		toggleGPS(false);
	}

	/**
	 * Gets the location string.
	 *
	 * @return the location string
	 */
	public String getLocationString() {
		return areaList.getLocationString();

	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		saveLocations();
		return new DataItem("", areaList);
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return "Number of Areas: " + areaList.size();
	}

	/**
	 * Gets the location area list.
	 *
	 * @return the location area list
	 */
	public LocationAreaList getLocationAreaList() {
		return areaList;
	}

	/** The location wait time. */
	public long locationWaitTime = 600000;
	
	/** The location timer. */
	public long locationTimer = 0;

	/* 
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	/* 
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		Log.v(TAG, "onLocationChanged");
		lastLocation = location;
		locationReceived = true;
	}

	/**
	 * Send locations.
	 *
	 * @param sendLocations the send locations
	 */
	public void sendLocations(boolean sendLocations) {
		this.sendLocations = sendLocations;
	}

	/**
	 * Load locations.
	 */
	public void loadLocations() {
		String fileName = "locations.ser";
		areaList = null;
		areaList = (LocationAreaList) StorageHandler.loadObject(fileName);
		if (areaList == null) {
			areaList = new LocationAreaList();
		}

	}

	/**
	 * Save locations.
	 */
	public void saveLocations() {
		String fileName = "locations.ser";
		Log.v(TAG, "Save Locations:" + fileName);
		StorageHandler.saveObject(areaList, fileName);

	}

	/**
	 * Gets the best location.
	 *
	 * @return the best location
	 */
	public Location getBestLocation() {
		Location bestResult = null;
		try {
			Location lastFusedLocation = null;
			if (connected) {
				lastFusedLocation = locationClient.getLastLocation();
			}
			Location gpsLocation = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Location networkLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (gpsLocation != null && networkLocation != null) {
				if (gpsLocation.getTime() > networkLocation.getTime())
					bestResult = gpsLocation;
			} else if (gpsLocation != null) {
				bestResult = gpsLocation;
			} else if (networkLocation != null) {
				bestResult = networkLocation;
			}
			if (bestResult != null && lastFusedLocation != null) {
				if (bestResult.getTime() < lastFusedLocation.getTime())
					bestResult = lastFusedLocation;
			}
			if (bestResult == null) {
				bestResult = lastLocation;
			}
		} catch (Exception e) {
			bestResult = null;
		}
		return bestResult;
	}

	/**
	 * Adds the location.
	 *
	 * @param location the location
	 */
	public void addLocation(Location location) {
		Log.v(TAG, "NEW LOCATION!!!!");
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			// double acc = location.getAccuracy();
			// double speed = location.getSpeed();
			Log.v(TAG, "onLocationChanged");
			if (lat != latitude && lng != longitude) {
				latitude = lat;
				longitude = lng;
				if (!areaList.addLocation(location)) {
					Log.v(TAG, "User moved to new Area!");
				}
				if (sendLocations) {
					dataController.addData("", areaList);
					sendLocations = false;
				}
			}
		}
	}

	/* 
	 * @see java.lang.Runnable#run()
	 */
	/* 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		currentTime = System.currentTimeMillis();
		while (currentTime - startTime < 300000) {
			currentTime = System.currentTimeMillis();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				Log.v(TAG, "Error: Thread.sleep()", e);
			}
			if (locationReceived) {
				Log.v(TAG, "locationReceived! connected: " + connected);
				if (connected) {
					break;
				} else if (currentTime - startTime > 5000) {
					break;
				}
			}

		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.v(TAG, "Error: Thread.sleep()", e);
		}
		Log.v(TAG, "wait for location finished!");
		Location location = getBestLocation();
		Log.v(TAG, "Location: " + location);
		addLocation(location);
		unregisterListener();
		locationReceived = false;
		running = false;
	}

	/**
	 * Toggle gps.
	 *
	 * @param activate the activate
	 */
	public void toggleGPS(boolean activate) {
		if (activate) {
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
				Intent intent = new Intent(
						"android.location.GPS_ENABLED_CHANGE");
				intent.putExtra("enabled", true);
				context.sendBroadcast(intent);
			}
			@SuppressWarnings("deprecation")
			String provider = Settings.Secure.getString(
					context.getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if (!provider.contains("gps")) {
				wasGPSenabled = false;
				// if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				context.sendBroadcast(poke);
			} else {
				wasGPSenabled = true;
			}
		} else {
			if (wasGPSenabled == false) {
				@SuppressWarnings("deprecation")
				String provider = Settings.Secure.getString(
						context.getContentResolver(),
						Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
				if (provider.contains("gps")) {
					final Intent poke = new Intent();
					poke.setClassName("com.android.settings",
							"com.android.settings.widget.SettingsAppWidgetProvider");
					poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
					poke.setData(Uri.parse("3"));
					context.sendBroadcast(poke);
				}
			}
		}
	}

	/* 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener#onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	/* 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener#onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	/* 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onConnected(android.os.Bundle)
	 */
	/* 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onConnected(android.os.Bundle)
	 */
	@Override
	public void onConnected(Bundle arg0) {
		Log.v(TAG, "connected!");
		connected = true;
		if (listening == true) {
			registerListener(true);
		}

	}

	/* 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onDisconnected()
	 */
	/* 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onDisconnected()
	 */
	@Override
	public void onDisconnected() {

		connected = false;
		// TODO Auto-generated method stub

	}

	/* 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	/* 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/* 
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	/* 
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/* 
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	/* 
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
