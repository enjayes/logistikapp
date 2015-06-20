package com.tum.ident.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.tum.ident.IdentificationConfiguration;
import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;
import com.tum.ident.util.HashGenerator;


/**
 * The Class FileData.
 */
public class FileData implements Runnable {

	/** The tag. */
	private final String TAG = "FileData";

	/** The data controller. */
	private DataController dataController;
	
	/** The file item list. */
	private FileItemList fileItemList = new FileItemList();

	/**
	 * Instantiates a new file data.
	 *
	 * @param dataController the data controller
	 */
	public FileData(DataController dataController) {
		this.dataController = dataController;
	}

	/**
	 * Adds the file list.
	 */
	public void addFileList() {
		Log.v(TAG, "addFileList");
		this.run();
	}

	/**
	 * Adds the file hash.
	 *
	 * @param file the file
	 * @param folderType the folder type
	 */
	private void addFileHash(File file, String folderType) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			InputStream in = new FileInputStream(file);
			byte[] buf = new byte[8192];
			for (;;) {
				int len;
				len = in.read(buf);
				if (len < 0)
					break;
				md.update(buf, 0, len);
			}
			in.close();
			byte[] hash = md.digest();
			String fileHash = HashGenerator.bytesToHex(hash);
			if (fileHash.length() > IdentificationConfiguration.hashLength) {
				fileHash = fileHash.substring(0,
						IdentificationConfiguration.hashLength);
			}
			Log.v(TAG, "New FILE: " + file.getAbsolutePath() + " hash: "
					+ fileHash);
			FileItem fileItem = new FileItem(fileHash, folderType);
			fileItemList.add(fileItem);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hash recursively.
	 *
	 * @param fileOrDirectory the file or directory
	 * @param folderType the folder type
	 */
	private void hashRecursively(File fileOrDirectory, String folderType) {
		if (fileOrDirectory != null) {
			if (fileOrDirectory.exists()) {
				if (fileOrDirectory.getAbsolutePath().indexOf(".thumbnails") == -1) {
					if (fileOrDirectory.isDirectory()) {
						Log.v(TAG,
								"FOLDER: " + fileOrDirectory.getAbsolutePath());
						if (fileOrDirectory.listFiles() != null) {
							for (File child : fileOrDirectory.listFiles()) {
								hashRecursively(child, folderType);
							}
						}
					} else {

						addFileHash(fileOrDirectory, folderType);
					}
				}
			}
		}
	}

	/**
	 * Gets the SD card path.
	 *
	 * @return the SD card path
	 */
	@SuppressWarnings("unused")
	private String getSDCardPath() { //todo
		if (new File("/storage/extSdCard/").exists()) {
			return "/storage/extSdCard/";
		}
		if (new File("/storage/sdcard1/").exists()) {
			return "/storage/sdcard1/";
		}
		if (new File("/storage/usbcard1/").exists()) {
			return "/storage/usbcard1/";
		}
		if (new File("/storage/sdcard0/").exists()) {
			return "/storage/sdcard0/";
		}
		return "";
	}

	/**
	 * Gets the file string.
	 *
	 * @return the file string
	 */
	public String getFileString() {
		return fileItemList.getFileString();
	}

	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	public FileItemList getFileList() {
		return fileItemList;
	}

	/* 
	 * @see java.lang.Runnable#run()
	 */
	@SuppressLint("NewApi")
	@Override
	public void run() {
		Log.v(TAG, "public void run()");
		File files = null;

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			files = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
			hashRecursively(files, "DOCUMENTS");
		} else {
			files = new File("storage/emulated/0/Documents"); //todo
			hashRecursively(files, "DOCUMENTS");
		}
		files = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
		hashRecursively(files, "MOVIES");
		files = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		hashRecursively(files, "DCIM");

		files = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		hashRecursively(files, "DOWNLOADS");

		dataController.addData("", fileItemList);
	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		if (fileItemList != null) {
			return new DataItem("", fileItemList);
		}
		return null;
	}

}
