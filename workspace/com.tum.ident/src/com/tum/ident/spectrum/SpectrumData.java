package com.tum.ident.spectrum;


import java.util.concurrent.Semaphore;

import android.graphics.Bitmap;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.Log;


import com.tum.ident.data.DataItem;
import com.tum.ident.fftpack.RealDoubleFFT;
import com.tum.ident.storage.StorageHandler;


/**
 * The Class SpectrumData.
 */
public class SpectrumData {

	// private static final String TAG = "SpectrumData";
	
	/** The Constant blockSize. */
	public final static int blockSize = 256;
	
	/** The frequency. */
	private int frequency = 44100;
	
	/** The channel configuration. */
	private int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
	
	/** The audio encoding. */
	private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

	/** The audio record. */
	private AudioRecord audioRecord;
	
	/** The transformer. */
	private RealDoubleFFT transformer;
	
	/** The started. */
	private boolean started = false;

	/** The record task. */
	private RecordAudio recordTask;

	/** The spectrum. */
	private double[] spectrum;
	
	/** The variance. */
	private double[] variance;
	
	/** The signum. */
	private double[] signum;
	
	/** The spectrum weight. */
	private double spectrumWeight = 0;
	
	/** The recording timer. */
	private long recordingTimer = 0;
	
	/** The recording time. */
	private long recordingTime = 5000;

	/** The constant sound theshold. */
	private static double constantSoundTheshold = 100;
	
	/** The bmp. */
	private Bitmap bmp = null;
	
	/** The bitmap item. */
	private SpectrumItem bitmapItem = null;

	/** The spectrum items. */
	private SpectrumItemList spectrumItems = new SpectrumItemList();
	
	/** The spectrum semaphore. */
	private Semaphore spectrumSemaphore = new Semaphore(1);

	/**
	 * Instantiates a new spectrum data.
	 */
	public SpectrumData() {
		transformer = new RealDoubleFFT(blockSize);
	}

	/**
	 * Gets the spectrum image2.
	 *
	 * @return the spectrum image2
	 */
	public Bitmap getSpectrumImage2() {
		if (spectrumItems.getList() != null) {
			long maxWeight = -1;

			SpectrumItem maxItem = null;
			for (SpectrumItem sItem : spectrumItems.getList()) {
				if (sItem.getWeight() > maxWeight) {
					maxWeight = sItem.getWeight();
					maxItem = sItem;
				}
			}
			if (maxItem != null) {
				bmp = maxItem.getImage(bitmapItem, bmp);
				bitmapItem = maxItem;
				return bmp;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Gets the spectrum image.
	 *
	 * @param index the index
	 * @return the spectrum image
	 */
	public Bitmap getSpectrumImage(int index) {
		if (spectrumItems.getList() != null) {
			if (spectrumItems.getList().size() > 0) {
				lock();
				index = (index) % spectrumItems.getList().size();
				SpectrumItem newBitmapItem = spectrumItems.getList().get(index);
				bmp = newBitmapItem.getImage(bitmapItem, bmp);
				bitmapItem = newBitmapItem;
				unlock();
			}
		}
		return bmp;
	}
	

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		if (spectrumItems != null) {
			return new DataItem("", spectrumItems);
		}
		return null;
	}

	/**
	 * Lock.
	 */
	public void lock() {
		try {
			spectrumSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unlock.
	 */
	public void unlock() {
		spectrumSemaphore.release();
	}
	
	/**
	 * The Class RecordAudio.
	 */
	private class RecordAudio extends AsyncTask<Void, double[], Void> {

		/* 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			int bufferSize = AudioRecord.getMinBufferSize(frequency,
					channelConfiguration, audioEncoding);
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
					frequency, channelConfiguration, audioEncoding, bufferSize);
			int bufferReadResult;
			short[] buffer = new short[blockSize];
			double[] toTransform = new double[blockSize];
			spectrum = new double[blockSize];
			variance = new double[blockSize];
			signum = new double[blockSize];
			spectrumWeight = 0;

			try {
				audioRecord.startRecording();
			} catch (IllegalStateException e) {
				Log.e("Recording failed", e.toString());

			}
			recordingTimer = System.currentTimeMillis();

			while (System.currentTimeMillis() - recordingTimer < recordingTime) {

				bufferReadResult = audioRecord.read(buffer, 0, blockSize);

				if (isCancelled())
					break;

				for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
					toTransform[i] = buffer[i] / 32768.0; // signed 16 bit
				}

				transformer.ft(toTransform);

				for (int i = 1; i < toTransform.length; i++) {

					double spectrumValue = Math.abs(toTransform[i]);

					spectrum[i] = (spectrum[i] * spectrumWeight + spectrumValue)
							/ (spectrumWeight + 1);

					double signumValue = Math.signum(toTransform[i]);

					signum[i] = (signum[i] * spectrumWeight + signumValue)
							/ (spectrumWeight + 1);

					double varianceValue = spectrumValue - spectrum[i];
					varianceValue = varianceValue * varianceValue;
					variance[i] = (variance[i] * spectrumWeight + varianceValue)
							/ (spectrumWeight + 1);
				}
				spectrumWeight++;

				publishProgress(toTransform);
				if (isCancelled())
					break;
				// return null;
			}

			if(isConstantSound(spectrum,variance)){
				SpectrumItem item = new SpectrumItem(spectrum, variance, signum);
				spectrumItems.add(item);
			}
		

			return null;
		}

		/* 
		 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
		 */
		@Override
		protected void onProgressUpdate(double[]... toTransform) {

		}

		/* 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			try {
				audioRecord.stop();
			} catch (IllegalStateException e) {
				Log.e("Stop failed", e.toString());

			}
			recordTask.cancel(true);
			started = false;
			// }
		}

	}

	/**
	 * Checks if is constant sound.
	 *
	 * @param spectrum the spectrum
	 * @param variance the variance
	 * @return true, if is constant sound
	 */
	public boolean isConstantSound(double[] spectrum,double[] variance){
		double v = 0;
		for (int i = 1; i < variance.length; i++) {
			v = v+variance[i];
		}
		v = v/variance.length;
		if(v < constantSoundTheshold){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Start.
	 */
	public void start() {
		if (started == false) {
			started = true;
			recordTask = new RecordAudio();
			recordTask.execute();
		}
	}

	/**
	 * Stop.
	 */
	public void stop() {
		if (started == true) {
			started = false;
			recordTask.cancel(true);
			// recordTask = null;
		}
	}

	/**
	 * On stop.
	 */
	public void onStop() {

		recordTask.cancel(true);

	}
	

	/**
	 * Load.
	 */
	public void load() {

		String fileName = "spectrum.ser";

		spectrumItems = null;
		spectrumItems = (SpectrumItemList) StorageHandler.loadObject(fileName);
		if (spectrumItems == null) {
			spectrumItems = new SpectrumItemList();
		}

	}
	
	/**
	 * Save.
	 */
	public void save() {
		
		String fileName = "spectrum.ser";

		StorageHandler.saveObject(spectrumItems, fileName);

	}


}
