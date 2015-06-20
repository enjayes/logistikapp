package com.tum.ident.gait;

import java.io.Serializable;

import com.tum.ident.util.Util;


/**
 * The Class StepItem.
 */
public class StepItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;	
	
	/** The Constant TAG. */
	@SuppressWarnings("unused")
	transient private static final String TAG = "StepData";
	
	/** The radius. */
	private final int radius = 8;

	/** The duration. */
	private long duration;
	
	/** The start time. */
	private transient long startTime;
	
	/** The data. */
	private double[] data = null;
	
	/** The dtw value. */
	private double dtwValue = 0;
	
	/** The weight. */
	private double weight = 0;
	
	
	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Gets the step data.
	 *
	 * @return the step data
	 */
	public double[] getStepData() {
		return data;
	}

	/**
	 * Gets the dtw value.
	 *
	 * @return the dtw value
	 */
	public double getDtwValue() {
		return dtwValue;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets the distance value.
	 *
	 * @param distance the new distance value
	 */
	public void setDistanceValue(double distance) {
		dtwValue = distance;
	}

	/**
	 * Length.
	 *
	 * @return the int
	 */
	public int length(){
		return data.length;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public double[] getData(){
		return data;
	}
	
	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Instantiates a new step item.
	 *
	 * @param duration the duration
	 * @param data the data
	 */
	public StepItem(long duration, double[] data) {
		this.duration = duration;
		this.data = data;
		this.startTime = 0;
	}

	/**
	 * Instantiates a new step item.
	 *
	 * @param duration the duration
	 * @param startTime the start time
	 * @param data the data
	 */
	public StepItem(long duration, long startTime, double[] data) {
		this.duration = duration;
		this.data = data;
		this.startTime = startTime;
	}

	/**
	 * Dwt distance.
	 *
	 * @param stepItem the step item
	 * @return the double
	 */
	public double dwtDistance(StepItem stepItem) {
		double result = -1;
		if (data != null) {
			result = Util.dwtDistance(this.data, stepItem.data, radius);
		}
		return result;
	}
}
