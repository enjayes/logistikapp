package com.tum.ident.locations;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.tum.ident.util.Util;



/**
 * The Class LocationItem.
 */
public class LocationItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The timecounter. */
	private long[][] timecounter = new long[7][24];
	
	/** The counter. */
	private long counter;
	
	/** The latitude. */
	private double latitude;
	
	/** The longitude. */
	private double longitude;
	
	
	
	
	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public long getCounter() {
		return counter;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Instantiates a new location item.
	 */
	public LocationItem() {
		counter = 1;
	}

	/**
	 * Distance to.
	 *
	 * @param locationItem the location item
	 * @return the double
	 */
	public double distanceTo(LocationItem locationItem) {
		double lat2 = locationItem.latitude;
		double lon2 = locationItem.longitude;
		double theta = longitude - lon2;
		double dist = Math.sin(Util.deg2rad(latitude))
				* Math.sin(Util.deg2rad(lat2))
				+ Math.cos(Util.deg2rad(latitude))
				* Math.cos(Util.deg2rad(lat2)) * Math.cos(Util.deg2rad(theta));
		dist = Math.acos(dist);
		dist = Util.rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return dist;
	}

	/**
	 * Update counter.
	 */
	public void updateCounter() {
		Calendar calendar = Calendar.getInstance();
		int time = calendar.get(Calendar.HOUR_OF_DAY);
		int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		timecounter[day][time] = timecounter[day][time] + 1;
		counter++;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
