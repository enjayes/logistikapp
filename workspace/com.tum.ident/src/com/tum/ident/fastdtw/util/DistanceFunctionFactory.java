/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.util;



/**
 * A factory for creating DistanceFunction objects.
 */
public class DistanceFunctionFactory {
	
	/** The euclidean dist fn. */
	public static DistanceFunction EUCLIDEAN_DIST_FN = new EuclideanDistance();
	
	/** The manhattan dist fn. */
	public static DistanceFunction MANHATTAN_DIST_FN = new ManhattanDistance();
	
	/** The binary dist fn. */
	public static DistanceFunction BINARY_DIST_FN = new BinaryDistance();

	/**
	 * Gets the dist fn by name.
	 *
	 * @param distFnName the dist fn name
	 * @return the dist fn by name
	 */
	public static DistanceFunction getDistFnByName(String distFnName) {
		if (distFnName.equals("EuclideanDistance")) {
			return EUCLIDEAN_DIST_FN;
		} else if (distFnName.equals("ManhattanDistance")) {
			return MANHATTAN_DIST_FN;
		} else if (distFnName.equals("BinaryDistance")) {
			return BINARY_DIST_FN;
		} else {
			throw new IllegalArgumentException(
					"There is no DistanceFunction for the name " + distFnName);
		} // end if
	}
}