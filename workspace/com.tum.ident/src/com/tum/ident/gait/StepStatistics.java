package com.tum.ident.gait;

import java.io.Serializable;



/**
 * The Class StepStatistics.
 */
public class StepStatistics implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The clusters. */
	private StepClusterList clusters = new StepClusterList();
	
	/** The counter. */
	private StepCounter counter = new StepCounter();
	
	/**
	 * Gets the clusters.
	 *
	 * @return the clusters
	 */
	public StepClusterList getClusters(){
		return clusters;
	}
	
	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public StepCounter getCounter(){
		return counter;
	}

	/**
	 * Sets the clusters.
	 *
	 * @param c the new clusters
	 */
	public void setClusters(StepClusterList c) {
		clusters = c;
		if (clusters == null) {
			clusters = new StepClusterList();
		}
		else if (clusters.getList() == null) {
			clusters = new StepClusterList();
		}
	}

	/**
	 * Sets the counter.
	 *
	 * @param c the new counter
	 */
	public void setCounter(StepCounter c) {
		counter = c;
		if (counter == null) {
			counter = new StepCounter();
		}
	}
	
	
	
}
