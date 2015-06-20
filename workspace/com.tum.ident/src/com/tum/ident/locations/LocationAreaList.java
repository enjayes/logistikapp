package com.tum.ident.locations;

import java.io.Serializable;
import java.util.ArrayList;

import android.location.Location;
import android.util.Log;



/**
 * The Class LocationAreaList.
 */
public class LocationAreaList implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant TAG. */
	private static final String TAG = "LocationData";
	
	/** The distance threshold max. */
	private double distanceThresholdMax = 8; // in km
	
	/** The distance threshold min. */
	private double distanceThresholdMin = 0.5; // in km

	/** The list. */
	private ArrayList<LocationArea> list = new ArrayList<LocationArea>();

	/**
	 * Gets the location string.
	 *
	 * @return the location string
	 */
	public String getLocationString() {
		String result = "";
		long counter = 1;
		for (LocationArea a : list) {
			result = result + "Area (" + counter + ")\n";
			result = result + "Center: (" + a.getCenter().getLatitude() + ","
					+ a.getCenter().getLongitude() + ") radius: " + a.getRadius() + "\n";

			for (LocationItem l : a.getLocations()) {

				result = result + l.toString() + "\n"; // "Location: ("+l.getLatitude()+","+l.longitude+") weight: "+l.counter+"\n";
			}
			result = result + "\n";
			counter++;
		}

		return result;

	}

	/**
	 * Size.
	 *
	 * @return the long
	 */
	public long size(){
		if(list!=null){
			return list.size();
		}
		else{
			return 0;
		}
	}
	
	
	/**
	 * Adds the location.
	 *
	 * @param location the location
	 * @return true, if successful
	 */
	public boolean addLocation(Location location) {

		Log.v(TAG,
				"add location: " + location.getLatitude() + " "
						+ location.getLongitude());

		LocationItem locationItem = new LocationItem();
		locationItem.setLatitude(location.getLatitude());
		locationItem.setLongitude(location.getLongitude());
		locationItem.updateCounter();

		boolean areaFound = false;
		for (LocationArea a : list) {
			double distance = locationItem.distanceTo(a.getCenter());
			if (distance <= (distanceThresholdMax + a.getRadius()) * 1000) {
				for (LocationItem l : a.getLocations()) {
					distance = locationItem.distanceTo(l);
					if (distance <= distanceThresholdMax * 1000) {
						if (distance > distanceThresholdMin) {
							a.getLocations().add(locationItem);
							a.getCenter().updateCounter();
							a.updateArea();
						} else {
							l.updateCounter();
						}
						areaFound = true;
						break;
					}
				}
			}
		}
		if (areaFound == false) {
			LocationArea newArea = new LocationArea();
			newArea.getCenter().updateCounter();
			newArea.getLocations().add(locationItem);
			newArea.updateArea();
			list.add(newArea);

		}
		return areaFound;

	}
}
