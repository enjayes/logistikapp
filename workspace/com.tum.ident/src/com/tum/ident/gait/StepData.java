package com.tum.ident.gait;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.widget.Toast;

import com.tum.ident.IdentificationConfiguration;
import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;
import com.tum.ident.sensors.SensorData;
import com.tum.ident.storage.StorageHandler;
import com.tum.ident.util.Util;


/**
 * The Class StepData.
 */
public class StepData implements SensorEventListener, Runnable {
	
	/** The sensor manager. */
	private SensorManager sensorManager;
	
	/** The step sensor. */
	private Sensor stepSensor = null;
	
	/** The count sensor. */
	private Sensor countSensor = null;
	
	/** The light sensor. */
	private Sensor lightSensor = null;;
	
	/** The Constant TAG. */
	private static final String TAG = "StepData";


	/** The current light. */
	private float currentLight = 100;

	/** The sensor data. */
	private SensorData sensorData;

	/** The max acceleration. */
	private double maxAcceleration = 0;
	
	/** The min acceleration. */
	private double minAcceleration = 0;


	/** The last time stamp. */
	private long lastTimeStamp = 0;

	/** The running. */
	private boolean running = true;

	/** The not in pocket counter. */
	private long notInPocketCounter = 0;


	/** The step queue. */
	private BlockingQueue<StepItem> stepQueue = new ArrayBlockingQueue<StepItem>(201);

	/** The steps. */
	private StepStatistics steps = new StepStatistics();

	/** The debug info. */
	private String debugInfo = "";

	/** The sleeping. */
	private boolean sleeping = true;


	/** The step clusters semaphore. */
	private Semaphore stepClustersSemaphore = new Semaphore(1);

	/** The context. */
	private Context context;

	/** The data controller. */
	private DataController dataController;

	/** The steps loaded. */
	private boolean stepsLoaded = false;

	/** The sound on. */
	private boolean soundOn = true;

	/** The bmp. */
	private Bitmap bmp = null;
	
	/** The bitmap item. */
	private StepCluster bitmapItem = null;
	
	/** The step detector available. */
	private boolean stepDetectorAvailable = false;

	/** The save steps. */
	private boolean saveSteps = false;
	
	/** The save steps timer. */
	private long saveStepsTimer = 0;
	
	/** The save steps wait time. */
	private long saveStepsWaitTime = 10000;
	
	/** The last save step time. */
	private long lastSaveStepTime = System.currentTimeMillis();

	/** The avg duration. */
	private long avgDuration = -1;

	/**
	 * Instantiates a new step data.
	 *
	 * @param context the context
	 * @param sensorData the sensor data
	 * @param dataController the data controller
	 */
	public StepData(Context context, SensorData sensorData,
			DataController dataController) {
		this.context = context;
		this.sensorData = sensorData;
		this.dataController = dataController;
		load();
		lock();
		steps.getClusters().clean(1);
		steps.getClusters().clean(2);
		steps.getClusters().removeEmbodiedClusters(); // todo

		unlock();
		if (context != null) {
			sensorManager = (SensorManager) context
					.getSystemService(Context.SENSOR_SERVICE);
			new Thread(this).start();
		}
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		context.registerReceiver(screenReceiver, filter);
		SensorManager sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		if (lightSensor != null) {
			sensorManager.registerListener(this, lightSensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	/**
	 * Lock.
	 */
	public void lock() {
		try {
			stepClustersSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unlock.
	 */
	public void unlock() {
		stepClustersSemaphore.release();
	}

	/**
	 * Checks if is step detector available.
	 *
	 * @return true, if is step detector available
	 */
	public boolean isStepDetectorAvailable() {
		return stepDetectorAvailable;
	}

	/**
	 * Unregister light listener.
	 */
	public void unregisterLightListener() {
		sensorManager.unregisterListener(this, lightSensor);
	}

	/**
	 * Register light listener.
	 */
	public void registerLightListener() {
		sensorManager.registerListener(this, lightSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	/**
	 * Unregister listener.
	 */
	public void unregisterListener() {
		if (countSensor != null) {
			sensorManager.unregisterListener(this, countSensor);
		}
		if (stepSensor != null) {
			sensorManager.unregisterListener(this, stepSensor);
		}
	}

	/**
	 * Register listener.
	 */
	@SuppressLint({ "InlinedApi", "NewApi" })
	public void registerListener() {
		PackageManager packageManager = context.getPackageManager();
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
				&& packageManager
						.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
				&& packageManager
						.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)) {
			countSensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
			stepSensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
			if (countSensor != null) {
				// Log.v(TAG,"sensorManager.registerListener countSensor");
				sensorManager.registerListener(this, countSensor,
						SensorManager.SENSOR_DELAY_FASTEST, 0);
			}
			if (stepSensor != null) {
				// Log.v(TAG,"sensorManager.registerListener stepSensor");
				sensorManager.registerListener(this, stepSensor,
						SensorManager.SENSOR_DELAY_FASTEST, 0);
			}
			// Log.v(TAG,"countSensor: "+countSensor);
			// Log.v(TAG,"stepSensor: "+stepSensor);
			if (countSensor != null && stepSensor != null) {
				stepDetectorAvailable = true;
			} else {
				stepDetectorAvailable = false;
			}
		} else {
			// Log.v(TAG,"SDK_INT < KITKAT");
			stepDetectorAvailable = false;
		}
		// Log.v(TAG,"stepDetectorAvailable: "+stepDetectorAvailable);

	}

	/* 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long addStepTime = 0;
		sleeping = true;
		boolean clustersUpdated = false;
		while (running) {
			long currentTime = System.currentTimeMillis();
			if (saveSteps) {
				if (currentTime - lastSaveStepTime > 7200000
						|| currentTime - saveStepsTimer > saveStepsWaitTime
								+ addStepTime) {
					lock();
					steps.getClusters().clean(0);
					unlock();
					save();
					saveStepsTimer = currentTime;
					lastSaveStepTime = currentTime;
					saveSteps = false;
				}
			}
			if (currentTime - saveStepsTimer > (IdentificationConfiguration.maxStepDuration / 1000000L)
					* 5 + addStepTime) {
				if (sleeping == false) {
					steps.getClusters().stopRecording();
					sleeping = true;
					sensorData.recordHistory(false);
				}
			}
			/*
			 * if(stepDetectorAvailable){ if(sensorData.getList()eningSteps()){
			 * if(currentTime-lastStepTime > 10000 ){
			 * sensorData.unregisterStepListeners(); debugInfo =
			 * debugInfo+"unregisterListener\n"; } } }
			 */

			StepItem stepItem = null;
			stepItem = stepQueue.poll();
			if (stepItem != null) {
				lock();
				long addStepStartTime = System.currentTimeMillis();
				if (sleeping == true) {
					steps.getClusters().clean(2);
					steps.getClusters().startRecording();
				}
				steps.getClusters().add(stepItem, sensorData);
				saveStepsTimer = System.currentTimeMillis();
				saveSteps = true;
				addStepTime = System.currentTimeMillis() - addStepStartTime;
				// Log.v(TAG,"time: "+addStepTime+"ms");
				unlock();
				sleeping = false;
				clustersUpdated = false;
			}
			if (sleeping) {
				if (clustersUpdated == false) {
					lock();
					steps.getClusters().calculateAverage();
					unlock();
					clustersUpdated = true;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Reset.
	 */
	public void reset() {
		lock();
		steps = new StepStatistics();
		unlock();
		save();
	}

	/**
	 * Save.
	 */
	public void save() {
		// Log.v(TAG,"saveSteps");
		saveStepClusters();
		saveStepCounter();
	}

	/**
	 * Load.
	 */
	public void load() {
		// Log.v(TAG,"loadSteps");
		loadStepClusters();
		loadStepCounters();
		stepsLoaded = true;
	}

	/**
	 * Save step counter.
	 */
	public void saveStepCounter() {
		if (stepsLoaded == true) {
			String fileName = "stepcounter.ser";
			// Log.v(TAG,"save stepCounter:"+fileName);
			StorageHandler.saveObject(steps.getCounter(), fileName);

		}
	}

	/**
	 * Load step counters.
	 */
	public void loadStepCounters() {
		String fileName = "stepcounter.ser";
		// Log.v(TAG,"load stepCounter:"+fileName);
		steps.setCounter((StepCounter) StorageHandler.loadObject(fileName));
		
	}

	/**
	 * Save step clusters.
	 */
	public void saveStepClusters() {
		if (stepsLoaded == true) {
			String fileName = "steps.ser";
			// Log.v(TAG,"save stepCluster:"+fileName);
			lock();
			steps.getClusters().prepare(true);
			StorageHandler.saveObject(steps.getClusters(), fileName);
			steps.getClusters().prepare(false);
			unlock();
		}

	}

	/**
	 * Load step clusters.
	 */
	public void loadStepClusters() {
		lock();
		String fileName = "steps.ser";
		// Log.v(TAG,"load stepCluster:"+fileName);
		
		steps.setClusters((StepClusterList) StorageHandler.loadObject(fileName));
		steps.getClusters().prepare(false);

		unlock();
	}

	/**
	 * Send step cluster.
	 */
	public void sendStepCluster() {
		lock();
		if (steps.getClusters().getList().size() > 0) {
			steps.getClusters().prepare(true);
			dataController.addData("", steps);
			steps.getClusters().prepare(false);
		}
		unlock();

	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		// Log.v(TAG, "stepData.getDataItem");
		lock();
		steps.getClusters().prepare(true);
		DataItem item = new DataItem("", steps);
		steps.getClusters().prepare(false);
		unlock();
		return item;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		lock();
		String summary = "";
		if (steps.getClusters().getList() != null) {
			summary = "Number of Step Clusters: " + steps.getClusters().getList().size()
					+ " Step Counter: " + steps.getCounter().currentSteps;
		} else {
			summary = "steps.getClusters().getList()==null";
		}
		unlock();
		return summary;

	}

	/**
	 * Gets the step counter string.
	 *
	 * @return the step counter string
	 */
	public String getStepCounterString() {
		return "queue size: " + stepQueue.size() + "\ncluster num: "
				+ steps.getClusters().getList().size() + "\nstep counter: "
				+ steps.getCounter().currentSteps;// +steps.getClusters().getHistory();
	}

	/**
	 * Creates the acceleration image.
	 */
	public void createAccelerationImage() {
		if (steps.getClusters().getList() != null) {
			lock();
			int random = (int) (Math.random() * 1000);
			// Log.v(TAG, "createAccelerationImage");
			// Log.v(TAG, "stepClusters.getList():" + steps.getClusters().getList());

			for (StepCluster sItem : steps.getClusters().getList()) {
				// Log.v(TAG, "stepClusters.getList():" + sItem);
				sItem.printImage(String.valueOf(random));
			}
			unlock();
			sendStepCluster();
		}
	}

	/**
	 * Next cluster index.
	 *
	 * @param index the index
	 * @return the int
	 */
	public int nextClusterIndex(int index) {
		if (steps.getClusters().getList() != null) {
			if (steps.getClusters().getList().size() > 0) {
				index++;
				return index % steps.getClusters().getList().size();
			}
		}
		return 0;
	}

	/**
	 * Gets the acceleration image.
	 *
	 * @param index the index
	 * @return the acceleration image
	 */
	public Bitmap getAccelerationImage(int index) {
		// Log.v(TAG,"getAccelerationImage");
		if (steps.getClusters().getList() != null) {
			if (steps.getClusters().getList().size() > 0) {
				lock();
				index = (index) % steps.getClusters().getList().size();
				StepCluster newBitmapItem = steps.getClusters().getList().get(index);
				Toast.makeText(context, "Cluster #" + (index + 1),
						Toast.LENGTH_SHORT).show();
				bmp = newBitmapItem.getImage(bitmapItem, bmp, sensorData);
				bitmapItem = newBitmapItem;
				unlock();
			}
		}
		return bmp;
	}

	/**
	 * Adds the step.
	 *
	 * @param newTimeStamp the new time stamp
	 * @return true, if successful
	 */
	public boolean addStep(long newTimeStamp) {
		boolean stepAdded = false;
		if (stepQueue.size() < 200) {
			if (sensorData.getFirstEventTime() <= lastTimeStamp) {
				long duration = newTimeStamp - lastTimeStamp;
				if (duration >= IdentificationConfiguration.minStepDuration
						&& duration <= IdentificationConfiguration.maxStepDuration) {
					double[] accelerationList = sensorData.getAccelerationN(
							lastTimeStamp, newTimeStamp, true);
					if (accelerationList != null) {
						double[] puffer = new double[IdentificationConfiguration.accelerationArrayLength];
						float xstep = (float) accelerationList.length
								/ (float) IdentificationConfiguration.accelerationArrayLength;
						for (int i = 0; i < IdentificationConfiguration.accelerationArrayLength; i++) {
							puffer[i] = Util.getCubicInterpolatorValue(
									accelerationList, i * xstep);
							if (puffer[i] > maxAcceleration) {
								maxAcceleration = puffer[i];
							}
							if (puffer[i] < minAcceleration) {
								minAcceleration = puffer[i];
							}
						}

						StepItem stepItem = new StepItem(duration,
								lastTimeStamp, puffer);
						debugInfo = debugInfo
								+ "->queue, duration: "
								+ (duration * 1.0 - IdentificationConfiguration.minStepDuration)
								/ (1.0 * IdentificationConfiguration.maxStepDuration - IdentificationConfiguration.minStepDuration)
								+ "\n";
						try {
							stepQueue.put(stepItem);
							stepAdded = true;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return stepAdded;
	}

	/** The last event time. */
	public long lastEventTime = 0;

	/**
	 * Gets the last event time.
	 *
	 * @return the last event time
	 */
	public long getLastEventTime() {
		return lastEventTime;
	}

	// BroadcastReceiver for handling ACTION_SCREEN_OFF.
	/** The screen receiver. */
	public BroadcastReceiver screenReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// Check action just to be on the safe side.
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				// Log.v(TAG,"trying re-registration");
				if (stepDetectorAvailable) {
					if (currentLight <= 10) {
						unregisterListener();
						registerListener();
					}
					unregisterLightListener();
					registerLightListener();

				}
			}
		}
	};

	/**
	 * Not in pocket counter.
	 *
	 * @return the long
	 */
	public long notInPocketCounter() {

		if (currentLight <= 15) {
			float[] gravity = sensorData.getGravity();
			// Log.v(TAG,
			// "gravity: "+gravity[0]+", "+gravity[1]+", "+gravity[2]);
			if (Math.abs(gravity[0]) + Math.abs(gravity[1]) > 3) {
				notInPocketCounter++;
				return notInPocketCounter;
			}
		}
		notInPocketCounter = 0;
		return 0; // todo
	}

	/* 
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == lightSensor) {
			currentLight = event.values[0];
			if (sensorData.listeningSteps() == false) {
				if (currentLight <= 15) {
					sensorData.registerStepListeners();
				}
			} else {
				if (currentLight > 15) {
					sensorData.unregisterStepListeners();
				}
			}
		} else if (event.sensor == countSensor) {
			saveStepsTimer = System.currentTimeMillis();
			saveSteps = true;
			// Log.v(TAG,"counter: "+event.values[0]);
			debugInfo = debugInfo + "step: " + event.sensor.getType() + " ("
					+ event.values[0] + ")\n";
		} else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { // event.sensor
																			// ==
																			// stepSensor
																			// ||
			// if(notInPocketCounter()<3 || sleeping == false){
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				if (stepDetectorAvailable == false) {
					steps.getCounter().addStep();
				}
			}
			lastEventTime = event.timestamp;
			/*
			 * if(sensorData!=null){ if(sensorData.getList()eningSteps()==false){
			 * debugInfo = debugInfo+"registerListener\n";
			 * sensorData.registerStepListeners(); } }
			 */
			// Log.v(TAG,"STEPS DETECTED!!! ");
			long newTimeStamp = event.timestamp;

			debugInfo = debugInfo
					+ "step num: "
					+ event.values.length
					+ " delay: ("
					+ (System.currentTimeMillis() - (event.timestamp / 1000000L))
					+ ")";
			long duration = newTimeStamp - lastTimeStamp;
			if (duration > IdentificationConfiguration.minStepDuration) {
				if (duration > avgDuration
						- IdentificationConfiguration.diffStepAvgDuration) {
					boolean added = false;
					if (addStep(newTimeStamp)) {
						added = true;
						sensorData.recordHistory(true);
					}

					if (added) {
						if (soundOn) {// &&
										// RealtimeData.mode.equals("GaitRecognition")){
							final ToneGenerator tg = new ToneGenerator(
									AudioManager.STREAM_NOTIFICATION, 100);
							tg.startTone(ToneGenerator.TONE_PROP_BEEP);
						}
					}
					debugInfo = debugInfo + "\n";
					lastTimeStamp = newTimeStamp;
				}
				if (duration < IdentificationConfiguration.maxStepDuration) {
					if (avgDuration == -1) {
						avgDuration = duration;
					} else {
						if (duration < avgDuration
								+ IdentificationConfiguration.diffStepAvgDuration
								* 10) {
							avgDuration = (long) (0.9 * avgDuration + duration * 0.1);
						} else {
							avgDuration = avgDuration
									+ IdentificationConfiguration.diffStepAvgDuration;
						}
					}
				}
				Log.v(TAG, "avgDuration: " + avgDuration);
				Log.v(TAG, "duration: " + duration + "\n");
			}
			saveStepsTimer = System.currentTimeMillis();
			saveSteps = true;
		}
		// Log.v(TAG,"t: "+(System.currentTimeMillis()-lastStepTime));
		// }
	}

	/* 
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
