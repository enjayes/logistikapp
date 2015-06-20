package com.tum.ident.network;

import java.io.UnsupportedEncodingException;

import android.os.AsyncTask;
import android.os.Bundle;

import com.tum.ident.network.NetworkClient.DataType;
import com.tum.ident.result.ResultListener;


/**
 * The Class NetworkClientAsync.
 */
public class NetworkClientAsync extends AsyncTask<Void, Void, byte[]> {

	/** The network client. */
	private NetworkClient networkClient = new NetworkClient();

	/** The url. */
	private String url = "";
	
	/** The result listener. */
	private ResultListener resultListener = null;
	
	/** The bundle params. */
	private Bundle bundleParams = null;
	
	/** The byte params. */
	private byte[] byteParams = null;
	
	/** The post type. */
	private DataType postType = DataType.String; // 0 = Bundle (Strings), 1 = Data
											// (Bytes)
	/** The response type. */
											private DataType responseType = DataType.Byte;// 0 = Strings, 1 = Data (Bytes)

	/**
	 * Post.
	 *
	 * @param url the url
	 * @param bundleParams the bundle params
	 * @param responseType the response type
	 * @param resultListener the result listener
	 */
	public void post(String url, Bundle bundleParams, DataType responseType,
			ResultListener resultListener) {
		if (bundleParams != null) {
			this.resultListener = resultListener;
			this.bundleParams = bundleParams;
			this.postType = DataType.String;
			this.responseType = responseType;
			this.execute();
		}
	}

	/**
	 * Post.
	 *
	 * @param url the url
	 * @param byteParams the byte params
	 * @param responseType the response type
	 * @param resultListener the result listener
	 */
	public void post(String url, byte[] byteParams, DataType responseType,
			ResultListener resultListener) {
		if (bundleParams != null) {
			this.resultListener = resultListener;
			this.byteParams = byteParams;
			this.postType = DataType.Byte;
			this.responseType = responseType;
			this.execute();
		}
	}

	/* 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected byte[] doInBackground(Void... v) {
		byte[] response = new byte[0];
		if (resultListener != null && url != "") {
			if (postType == DataType.String) {
				response = networkClient.postBundleData(url, bundleParams,
						responseType, true);
			} else if (postType == DataType.Byte) {
				response = networkClient.postByteData(url, byteParams,
						responseType, true);
			}
		}
		return response;
	}

	/* 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(byte[] result) {
		if (resultListener != null) {
			if (responseType == DataType.String) {
				try {
					resultListener.onReceive(new String(result, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else if (postType == DataType.Byte) {
				resultListener.onReceive(result);
			}
		}
	}

}