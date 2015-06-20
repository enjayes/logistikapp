/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.util;



/**
 * The Class EuclideanDistance.
 */
public class EuclideanDistance implements DistanceFunction {
	
	/**
	 * Instantiates a new euclidean distance.
	 */
	public EuclideanDistance() {

	}

	/* 
	 * @see com.tum.ident.fastdtw.util.DistanceFunction#calcDistance(double[], double[])
	 */
	/* 
	 * @see com.tum.ident.fastdtw.util.DistanceFunction#calcDistance(double[], double[])
	 */
	@Override
	public double calcDistance(double[] vector1, double[] vector2) {
		if (vector1.length != vector2.length)
			throw new RuntimeException("ERROR:  cannot calculate the distance "
					+ "between vectors of different sizes.");

		double sqSum = 0.0;
		for (int x = 0; x < vector1.length; x++)
			sqSum += Math.pow(vector1[x] - vector2[x], 2.0);

		return Math.sqrt(sqSum);
	} // end class euclideanDist(..)

}