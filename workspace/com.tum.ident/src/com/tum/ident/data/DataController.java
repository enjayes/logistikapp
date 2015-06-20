package com.tum.ident.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;

import com.tum.ident.IdentificationConfiguration;
import com.tum.ident.IdentificationListener;
import com.tum.ident.IdentificationService;
import com.tum.ident.battery.BatteryData;
import com.tum.ident.camera.CameraData;
import com.tum.ident.files.FileData;
import com.tum.ident.files.FileItemList;
import com.tum.ident.gait.StepData;
import com.tum.ident.identification.IdentificationData;
import com.tum.ident.identification.IdentificationItem;
import com.tum.ident.locations.LocationData;
import com.tum.ident.music.MusicData;
import com.tum.ident.music.MusicItemList;
import com.tum.ident.network.NetworkClient;
import com.tum.ident.network.ServerRunner;
import com.tum.ident.orientation.OrientationData;
import com.tum.ident.realtime.RealtimeData;
import com.tum.ident.realtime.RealtimeServer;
import com.tum.ident.result.ResultHandler;
import com.tum.ident.result.ResultItem;
import com.tum.ident.sensors.SensorData;
import com.tum.ident.spectrum.SpectrumData;
import com.tum.ident.stepdetector.StepDetector;
import com.tum.ident.storage.StorageHandler;


/**
 * The Class DataController.
 */
public class DataController implements Runnable, IdentificationListener {

	/** The Constant TAG. */
	private final static String TAG = "DataController";
	
	/** The Ident version. */
	private final long IdentVersion = 9;
	
	/** The identification data. */
	private IdentificationData identificationData = null;
	
	/** The camera data. */
	private CameraData cameraData = null;
	
	/** The sensor data. */
	private SensorData sensorData = null;
	
	/** The step data. */
	private StepData stepData = null;
	
	/** The location data. */
	private LocationData locationData = null;
	
	/** The orientation data. */
	private OrientationData orientationData = null;
	
	/** The step detector. */
	private StepDetector stepDetector = null;
	
	/** The music data. */
	private MusicData musicData = null;
	
	/** The file data. */
	private FileData fileData = null;
	
	/** The battery data. */
	private BatteryData batteryData = null;
	
	/** The spectrum data. */
	private SpectrumData spectrumData = null;
	
	/** The context. */
	private Context context;
	
	/** The server url. */
	private String serverURL;
	
	/** The debug url. */
	private String debugURL;
	
	/** The todo list. */
	private boolean[] todoList = null;
	
	/** The data item list. */
	private DataItem[] dataItemList = null;
	
	/** The listener. */
	private IdentificationListener listener;
	
	/** The result handler. */
	private ResultHandler resultHandler;
	
	/** The app changed. */
	private boolean appChanged = false;
	
	/** The running. */
	private boolean running = true;
	
	/** The send battery real time. */
	private boolean sendBatteryRealTime = true;
	
	/** The user id. */
	private String userID = "";
	
	/** The device id. */
	private String deviceID = "";
	
	/** The surface holder. */
	private SurfaceHolder surfaceHolder;
	
	/** The service. */
	private IdentificationService service = null;
	
	/** The num types. */
	private static int numTypes = 0;
	
	/** The timer. */
	private long[] timer = new long[20];
	
	/** The wait time. */
	private long[] waitTime = new long[20];
	
	/** The todo wait time. */
	private long todoWaitTime = 3600000;
	
	/** The todo timer. */
	private long todoTimer = 0;
	
	/** The send location counter. */
	private long sendLocationCounter = 0;
	
	/** The sendlistener update. */
	private boolean sendlistenerUpdate = false;
	
	/** The wait for identification item. */
	private boolean waitForIdentificationItem = false;
	
	/** The data queue. */
	private BlockingQueue<DataItem> dataQueue = new ArrayBlockingQueue<DataItem>(1024);
	
	/** The user name. */
	private static String userName = "";

	/**
	 * The Enum DataType.
	 */
	public enum DataType {
		
		/** The Pixel error front. */
		PixelErrorFront, 
 /** The Pixel error back. */
 PixelErrorBack, 
 /** The Dark frame front. */
 DarkFrameFront, 
 /** The Dark frame back. */
 DarkFrameBack, 
 /** The Location. */
 Location, 
 /** The Step detection. */
 StepDetection, 
 /** The User device. */
 UserDevice, 
 /** The Music. */
 Music, 
 /** The File. */
 File, 
 /** The Orientation. */
 Orientation, 
 /** The Battery. */
 Battery, 
 /** The Spectrum. */
 Spectrum
	}

	/**
	 * Instantiates a new data controller.
	 *
	 * @param serverURL the server url
	 * @param context the context
	 * @param surfaceHolder the surface holder
	 * @param listener the listener
	 */
	@SuppressWarnings("deprecation")
	public DataController(String serverURL, Context context,
			SurfaceHolder surfaceHolder, IdentificationListener listener) {
		this.surfaceHolder = surfaceHolder;
		this.serverURL = serverURL + "Identification";
		this.debugURL = serverURL + "Debug";
		this.listener = listener;
		this.context = context;
		this.resultHandler = new ResultHandler(listener, this);

		String absolutePath = context.getFilesDir().getAbsolutePath();
		int modDate = StorageHandler
				.lastModified(absolutePath + "/storage.dat").getDate();
		String modDateSaved = StorageHandler.readFromFile("app.dat");
		if (modDateSaved.length() > 0
				&& modDate != Integer.parseInt(modDateSaved)) {
			appChanged = true;
		}

		if (appChanged == false) {
			String settingsString = StorageHandler.readFromFile("storage.dat");
			Log.v(TAG, "settingsString: " + settingsString);
			if (settingsString.length() > 0) {
				Log.v(TAG,
						"settingsString.length(): " + settingsString.length());
				if (settingsString.length() >= IdentificationConfiguration.hashLength * 2) {
					String storedDeviceID = settingsString.substring(0,
							IdentificationConfiguration.hashLength);
					String storedUserID = settingsString.substring(
							IdentificationConfiguration.hashLength,
							IdentificationConfiguration.hashLength * 2);
					Log.v(TAG, "StorageHandler - deviceID: " + storedDeviceID
							+ " " + storedDeviceID.length());
					Log.v(TAG, "StorageHandler - userID: " + storedUserID + " "
							+ storedUserID.length());
					if (storedUserID.length() == IdentificationConfiguration.hashLength
							&& storedDeviceID.length() == IdentificationConfiguration.hashLength) {
						if (listener != null) {
							userID = storedUserID;
							deviceID = storedDeviceID;
							listener.onReceiveIds(deviceID, userID);
							Log.v(TAG,
									"listener.onReceiveIds(deviceID, userID);");
						}
					}
				}
			}
		}
		DataType[] dataTypeList = DataType.values();
		numTypes = dataTypeList.length;
		timer = new long[numTypes];
		waitTime = new long[numTypes];
		todoList = new boolean[numTypes];
		dataItemList = new DataItem[numTypes];
		loadTodoList();
		sendlistenerUpdate = true;
		Log.v("DEBUG", "new Thread(this).start();");
		new Thread(this).start();
		ServerRunner.run(RealtimeServer.class);

	}


	/**
	 * Sets the parameter.
	 *
	 * @param serverURL the server url
	 * @param context the context
	 * @param surfaceHolder the surface holder
	 * @param listener the listener
	 */
	public void setParameter(String serverURL, Context context,
			SurfaceHolder surfaceHolder, IdentificationListener listener) {
		this.surfaceHolder = surfaceHolder;
		this.serverURL = serverURL + "Identification";
		this.debugURL = serverURL + "Debug";
		this.listener = listener;
		this.context = context;
		this.resultHandler = new ResultHandler(listener, this);
		if (identificationData == null) {
			identificationData = new IdentificationData(context, this);
			new Thread(this).start();
		}
		DataType[] dataTypeList = DataType.values();
		numTypes = dataTypeList.length;
		timer = new long[numTypes];
		waitTime = new long[numTypes];
		loadTodoList();
		if (userID != null || deviceID != null) {
			if (userID.length() == IdentificationConfiguration.hashLength
					&& deviceID.length() == IdentificationConfiguration.hashLength) {
				if (listener != null) {
					if (identificationData != null) {
						identificationData.onReceiveIds(deviceID, userID);
					}
					listener.onReceiveIds(deviceID, userID);
				}
			}
		}
		sendlistenerUpdate = true;
	}
	
	/**
	 * Release.
	 */
	public void release() {
		running = false;
		if (sensorData != null) {
			sensorData.release();
		}
	}

	/**
	 * Keep item data.
	 *
	 * @param type the type
	 * @return true, if successful
	 */
	public static boolean keepItemData(DataType type) {
		if (type == DataType.UserDevice) {
			return false;
		} else if (type == DataType.File) {
			return false;
		} else if (type == DataType.Music) {
			return false;
		}
		return true;
	}

	/**
	 * I.
	 *
	 * @param type the type
	 * @return the int
	 */
	public static int i(DataType type) {
		return type.ordinal();
	}

	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveUpdate()
	 */
	@Override
	public void onReceiveUpdate() {

	}
	
	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveDataItem(com.tum.ident.data.DataItem)
	 */
	@Override
	public void onReceiveDataItem(DataItem item) {
	}
	
	/**
	 * Sets the service.
	 *
	 * @param service the new service
	 */
	public void setService(IdentificationService service) {
		this.service = service;
	}

	/**
	 * Check reset data.
	 */
	public void checkResetData() {
		URL url;
		try {
			url = new URL("http://songbase.net/thesis/Reset.txt");
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String resetData = sb.toString();
			if (resetData.length() > 0) {
				if (resetData.equals("reset")) {
					if (stepData != null) {
						stepData.reset();
					}
				}

			}
			br.close();
			urlConnection.disconnect();
		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
	}

	/**
	 * New update available.
	 *
	 * @return true, if successful
	 */
	public boolean newUpdateAvailable() {
		boolean result = false;

		URL url;
		try {
			url = new URL("http://songbase.net/thesis/Update.txt");

			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String UpdateVersion = sb.toString();
			if (UpdateVersion.length() > 0) {
				if (UpdateVersion.length() < 5) {
					try {
						int Version = Integer.valueOf(UpdateVersion);
						if (Version > IdentVersion) {
							result = true;
						}
					} catch (NumberFormatException e) {
						result = false;
					}
				}

			}
			br.close();
			urlConnection.disconnect();

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
		return result;
	}

	/**
	 * Reset timer.
	 */
	public void resetTimer() {
		todoWaitTime = 3600000;
		todoTimer = 0;
		sendLocationCounter = 0;

		waitTime[i(DataType.UserDevice)] = 43200000;
		timer[i(DataType.UserDevice)] = -waitTime[i(DataType.UserDevice)];

		waitTime[i(DataType.StepDetection)] = 7200000;
		timer[i(DataType.StepDetection)] = -timer[i(DataType.StepDetection)];

		waitTime[i(DataType.PixelErrorBack)] = 1800000;
		timer[i(DataType.PixelErrorBack)] = -timer[i(DataType.PixelErrorBack)];

		waitTime[i(DataType.Orientation)] = 300000;
		timer[i(DataType.Orientation)] = -timer[i(DataType.Orientation)];

		waitTime[i(DataType.Location)] = 1200000;
		timer[i(DataType.Location)] = -waitTime[i(DataType.Location)];

		waitTime[i(DataType.File)] = 43200000;
		timer[i(DataType.File)] = -waitTime[i(DataType.File)];

		waitTime[i(DataType.Music)] = 43200000;
		timer[i(DataType.Music)] = -waitTime[i(DataType.Music)];

		waitTime[i(DataType.Spectrum)] = 20000;
		timer[i(DataType.Spectrum)] = -waitTime[i(DataType.Spectrum)];
	}

	/**
	 * Send data.
	 *
	 * @param notification the notification
	 * @return true, if successful
	 */
	public boolean sendData(final boolean notification) {
		if (NetworkClient.networkAvailable()) {
			if (deviceID.length() > 0 && userID.length() > 0) {
				if (identificationData.isReady()) {

					DataType[] dataTypeList = DataType.values();
					Log.v("DEBUG", "sendData!");

					boolean success = true;
					DataItem item = null;
					String debugText = userID
							+ ", "
							+ deviceID
							+ ", "
							+ Settings.Secure.getString(
									context.getContentResolver(),
									Settings.Secure.ANDROID_ID) + "\n";
					debugText = debugText + Build.MANUFACTURER + " "
							+ Build.MODEL + "\n";
					debugText = debugText + userName + "\n";
					for (int i = 0; i < numTypes; i++) {
						todoList[i] = false;
						dataItemList[i] = null;
					}
					System.gc();
					for (int i = 0; i < numTypes; i++) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String error = "";
						String summary = "";
						item = null;

						if (i == i(DataType.UserDevice)) {
							if (identificationData != null) {
								item = identificationData.getDataItem();
							}
						} else if (i == i(DataType.Spectrum)) {
							if (spectrumData != null) {
								item = spectrumData.getDataItem();
							}
						} else if (i == i(DataType.File)) {
							if (fileData != null) {
								item = fileData.getDataItem();
							}
						} else if (i == i(DataType.Music)) {
							if (musicData != null) {
								item = musicData.getDataItem();
							}
						} else if (i == i(DataType.StepDetection)) {
							if (stepData != null) {
								item = stepData.getDataItem();
								summary = stepData.getSummary();
							}
						} else if (i == i(DataType.Location)) {
							if (locationData != null) {
								item = locationData.getDataItem();
								summary = locationData.getSummary();
							}
						} else if (i == i(DataType.Orientation)) {
							if (orientationData != null) {
								item = orientationData.getDataItem();
							}
						} else if (i == i(DataType.Battery)) {
							if (batteryData != null) {
								item = batteryData.getDataItem();
							}
						} else if (i == i(DataType.PixelErrorFront)) {
							if (cameraData != null) {
								item = cameraData
										.getDataItem(DataType.PixelErrorFront);

							}
						} else if (i == i(DataType.PixelErrorBack)) {
							if (cameraData != null) {
								item = cameraData
										.getDataItem(DataType.PixelErrorBack);

							}
						} else if (i == i(DataType.DarkFrameFront)) {
							if (cameraData != null) {
								item = cameraData
										.getDataItem(DataType.DarkFrameFront);

							}
						} else if (i == i(DataType.DarkFrameBack)) {
							if (cameraData != null) {
								item = cameraData
										.getDataItem(DataType.DarkFrameBack);

							}
						}

						if (item != null) {
							error = DataItem.getLastError();
							if (sendData(item) == false) {
								debugText = debugText + "failed: " + item.getType()
										+ "   " + summary + "  " + error + "\n";
								success = false;
							} else {
								debugText = debugText + "sumbitted: "
										+ item.getType() + "   " + summary + "  "
										+ error + "\n";

							}
						} else {
							debugText = debugText + "no data: "
									+ dataTypeList[i] + "\n";
						}

						item.clear();
						item = null;
						System.gc();

					}
					if (notification) {
						listener.onReceiveDataSubmitted(success);
					}
					NetworkClient networkClient = new NetworkClient();
					Bundle message = new Bundle();

					message.putString("id", userID);
					message.putString("type", "submit");
					try {
						message.putString("debug",
								URLDecoder.decode(debugText, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						message.putString("debug", "encoding error");
					}
					networkClient.postBundleData(debugURL, message,
							NetworkClient.DataType.Byte, true);
					return true;
				}

			} else {
				if (waitForIdentificationItem == false) {
					if (identificationData.addIdentificationData()) {
						todoList[i(DataType.UserDevice)] = false;
						waitForIdentificationItem = true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Send data runnable.
	 *
	 * @param notification the notification
	 * @return true, if successful
	 */
	public boolean sendDataRunnable(final boolean notification) {
		if (NetworkClient.networkAvailable()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					while (identificationData.isReady() == false) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					sendData(notification);
					Looper.loop();
				}
			}).start();
			return true;
		} else {
			if (listener != null) {
				if (notification) {
					listener.onReceiveDataSubmitted(false);
				}
			}
			return false;
		}
	}

	/* 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Looper.prepare();

		RealtimeData.init(context, this);

		if (service != null) {
			service.checkUpdateNotification(false);
		}
		Log.v(TAG, "context: " + context);
		if (identificationData == null) {
			identificationData = new IdentificationData(context, this);
		}
		cameraData = new CameraData(context, surfaceHolder, this);
		cameraData.takePictures();
		orientationData = new OrientationData(this);
		sensorData = new SensorData(context, orientationData, this);
		stepData = new StepData(context, sensorData, this);
		stepData.registerListener();
		locationData = new LocationData(context, this);
		musicData = new MusicData(context, this);
		fileData = new FileData(this);
		batteryData = new BatteryData(context, this);
		spectrumData = new SpectrumData();
		spectrumData.start();

		stepDetector = new StepDetector();
		sensorData.setStepDetector(stepDetector);
		stepDetector.setStepData(stepData);
		sensorData.registerStepListeners();
		// }
		orientationData.setSensorData(sensorData);

		if (identificationData != null) {
			if (userID.length() == IdentificationConfiguration.hashLength
					&& deviceID.length() == IdentificationConfiguration.hashLength) {
				identificationData.onReceiveIds(deviceID, userID);
			}
		}

		boolean identificationDataSent = false;

		long currentTime = System.currentTimeMillis();

		resetTimer();

		long sendItemsTimer = currentTime - 42900000;
		long sendItemsWaitTime = 43200000;

		long updateTimer = -1800000;
		long updateWaitTime = 1800000;

		DataItem item = null;

		Log.v(TAG, "while(running)");
		while (running) {

			if (sendBatteryRealTime) {
				RealtimeData.send(batteryData.getBatteryItemList());
				sendBatteryRealTime = true;
			}

			item = null;
			if (sendlistenerUpdate == true) {
				if (listener != null) {
					listener.onReceiveUpdate();
				}
				sendlistenerUpdate = false;
			}
			currentTime = System.currentTimeMillis();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (currentTime - updateTimer > updateWaitTime) {
				Log.v("DEBUG", "CHECK FOR UPDATES");
				if (service != null) {
					if (newUpdateAvailable()) {
						try {
							Uri notification = RingtoneManager
									.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
							Ringtone r = RingtoneManager.getRingtone(context,
									notification);
							r.play();
						} catch (Exception e) {
							e.printStackTrace();
						}
						service.checkUpdateNotification(true);
					} else {
						service.checkUpdateNotification(false);
					}
					checkResetData();
				}
				updateTimer = currentTime;
			}

			if (currentTime - sendItemsTimer > sendItemsWaitTime) {
				if (sendData(false)) {
					sendItemsWaitTime = currentTime;
				}

			}

			if (SystemClock.elapsedRealtime() > 10000) {
				if (item == null) {
					for (int i = 0; i < numTypes; i++) {
						if (waitTime[i] != 0) {
							if (currentTime - timer[i] > waitTime[i]) {
								if (i == i(DataType.UserDevice)) {
									if (identificationData != null) {
										if (NetworkClient.networkAvailable()) {
											Log.v(TAG,
													"TODO!!!: identificationDataSent: "
															+ identificationDataSent);
											if (waitForIdentificationItem == false) {
												if (userID.length() > 0
														&& deviceID.length() > 0) {
													if (identificationData
															.updateIdentificationData()) {
														todoList[i(DataType.UserDevice)] = false;
														waitForIdentificationItem = true;
													}
												} else {
													if (identificationData
															.addIdentificationData()) {
														todoList[i(DataType.UserDevice)] = false;
														waitForIdentificationItem = true;
													}
												}
											}
										}
									}
								} else if (i == i(DataType.Spectrum)) {
									if (spectrumData != null) {
										spectrumData.start();
									}
								} else if (i == i(DataType.File)) {
									if (fileData != null) {
										fileData.addFileList();
									}
								} else if (i == i(DataType.Music)) {
									if (musicData != null) {
										musicData.addMusicList();
									}
								} else if (i == i(DataType.StepDetection)) {
									if (stepData != null) {

										item = stepData.getDataItem();
										if (item != null) {
											todoList[i(DataType.StepDetection)] = false;
										}
									}
								} else if (i == i(DataType.Location)) {
									if (locationData != null) {
										if (sendLocationCounter % 12 == 0) {
											locationData.sendLocations(true);
										}
										locationData.registerListener(false);

										sendLocationCounter++;
									}
								} else if (i == i(DataType.Orientation)) {
									if (orientationData != null) {
										orientationData.startListening();
										sensorData
												.registerOrientationListeners();
									}
								} else if (i == i(DataType.PixelErrorBack)) {
									if (cameraData != null) {
										cameraData.takePictures();
									}
								}
								timer[i] = currentTime;
								break;
							}
						}
					}
				}
				if (item == null) {
					try {
						item = dataQueue.poll();
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (item != null) {
					if (item.getData() != null) {
						if (listener != null) {
							listener.onReceiveUpdate();
						}
						storeData(item);

						if (NetworkClient.networkAvailable()) {
							if (sendData(item) == false) {
								todoList[item.getTypeIndex()] = keepItemData(item.getType());
								dataItemList[item.getTypeIndex()] = item;
							} else {
								todoList[item.getTypeIndex()] = false;
							}

						} else {
							todoList[item.getTypeIndex()] = true;
							dataItemList[item.getTypeIndex()] = item;
						}
						saveTodoList();
					}
				}
				if (todoTimer == 0
						|| System.currentTimeMillis() - todoTimer > todoWaitTime) {
					Log.v(TAG,
							"TODO!!!: networkAvailable: "
									+ NetworkClient.networkAvailable());
					if (NetworkClient.networkAvailable()) {
						todo();
						if (userID.length() == 0 || deviceID.length() == 0) {
							if (waitForIdentificationItem == false) {
								if (identificationData.addIdentificationData()) {
									todoList[i(DataType.UserDevice)] = false;
									waitForIdentificationItem = true;
								}
							}
						}
						todoWaitTime = 3600000;
					} else {
						todoWaitTime = 300000;
					}
					todoTimer = System.currentTimeMillis();
				}
			}
		}
		Log.v(TAG, "end loop!");
		Log.v(TAG, "running: " + running);
		Looper.loop();
	}

	/**
	 * Todo.
	 *
	 * @return true, if successful
	 */
	private boolean todo() {
		Log.v(TAG, "todo()");
		for (int i = 0; i < todoList.length; i++) {
			if (todoList[i] && dataItemList[i] != null) {
				if (keepItemData(dataItemList[i].getType())) {
					if (sendData(dataItemList[i])) {
						todoList[i] = false;
					}

				} else {
					todoList[i] = false;
				}
			}
		}
		return true;
	}

	/**
	 * Sets the user name.
	 *
	 * @param n the new user name
	 */
	public static void setUserName(String n){
		userName = n;
	}
	

	/**
	 * Creates the acceleration image.
	 */
	public  void createAccelerationImage() {
		if (stepData != null) {
			stepData.createAccelerationImage();
		}
	}

	/**
	 * Gets the acceleration image.
	 *
	 * @param index the index
	 * @return the acceleration image
	 */
	public  Bitmap getAccelerationImage(int index) {
		if (stepData != null) {
			return stepData.getAccelerationImage(index);
		}
		return null;
	}

	/**
	 * Next cluster index.
	 *
	 * @param index the index
	 * @return the int
	 */
	public  int nextClusterIndex(int index) {
		if (stepData != null) {
			return stepData.nextClusterIndex(index);
		}
		return 0;
	}

	/**
	 * Gets the spectrum image.
	 *
	 * @param index the index
	 * @return the spectrum image
	 */
	public  Bitmap getSpectrumImage(int index) {
		if (spectrumData != null) {
			return spectrumData.getSpectrumImage(index);
		}
		return null;
	}
	
	
	/**
	 * Checks if is user available.
	 *
	 * @return true, if is user available
	 */
	public boolean isUserAvailable(){
		if(identificationData.getIdentificationItem()!=null){
			if(identificationData.getIdentificationItem().getUserDevice()!=null){
				if(identificationData.getIdentificationItem().getUserDevice().getUser()!=null){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if is device available.
	 *
	 * @return true, if is device available
	 */
	public boolean isDeviceAvailable(){
		if(identificationData.getIdentificationItem()!=null){
			if(identificationData.getIdentificationItem().getUserDevice()!=null){
				if(identificationData.getIdentificationItem().getUserDevice().getDevice()!=null){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets the account list string.
	 *
	 * @return the account list string
	 */
	public String getAccountListString() {
		if(isUserAvailable()){
			return identificationData.getIdentificationItem().getUserDevice().getUser().getAccountListString();
		}
		return "";
	}

	/**
	 * Gets the orientation image.
	 *
	 * @return the orientation image
	 */
	public Bitmap getOrientationImage() {
		if (orientationData != null) {
			return orientationData.getOrientationImage();
		}
		return null;
	}
	
	/**
	 * Gets the identification item.
	 *
	 * @return the identification item
	 */
	public IdentificationItem getIdentificationItem(){
		return identificationData.getIdentificationItem();
	}
	
	/**
	 * Gets the music list.
	 *
	 * @return the music list
	 */
	public MusicItemList getMusicList(){
		if(musicData!=null){
			return musicData.getMusicList();
		}
		return null;
	}
	
	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	public FileItemList getFileList(){
		if(fileData!=null){
			return fileData.getFileList();
		}
		return null;
	}
	
	/**
	 * Send battery real time.
	 */
	public void sendBatteryRealTime(){
		sendBatteryRealTime = true;
	}

	/**
	 * Gets the music string.
	 *
	 * @return the music string
	 */
	public String getMusicString() {
		if (musicData != null) {
			return musicData.getMusicString();
		} else {
			return "";
		}
	}

	/**
	 * Gets the file string.
	 *
	 * @return the file string
	 */
	public String getFileString() {
		if (fileData != null) {
			return fileData.getFileString();
		} else {
			return "";
		}
	}

	/**
	 * Gets the location string.
	 *
	 * @return the location string
	 */
	public String getLocationString() {
		if (locationData != null) {
			return locationData.getLocationString();
		} else {
			return "";
		}
	}

	/**
	 * Gets the step counter string.
	 *
	 * @return the step counter string
	 */
	public String getStepCounterString() {
		if (stepData != null) {
			return stepData.getStepCounterString();
		}
		return "";
	}

	/**
	 * Gets the battery string.
	 *
	 * @return the battery string
	 */
	public String getBatteryString() {
		if (batteryData != null) {
			return batteryData.getBatteryString();
		}
		return "";
	}

	/**
	 * Gets the SIM list string.
	 *
	 * @return the SIM list string
	 */
	public String getSIMListString() {
		if(isUserAvailable()){
			return identificationData.getIdentificationItem().getUserDevice().getUser()
				.getSIMListString();
		}
		return "";
	}

	/**
	 * Gets the carrier list string.
	 *
	 * @return the carrier list string
	 */
	public String getCarrierListString() {
		if(isUserAvailable()){
				return identificationData.getIdentificationItem().getUserDevice().getUser()
						.getCarrierListString();
		}
		return "";
	}

	/**
	 * Gets the call log string.
	 *
	 * @return the call log string
	 */
	public String getCallLogString() {
		if(isUserAvailable()){
			return identificationData.getIdentificationItem().getUserDevice().getUser()
					.getCallLogString();
		}
		return "";
	}

	/**
	 * Gets the contact string.
	 *
	 * @return the contact string
	 */
	public String getContactString() {
		if(isUserAvailable()){
			return identificationData.getIdentificationItem().getUserDevice().getUser()
					.getContactString();
		}
		return "";
	}

	/**
	 * Gets the bluetooth string.
	 *
	 * @return the bluetooth string
	 */
	public String getBluetoothString() {
		if(isUserAvailable() && isDeviceAvailable()){
			return identificationData.getIdentificationItem().getUserDevice().getDevice()
					.getBluetoothString()
					+ "\n\n"
					+ identificationData.getIdentificationItem().getUserDevice().getUser()
							.getBluetoothString();
		}
		return "";
	}

	/**
	 * Gets the WLAN string.
	 *
	 * @return the WLAN string
	 */
	public String getWLANString() {
		if(isUserAvailable() && isDeviceAvailable()){
		return identificationData.getIdentificationItem().getUserDevice().getDevice()
				.getWLANString()
				+ "\n\n"
				+ identificationData.getIdentificationItem().getUserDevice().getUser()
						.getWLANString();
		}
		return "";
	}

	/**
	 * Gets the package string.
	 *
	 * @return the package string
	 */
	public String getPackageString() {
		if(isUserAvailable()){
			return identificationData.getIdentificationItem().getUserDevice().getUser()
					.getPackageString();
		}
		return "";
	}

	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveIds(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void onReceiveIds(String deviceID, String userID) {
		this.userID = userID;
		this.deviceID = deviceID;

		Log.v(TAG, "Received  deviceID: " + deviceID + " " + deviceID.length());
		Log.v(TAG, "Received  userID: " + userID + " " + userID.length());

		identificationData.onReceiveIds(deviceID, userID);
		StorageHandler.writeToFile("storage.dat", deviceID + userID);
		Log.v(TAG, "save settingsString: " + "storage.dat " + deviceID + userID);
		String absolutePath = context.getFilesDir().getAbsolutePath();
		int modDate = StorageHandler
				.lastModified(absolutePath + "/storage.dat").getDate();
		StorageHandler.writeToFile("app.dat", String.valueOf(modDate));
	}



	/**
	 * Gets the int.
	 *
	 * @param arr the arr
	 * @param off the off
	 * @return the int
	 */
	public static int getInt(byte[] arr, int off) {
		return arr[off] << 8 & 0xFF00 | arr[off + 1] & 0xFF;
	}

	/**
	 * Send data.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
	private boolean sendData(DataItem item) {
		Log.v(TAG, "sendData(DataItem item)");
		boolean result = false;
		String url = serverURL;

		if (item.getParameter().equals("void") == false) {
			if (deviceID.length() > 0 && userID.length() > 0) {
				if (item.getParameter().length() > 0) {
					url = url + item.getParameter();
					if (identificationData != null) {
						url = url + "&deviceID=" + deviceID + "&userID="
								+ userID;
						url = url + "&type=" + item.getTypeIndex();
					}
				} else if (identificationData != null) {
					url = url + "?deviceID=" + deviceID + "&userID=" + userID;
					url = url + "&type=" + item.getTypeIndex();
				} else {
					url = url + "?type=" + item.getTypeIndex();
				}
			} else {
				return false;
			}

		} else {
			url = url + "?type=" +  item.getTypeIndex();
		}
		Log.v(TAG, "sendData to url: " + url);
		NetworkClient networkClient = new NetworkClient();
		byte[] compressed = DataFactory.compress(item.getData());
		Log.v(TAG, "uncompressed " + item.getData().length);
		Log.v(TAG, "compressed " + compressed.length);
		if (compressed != null) {
			byte[] bytes = networkClient.postByteData(url, compressed,
					NetworkClient.DataType.Byte, true);
			if (bytes != null) {
				if (bytes.length > 0) {
					if (bytes[0] == 31) {
						ResultItem resultItem = (ResultItem) DataFactory.decompressObject(bytes);
						if (resultItem != null) {
							result = resultHandler.getReturnValue(resultItem);
							if (result == true) {
								resultHandler.onReceive(resultItem);
							}
						}
					}
				}
			}
		}
		if (item.getType() == DataType.UserDevice) {
			waitForIdentificationItem = false;
		}
		return result;
	}

	/**
	 * Load todo list.
	 */
	public void loadTodoList() {
		Log.v(TAG, "loadTodoList");
		String path = "todolist.dat";
		FileInputStream file = StorageHandler.readFile(path);
		if (file != null) {
			for (int s = 0; s < DataType.values().length; s++) {
				byte todo = StorageHandler.readByte(file);
				if (todo == 1) {

					DataItem item = loadData(s);
					if (item != null) {
						todoList[i(item.getType())] = keepItemData(item.getType());
						dataItemList[i(item.getType())] = item;

					}

				} else {
					todoList[s] = false;
				}
			}
			StorageHandler.closeFile(file);
		}
	}

	/**
	 * Save todo list.
	 */
	public void saveTodoList() {
		Log.v(TAG, "saveTodoList");
		String path = "todolist.dat";
		FileOutputStream file = StorageHandler.openFile(path);
		if (file != null) {
			Log.v(TAG, "file!=null");
			for (int s = 0; s < DataType.values().length; s++) {
				Log.v(TAG, "todoList[" + s
						+ "]  -> StorageHandler.writeByte(file,(byte))");
				if (todoList[s]) {
					StorageHandler.writeByte(file, (byte) 1);
				} else {
					StorageHandler.writeByte(file, (byte) 0);
				}
			}
			Log.v(TAG, "StorageHandler.closeFile(file);");
			StorageHandler.closeFile(file);
		}
		Log.v(TAG, "saveTodoList done!");
	}

	/**
	 * Load data.
	 *
	 * @param s the s
	 * @return the data item
	 */
	private DataItem loadData(int s) {
		DataItem item = null;
		String path = "item_" + s + ".dat";
		item = (DataItem) StorageHandler.loadObject(path, true);
		return item;
	}

	/**
	 * Store data.
	 *
	 * @param item the item
	 */
	private void storeData(DataItem item) {
		Log.v(TAG, "storeData(DataItem item)");
		String path = "item_" + item.getTypeIndex() + ".dat";
		FileOutputStream file = StorageHandler.openFile(path);
		byte data[] = DataFactory.getData(item);
		if (file != null && data != null) {
			StorageHandler.writeData(file, data);
			StorageHandler.closeFile(file);
		}
	}

	/**
	 * Adds the data.
	 *
	 * @param parameter the parameter
	 * @param data the data
	 * @param type the type
	 */
	public void addData(String parameter, byte[] data, DataType type) {
		Log.v(TAG, "addData(String parameter,byte[] data,DataType type)");
		DataItem item = new DataItem(parameter, data, type);
		try {
			dataQueue.put(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void addData(String parameter,Object dataObject,DataType type){
	 * Log.v(TAG, "addData(String parameter,Object dataObject,DataType type)");
	 * DataItem item = new DataItem(parameter,dataObject,type); try {
	 * dataQueue.put(item); } catch (InterruptedException e) {
	 * e.printStackTrace(); } }
	 */

	/**
	 * Adds the data.
	 *
	 * @param parameter the parameter
	 * @param dataObject the data object
	 */
	public void addData(String parameter, Object dataObject) {
		DataType type = DataItem.getDataType(dataObject);
		if (type != null) {
			Log.v(TAG,
					"addData(String parameter,Object dataObject,DataType type)");
			DataItem item = new DataItem(parameter, dataObject);// ,type)
			try {
				dataQueue.put(item);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (RealtimeData.isRealTimeData(dataObject)) {
			RealtimeData.send(dataObject);
		}
	}

	/**
	 * Load data.
	 */
	public void loadData() {

	}

	/* 
	 * @see com.tum.ident.IdentificationListener#onReceiveDataSubmitted(boolean)
	 */
	@Override
	public void onReceiveDataSubmitted(boolean result) {
		// TODO Auto-generated method stub

	}

}
