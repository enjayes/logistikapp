/*
 * TimeWarpInfo.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;



/**
 * The Class TimeWarpInfo.
 */
public class TimeWarpInfo {
	// PRIVATE DATA
	/** The distance. */
	private final double distance;
	
	/** The path. */
	private final WarpPath path;

	// CONSTRUCTOR
	/**
	 * Instantiates a new time warp info.
	 *
	 * @param dist the dist
	 * @param wp the wp
	 */
	TimeWarpInfo(double dist, WarpPath wp) {
		distance = dist;
		path = wp;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public WarpPath getPath() {
		return path;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(Warp Distance=" + distance + ", Warp Path=" + path + ")";
	}

} // end class TimeWarpInfo
