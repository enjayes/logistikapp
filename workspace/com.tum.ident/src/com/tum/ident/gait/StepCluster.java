package com.tum.ident.gait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


//import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.tum.ident.IdentificationConfiguration;
import com.tum.ident.camera.CameraData;
import com.tum.ident.data.DataFactory;
import com.tum.ident.sensors.SensorData;
import com.tum.ident.util.Util;


/**
 * The Class StepCluster.
 */
public class StepCluster implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The radius. */
	private final int radius = 8;
	
	/** The Constant TAG. */
	private static final String TAG = "StepData";
	
	/** The steps. */
	private ArrayList<StepItem> steps = new ArrayList<StepItem>(); // todo
																	// transient;
	/** The data. */
																	private  byte[] data = null;
	
	/** The avg steps. */
	private  StepItem avgSteps = null;
	
	/** The duration. */
	private  long duration;
	
	/** The iterator. */
	private  transient Iterator<StepItem> iterator = steps.iterator();
	
	/** The merge. */
	private  boolean merge = false;
	
	/** The num steps. */
	private  long numSteps = 0;
	
	/** The min acceleration. */
	private double minAcceleration = -1;
	
	/** The max acceleration. */
	private double maxAcceleration = 1;
	
	/** The changed. */
	private transient boolean changed = true;
	
	/** The dtw sum max. */
	private  double dtwSumMax = 0;
	
	/** The dtw sum min. */
	private  double dtwSumMin = IdentificationConfiguration.dwtThreshold * 10;
	
	/** The max step list size. */
	private  long maxStepListSize = 20;
	
	/** The last update time. */
	private  long lastUpdateTime = 0;

	/** The distance puffer. */
	private  transient  double distancePuffer = 0;
	
	/** The distance puffer item. */
	private  transient StepItem distancePufferItem = null;
	
	/** The new cluster. */
	private  transient boolean newCluster = false;
	
	/** The cluster found. */
	private  transient boolean clusterFound = false;
	
	/** The index. */
	private  long index;

	/** The time puffer. */
	private transient double timePuffer[] = null;

	/**
	 * Instantiates a new step cluster.
	 *
	 * @param clusterIndex the cluster index
	 */
	public StepCluster(long clusterIndex) {
		this.index = clusterIndex;
		this.newCluster = true;
	}

	/**
	 * Prepare.
	 *
	 * @param mode the mode
	 */
	@SuppressWarnings("unchecked")
	public void prepare(boolean mode) {
		if (mode == true) {
			if (steps.size() > 0) {
				data = DataFactory.getData(steps);
			}
			steps.clear();
		} else {
			if (data != null) {
				if (data.length > 0) {
					ArrayList<StepItem> loadSteps = (ArrayList<StepItem>) DataFactory
							.getObject(data);
					if (loadSteps != null) {
						steps.clear();
						steps = loadSteps;
					}
				}
				if (steps == null) {
					steps = new ArrayList<StepItem>();
				}
				if (numSteps < steps.size()) {
					numSteps = steps.size();
				}
				data = null;
			}
		}

	}

	/**
	 * Size.
	 *
	 * @return the long
	 */
	public long size() {
		return numSteps;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration(){
		return duration/numSteps;
	}
	
	/**
	 * Clean.
	 */
	public void clean() {
		while (steps.size() > maxStepListSize) {
			double worst = 0;
			StepItem worstItem = null;
			Iterator<StepItem> i = steps.iterator();
			while (i.hasNext()) {
				StepItem stepItem = i.next();
				if (stepItem.getDtwValue() > worst) {
					worstItem = stepItem;
					worst = stepItem.getDtwValue();
					dtwSumMax = worst;
				}
			}
			if (worstItem != null) {
				steps.remove(worstItem);
			} else
				break;
		}
		dtwSumMax = 0;
		dtwSumMin = IdentificationConfiguration.dwtThreshold * 10;
		for (StepItem stepItem : steps) {
			if (stepItem.getDtwValue() > dtwSumMax) {
				dtwSumMax = stepItem.getDtwValue();
			}
			if (stepItem.getDtwValue() < dtwSumMin) {
				dtwSumMin = stepItem.getDtwValue();
			}
		}
		changed = true;
	}

	/**
	 * Adds the.
	 *
	 * @param stepItem the step item
	 */
	public void add(StepItem stepItem) {
		numSteps++;
		steps.add(stepItem);
		duration = (duration + stepItem.getDuration());
		if (steps.size() > 2) {
			if (distancePufferItem != stepItem || distancePuffer == 0) {
				distancePuffer = dwtAvgDistance(stepItem);
			}
			stepItem.setDistanceValue(distancePuffer);

			if (stepItem.getDtwValue() > dtwSumMax) {
				dtwSumMax = stepItem.getDtwValue();
			}
			if (stepItem.getDtwValue() < dtwSumMin) {
				dtwSumMin = stepItem.getDtwValue();
			}
		}
		if (numSteps > 1) {
			updateAverage();
		} else {
			stepItem.setWeight(1.0);
			double[] puffer = new double[stepItem.length()];
			System.arraycopy(stepItem.getData(), 0, puffer, 0,
					stepItem.length());
			avgSteps = new StepItem(duration, puffer);
		}
		lastUpdateTime = System.currentTimeMillis();
		changed = true;
		clean();
	}

	/**
	 * Merge.
	 *
	 * @param stepCluster the step cluster
	 */
	public void merge(StepCluster stepCluster) {
		numSteps = numSteps + stepCluster.size();
		duration = duration + stepCluster.duration;
		for (StepItem sItem : stepCluster.steps) {
			if (distancePufferItem != sItem || distancePuffer == 0) {
				distancePuffer = dwtAvgDistance(sItem);
			}
			if (distancePuffer <= IdentificationConfiguration.dwtAvgThreshold) {
				sItem.setDistanceValue(distancePuffer);
				if (sItem.getDtwValue() > dtwSumMax) {
					dtwSumMax = sItem.getDtwValue();
				}
				if (sItem.getDtwValue() < dtwSumMin) {
					dtwSumMin = sItem.getDtwValue();
				}
				double w = 1.0 - ((sItem.getDtwValue() - dtwSumMin) / (dtwSumMax - dtwSumMin)) * 0.95;
				sItem.setWeight(w);
				steps.add(sItem);

			}
		}
		clean();
		changed = true;
	}

	/**
	 * Reset iterator.
	 */
	public void resetIterator() {
		iterator = steps.iterator();
	}

	/**
	 * Reset distance puffer.
	 */
	public void resetDistancePuffer() {
		distancePuffer = 0;
		distancePufferItem = null;
	}

	/**
	 * Next.
	 *
	 * @return the step item
	 */
	public StepItem next() {
		if (iterator.hasNext()) {
			return iterator.next();
		} else
			return null;
	}

	/**
	 * Gets the min distance.
	 *
	 * @param stepItem the step item
	 * @param threshold the threshold
	 * @return the min distance
	 */
	public double getMinDistance(StepItem stepItem, double threshold) {
		double result = -1;
		for (StepItem sItem : steps) {
			double distance = stepItem.dwtDistance(sItem);
			if (distance < result || result == -1) {
				result = distance;
				if (result < threshold) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Cluster found.
	 *
	 * @return true, if successful
	 */
	public boolean clusterFound(){
		return clusterFound;
	}
	
	/**
	 * Cluster found.
	 *
	 * @param f the f
	 */
	public void clusterFound(boolean f){
		clusterFound = f;
	}
	
	/**
	 * New cluster.
	 *
	 * @return true, if successful
	 */
	public boolean newCluster(){
		return newCluster;
	}
	
	/**
	 * Merge.
	 *
	 * @param m the m
	 */
	public void merge(boolean m){
		merge = m;
	}
	
	/**
	 * Gets the last update time.
	 *
	 * @return the last update time
	 */
	public long getLastUpdateTime(){
		return lastUpdateTime;
	}
	
	/**
	 * Merge.
	 *
	 * @return true, if successful
	 */
	public boolean merge(){
		return merge;
	}
	
	/**
	 * New cluster.
	 *
	 * @param n the n
	 */
	public void newCluster(boolean n){
		newCluster = n;
	}
	
	/**
	 * Gets the num steps.
	 *
	 * @return the num steps
	 */
	public long getNumSteps(){
		if(steps!=null){
			return numSteps;
		}
		return 0;
	}
	
	
	
	/**
	 * Calculate average.
	 */
	public void calculateAverage() {
		if (steps.size() >= maxStepListSize) {
			double[] puffer = new double[IdentificationConfiguration.accelerationArrayLength];
			for (StepItem sItem : steps) {
				double distancePuffer = dwtAvgDistance(sItem);
				sItem.setDistanceValue(distancePuffer);
				if (sItem.getDtwValue() > dtwSumMax) {
					dtwSumMax = sItem.getDtwValue();
				}
				if (sItem.getDtwValue() < dtwSumMin) {
					dtwSumMin = sItem.getDtwValue();
				}
			}
			double newWeight = 0;
			for (StepItem sItem : steps) {
				double w = 1.0 - ((sItem.getDtwValue() - dtwSumMin) / (dtwSumMax - dtwSumMin));
				sItem.setWeight(w);
				if (w <= 0.05) {
					w = 0.05;
				}
				for (int x = 0; x < sItem.length(); x++) {
					puffer[x] = puffer[x] + sItem.getData()[x] * w;
				}
				newWeight = newWeight + w;
			}
			if (newWeight > 0) {
				for (int x = 0; x < IdentificationConfiguration.accelerationArrayLength; x++) {
					puffer[x] = puffer[x] / newWeight;
				}
			}
			minAcceleration = -1;
			maxAcceleration = 1;
			for (int x = 0; x < IdentificationConfiguration.accelerationArrayLength; x++) {
				if (puffer[x] > maxAcceleration)
					maxAcceleration = puffer[x];
				if (puffer[x] < minAcceleration)
					minAcceleration = puffer[x];
			}
			avgSteps = new StepItem(duration, puffer);
			calculateExtrema();
			changed = true;
		}
	}

	/**
	 * Calculate extrema.
	 */
	void calculateExtrema() {
		if (avgSteps.getData() != null) {
			minAcceleration = -1;
			maxAcceleration = 1;
			for (int x = 0; x < IdentificationConfiguration.accelerationArrayLength; x++) {
				if (avgSteps.getData()[x] > maxAcceleration)
					maxAcceleration = avgSteps.getData()[x];
				if (avgSteps.getData()[x] < minAcceleration)
					minAcceleration = avgSteps.getData()[x];
			}
		}
	}

	/**
	 * Update average.
	 */
	public void updateAverage() {
		double[] puffer = null;
		double newWeight = 0;
		puffer = new double[IdentificationConfiguration.accelerationArrayLength];
		if (steps.size() > maxStepListSize) {
			for (StepItem sItem : steps) {
				double w = 1.0 - ((sItem.getDtwValue() - dtwSumMin) / (dtwSumMax - dtwSumMin)) * 0.95;
				sItem.setWeight(w);
				for (int x = 0; x < sItem.length(); x++) {
					puffer[x] = puffer[x] + sItem.getData()[x] * w;
				}
				newWeight = newWeight + w;
			}
		} else {
			if (steps.size() == maxStepListSize) {
				for (StepItem sItem : steps) {
					double distancePuffer = dwtAvgDistance(sItem);
					sItem.setDistanceValue(distancePuffer);
					if (sItem.getDtwValue() > dtwSumMax) {
						dtwSumMax = sItem.getDtwValue();
					}
					if (sItem.getDtwValue() < dtwSumMin) {
						dtwSumMin = sItem.getDtwValue();
					}
				}
				for (StepItem sItem : steps) {
					double w = 1.0 - ((sItem.getDtwValue() - dtwSumMin) / (dtwSumMax - dtwSumMin));
					sItem.setWeight(w);
					if (w <= 0.05) {
						w = 0.05;
					}
					for (int x = 0; x < sItem.length(); x++) {
						puffer[x] = puffer[x] + sItem.getData()[x] * w;
					}
					newWeight = newWeight + w;
				}
			} else {
				if (steps.size() == 3) {
					for (StepItem sItem : steps) {
						double distancePuffer = dwtAvgDistance(sItem);
						sItem.setDistanceValue(distancePuffer);
						if (sItem.getDtwValue() > dtwSumMax) {
							dtwSumMax = sItem.getDtwValue();
						}
						if (sItem.getDtwValue() < dtwSumMin) {
							dtwSumMin = sItem.getDtwValue();
						}
					}
				}
				for (StepItem sItem : steps) {
					for (int x = 0; x < sItem.length(); x++) {
						puffer[x] = puffer[x] + sItem.getData()[x];
						sItem.setWeight(1.0 / steps.size());
					}
				}
				newWeight = steps.size();

			}
		}
		for (int x = 0; x < IdentificationConfiguration.accelerationArrayLength; x++) {
			puffer[x] = puffer[x] / newWeight;
		}
		avgSteps = new StepItem(duration, puffer);
		calculateExtrema();
		changed = true;
	}

	/**
	 * Gets the image.
	 *
	 * @param lastItem the last item
	 * @param bmp the bmp
	 * @param sensorData the sensor data
	 * @return the image
	 */
	public Bitmap getImage(StepCluster lastItem, Bitmap bmp,
			SensorData sensorData) {

		if (changed || bmp == null || lastItem != this) {
			// PearsonsCorrelation corr = new PearsonsCorrelation();
			// double result = corr.correlation(x, y); //result = NaN.
			if (steps.size() > 0) {
				Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf
																// types
				int oHeight = 200;

				int bsize = IdentificationConfiguration.accelerationArrayLength * 6;

				if (bmp == null) {
					bmp = Bitmap.createBitmap(bsize, bsize + oHeight, conf);
				}
				Canvas canvas = new Canvas(bmp);
				Paint paint = new Paint();

				paint.setStrokeWidth(10);
				paint.setStyle(Paint.Style.STROKE);
				canvas.drawColor(Color.WHITE);
				long oldy = -1;
				int color = Color.argb(255, 0, 0, 0);
				paint.setColor(color);
				for (int x = 0; x < avgSteps.length(); x++) {
					double acceleration = avgSteps.getData()[x];
					int y = 4 + (int) (((acceleration - minAcceleration) / (maxAcceleration - minAcceleration)) * (IdentificationConfiguration.accelerationArrayLength - 5));
					long ypos = y * 6;
					if (ypos < 0) {
						ypos = 0;
					}
					if (ypos >= bmp.getHeight() - 1) {
						ypos = bmp.getWidth() - 1;
					}
					long xpos1 = (x - 1) * 6 - 1;
					long xpos2 = x * 6 + 1;
					if (xpos1 < 0) {
						xpos1 = 0;
					}
					if (xpos2 > bmp.getWidth() - 1) {
						xpos2 = bmp.getWidth() - 1;
					}

					if (oldy != -1) {
						canvas.drawLine(xpos1, oldy, xpos2, ypos, paint);
					}
					oldy = ypos;
				}
				paint.setStrokeWidth(3);

				for (StepItem sItem : steps) {
					int red = 220;// (int)(rand.nextFloat()*128)+127;
					int green = 220;// (int)(rand.nextFloat()*128)+127;
					int blue = 220;// (int)(rand.nextFloat()*128)+127;
					int alpha = 200;
					if (steps.size() > 2) {
						alpha = (int) ((1.0 - ((sItem.getDtwValue() - dtwSumMin) / (dtwSumMax - dtwSumMin))) * 255);
					}
					if (timePuffer == null) {
						timePuffer = sensorData.getHistoryN(
								sItem.getStartTime() - 1500000000, sItem.getStartTime()
										+ sItem.getDuration() + 1500000000, true);
					}

					color = Color.argb(alpha, red, green, blue);
					paint.setColor(color);
					oldy = -1;

					for (int x = 0; x < sItem.length(); x++) {

						double acceleration = sItem.getData()[x];
						int y = 4 + (int) (((acceleration - minAcceleration) / (maxAcceleration - minAcceleration)) * (IdentificationConfiguration.accelerationArrayLength - 5));
						long ypos = y * 6;
						if (ypos < 0) {
							ypos = 0;
						}
						if (ypos >= bmp.getHeight() - 1) {
							ypos = bmp.getWidth() - 1;
						}
						long xpos1 = (x - 1) * 6 - 1;
						long xpos2 = x * 6 + 1;
						if (xpos1 < 0) {
							xpos1 = 0;
						}
						if (xpos2 > bmp.getWidth() - 1) {
							xpos2 = bmp.getWidth() - 1;
						}

						if (oldy != -1) {
							canvas.drawLine(xpos1, oldy, xpos2, ypos, paint);
						}
						oldy = ypos;
					}
				}
				if (timePuffer != null) {
					color = Color.argb(255, 0, 0, 0);
					paint.setColor(color);
					paint.setStrokeWidth(1);
					if (timePuffer.length > 1) {
						float xstep = (float) timePuffer.length
								/ ((float) bsize);
						float oy = 0;
						for (int i = 0; i < bsize; i++) {
							float ypos = (float) (bsize + oHeight * 0.5 + Util
									.getCubicInterpolatorValue(timePuffer, i
											* xstep)
									* (oHeight / 5.0));
							if (i > 0) {
								canvas.drawLine(i - 1, oy, i, ypos, paint);
							}
							oy = ypos;
						}
					}
				}
				/*
				changed = false;
				color = Color.argb(255, 130, 130, 130);
				paint.setColor(color);
				paint.setTextSize(35);
				paint.setStrokeWidth(1);
				paint.setStyle(Paint.Style.FILL);
				canvas.drawText("" + index + ",  num: " + numSteps
						+ ",  duration: " + (duration / numSteps) / 1000000L
						+ " ms", 10, 35, paint);
				color = Color.argb(255, 170, 170, 170);
				paint.setColor(color);
				int ypos = 1;
				for (StepItem sItem : steps) {
					canvas.drawText("" + sItem.getWeight() + ", " + sItem.getDtwValue(),
							10, 35 + ypos * 36, paint);
					ypos++;
				}
				*/
			}
		}
		return bmp;
	}

	/**
	 * Prints the image.
	 *
	 * @param index the index
	 */
	public void printImage(String index) {
		Bitmap printBmp = null;
		printBmp = getImage(null, printBmp, null);
		if (printBmp != null) {
			String filename = "cluster_" + index + "_"
					+ System.currentTimeMillis() + ".png";
			CameraData.saveBitmap(printBmp, filename);
			Log.v(TAG, filename);
		}
	}

	/**
	 * Part of.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean partOf(StepCluster c) {

		double avgDuration1 = duration / numSteps;
		double avgDuration2 = c.getDuration() / c.numSteps;
		if (avgDuration2 > avgDuration1) {
			if (c.avgSteps != null && avgSteps != null) {
				if (c.avgSteps.getData() != null && avgSteps.getData() != null) {
					double result = 0;
					int len = (int) ((avgDuration1 / avgDuration2) * avgSteps.length());
					int offset = avgSteps.length() - len;

					double[] pufferPart = new double[len];
					System.arraycopy(c.avgSteps.getData(), 0, pufferPart, 0, len);

					double resultL = dwtAvgDistance(pufferPart);

					Log.v(TAG, "dwtDistance (" + index + "," + c.index
							+ ") (s): " + result);
					if (resultL < IdentificationConfiguration.dwtEmbodiedThreshold) {
						return true;
					}
					System.arraycopy(c.avgSteps.getData(), offset, pufferPart,
							0, len);

					double resultR = dwtAvgDistance(pufferPart);
					/*
					 * if(index==123&&c.index==9){
					 * 
					 * avgSteps.getData() = puffer; }
					 */
					Log.v(TAG, "dwtDistance (" + index + "," + c.index
							+ ") (s): " + result);
					if (resultR < IdentificationConfiguration.dwtEmbodiedThreshold) {
						return true;
					}
					/*
					 * if(resultL<resultR){
					 * if(resultL<IdentificationConfiguration
					 * .dwtEmbodiedThreshold){ double lastValue =
					 * c.avgSteps.getData()[0]; int lastDirection = 0; offset =
					 * 0; for(int i =1; i < avgSteps.length(); i++){
					 * double v = c.avgSteps.getData()[i]; int direction = (v >
					 * lastValue ? 1 : (v < lastValue ? -1 : 0));
					 * if(direction==-1){ if (direction == -lastDirection) {
					 * offset = i; } } lastDirection = direction; lastValue = v;
					 * 
					 * } if(offset > 0 && offset <
					 * avgSteps.length()*0.5){
					 * 
					 * } } } else
					 * if(resultR<IdentificationConfiguration.dwtEmbodiedThreshold
					 * ){
					 * 
					 * }
					 */
				}
			}
		}
		return false;
	}

	/**
	 * Dwt avg distance.
	 *
	 * @param pufferPart the puffer part
	 * @return the double
	 */
	public double dwtAvgDistance(double[] pufferPart) {
		double[] puffer = new double[avgSteps.length()];
		float xstep = (float) pufferPart.length
				/ (float) avgSteps.length();
		for (int i = 0; i < avgSteps.length(); i++) {
			puffer[i] = Util.getCubicInterpolatorValue(pufferPart, i * xstep);
		}
		Util.normalize(puffer);

		return Util.dwtDistance(avgSteps.getData(), puffer, radius);
	}

	/**
	 * Dwt avg distance.
	 *
	 * @param stepItem the step item
	 * @return the double
	 */
	public double dwtAvgDistance(StepItem stepItem) {
		double result = 0;
		if (avgSteps != null) {
			result = Util.dwtDistance(avgSteps.getData(), stepItem.getData(),
					radius);
		}
		distancePuffer = result;
		distancePufferItem = stepItem;
		return result;
	}
}
