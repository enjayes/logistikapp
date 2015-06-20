package com.tum.ident.gait;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * The Class StepCounter.
 */
public class StepCounter implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The step weights. */
	public int[] stepWeights = new int[7];
	
	/** The first step time. */
	public long[] firstStepTime = new long[7];
	
	/** The last step time. */
	public long[] lastStepTime = new long[7];
	
	/** The steps. */
	public double[] steps = new double[7];
	
	/** The first step time avg. */
	public double firstStepTimeAvg;
	
	/** The last step time avg. */
	public double lastStepTimeAvg;
	
	/** The last step time cache. */
	public long lastStepTimeCache = 0;
	
	/** The avgsteps. */
	public double avgsteps;
	
	/** The step day offset. */
	public int stepDayOffset = 0;
	
	/** The current day of year. */
	public int currentDayOfYear = -1;
	
	/** The current day. */
	public int currentDay = -1;
	
	/** The current steps. */
	public int currentSteps = 0;

	/**
	 * Update average.
	 */
	private void updateAverage() {
		double avg = 0;
		double weight = 0;
		double firstAvg = 0;
		double lastAvg = 0;
		for (int i = 0; i < 7; i++) {
			if (steps[i] > 0) {
				avg = avg + steps[i] * stepWeights[i];
				weight = weight + stepWeights[i];
				firstAvg = firstAvg + firstStepTime[i] * stepWeights[i];
				lastAvg = lastAvg + lastStepTime[i] * stepWeights[i];
			}
		}
		if (weight > 0) {
			avgsteps = avg / weight;
			firstStepTimeAvg = firstAvg / weight;
			lastStepTimeAvg = lastAvg / weight;
		} else {
			avgsteps = 0;
		}
	}

	/**
	 * New day.
	 */
	public void newDay() {
		if (lastStepTime == null) {
			lastStepTime = new long[7];
		}
		if (stepWeights == null) {
			stepWeights = new int[7];
		}
		if (firstStepTime == null) {
			firstStepTime = new long[7];
		}
		if (steps == null) {
			steps = new double[7];
		}
		if (currentDay != -1) {
			int day = currentDay;
			lastStepTime[day] = lastStepTimeCache;
			steps[day] = (steps[day] * stepWeights[day] + currentSteps)
					/ ((double) stepWeights[day] + 1);
			stepWeights[day]++;
			currentSteps = 0;
			updateAverage();
		}
		Calendar calendar = Calendar.getInstance();
		currentDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		firstStepTime[currentDay] = hours * 3600 + minutes * 60 + seconds;
	}

	/**
	 * Sets the last step time.
	 */
	public void setLastStepTime() {
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		lastStepTimeCache = hours * 3600 + minutes * 60 + seconds;
	}

	/**
	 * Sets the steps.
	 *
	 * @param s the s
	 * @return the int
	 */
	public int setSteps(int s) {
		int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if (dayOfYear != currentDayOfYear) {
			newDay();
			stepDayOffset = s;
		}
		currentDayOfYear = dayOfYear;
		currentSteps = s - stepDayOffset;
		setLastStepTime();
		return currentSteps;
	}

	/**
	 * Adds the step.
	 *
	 * @return the int
	 */
	public int addStep() {
		int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if (dayOfYear != currentDayOfYear) {
			newDay();
			stepDayOffset = 0;
		} else {
			currentSteps = currentSteps + 1;
		}
		currentDayOfYear = dayOfYear;
		setLastStepTime();
		return currentSteps;

	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
