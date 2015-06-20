/*
 * FastDTW.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;

import com.tum.ident.fastdtw.timeseries.PAA;
import com.tum.ident.fastdtw.timeseries.TimeSeries;
import com.tum.ident.fastdtw.util.DistanceFunction;



/**
 * The Class FastDTW.
 */
public class FastDTW {
	// CONSTANTS
	/** The Constant DEFAULT_SEARCH_RADIUS. */
	final static int DEFAULT_SEARCH_RADIUS = 1;

	/**
	 * Gets the warp dist between.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param distFn the dist fn
	 * @return the warp dist between
	 */
	public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ,
			DistanceFunction distFn) {
		return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getDistance();
	}

	/**
	 * Gets the warp dist between.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param searchRadius the search radius
	 * @param distFn the dist fn
	 * @return the warp dist between
	 */
	public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ,
			int searchRadius, DistanceFunction distFn) {
		return fastDTW(tsI, tsJ, searchRadius, distFn).getDistance();
	}

	/**
	 * Gets the warp path between.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param distFn the dist fn
	 * @return the warp path between
	 */
	public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ,
			DistanceFunction distFn) {
		return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getPath();
	}

	/**
	 * Gets the warp path between.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param searchRadius the search radius
	 * @param distFn the dist fn
	 * @return the warp path between
	 */
	public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ,
			int searchRadius, DistanceFunction distFn) {
		return fastDTW(tsI, tsJ, searchRadius, distFn).getPath();
	}

	/**
	 * Gets the warp info between.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param searchRadius the search radius
	 * @param distFn the dist fn
	 * @return the warp info between
	 */
	public static TimeWarpInfo getWarpInfoBetween(TimeSeries tsI,
			TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
		return fastDTW(tsI, tsJ, searchRadius, distFn);
	}

	/**
	 * Fast dtw.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param searchRadius the search radius
	 * @param distFn the dist fn
	 * @return the time warp info
	 */
	private static TimeWarpInfo fastDTW(TimeSeries tsI, TimeSeries tsJ,
			int searchRadius, DistanceFunction distFn) {
		if (searchRadius < 0)
			searchRadius = 0;

		final int minTSsize = searchRadius + 2;

		if ((tsI.size() <= minTSsize) || (tsJ.size() <= minTSsize)) {
			// Perform full Dynamic Time Warping.
			return DTW.getWarpInfoBetween(tsI, tsJ, distFn);
		} else {
			final double resolutionFactor = 2.0;

			final PAA shrunkI = new PAA(tsI,
					(int) (tsI.size() / resolutionFactor));
			final PAA shrunkJ = new PAA(tsJ,
					(int) (tsJ.size() / resolutionFactor));

			// Determine the search window that constrains the area of the cost
			// matrix that will be evaluated based on
			// the warp path found at the previous resolution (smaller time
			// series).
			final SearchWindow window = new ExpandedResWindow(tsI, tsJ,
					shrunkI, shrunkJ, FastDTW.getWarpPathBetween(shrunkI,
							shrunkJ, searchRadius, distFn), searchRadius);

			// Find the optimal warp path through this search window constraint.
			return DTW.getWarpInfoBetween(tsI, tsJ, window, distFn);
		} // end if
	} // end recFastDTW(...)

} // end class fastDTW
