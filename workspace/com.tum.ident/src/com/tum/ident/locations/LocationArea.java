package com.tum.ident.locations;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The Class LocationArea.
 */
public class LocationArea implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TAG. */
	@SuppressWarnings("unused")
	private static final String TAG = "SensorData";
	
	/** The radius. */
	private long radius;
	
	/** The center. */
	private LocationItem center;
	
	/** The locations. */
	private ArrayList<LocationItem> locations;
	
	

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public long getRadius() {
		return radius;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(long radius) {
		this.radius = radius;
	}

	/**
	 * Gets the center.
	 *
	 * @return the center
	 */
	public LocationItem getCenter() {
		return center;
	}

	/**
	 * Sets the center.
	 *
	 * @param center the new center
	 */
	public void setCenter(LocationItem center) {
		this.center = center;
	}

	/**
	 * Gets the locations.
	 *
	 * @return the locations
	 */
	public ArrayList<LocationItem> getLocations() {
		return locations;
	}

	/**
	 * Sets the locations.
	 *
	 * @param locations the new locations
	 */
	public void setLocations(ArrayList<LocationItem> locations) {
		this.locations = locations;
	}

	/**
	 * Instantiates a new location area.
	 */
	public LocationArea() {
		center = new LocationItem();
		locations = new ArrayList<LocationItem>();
	}

	/**
	 * Update area.
	 */
	public void updateArea() {
		if (center == null) {
			center = new LocationItem();
		}
		if (locations == null) {
			locations = new ArrayList<LocationItem>();
		}
		if (locations.size() > 0) {
			double avgLat = 0;
			double avgLong = 0;
			for (LocationItem l : locations) {
				avgLat = avgLat + l.getLatitude();
				avgLong = avgLong + l.getLongitude();
			}
			avgLat = avgLat / locations.size();
			avgLong = avgLong / locations.size();
			center.setLatitude( avgLat);
			center.setLongitude(avgLong);
			radius = 80; // todo
		}

	}

	/**
	 * Compare areas.
	 *
	 * @param al1 the al1
	 * @param al2 the al2
	 * @return the double
	 */
	public static double compareAreas(ArrayList<LocationArea> al1,
			ArrayList<LocationArea> al2) {
		double avgDistances = 0;

		for (LocationArea a1 : al1) {
			for (LocationArea a2 : al2) {
				double distance = a1.center.distanceTo(a2.center);

				double avgDistance = 0;

				if (distance <= a1.radius + a2.radius) {
					if (a1.locations.size() > 0 || a2.locations.size() > 0) {
						long num = a1.locations.size() * a2.locations.size();
						for (LocationItem l1 : a1.locations) {
							for (LocationItem l2 : a2.locations) {
								distance = l1.distanceTo(l2);
								avgDistance = avgDistance + distance;
							}
							avgDistance = avgDistance / num;
						}

					}
					avgDistances = avgDistances + avgDistance;
				}
			}
		}
		return avgDistances;
	}

}
