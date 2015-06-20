package com.tum.ident.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.tum.ident.data.DataFactory;


/**
 * The Class StorageHandler.
 */
public class StorageHandler {

	/** The Constant TAG. */
	private static final String TAG = "StorageHandler";

	/** The context. */
	private static Context context;

	/** The storage semaphore. */
	private transient static Semaphore storageSemaphore = new Semaphore(1);

	/**
	 * Sets the context.
	 *
	 * @param c the new context
	 */
	public static void setContext(Context c) {
		context = c;
	}

	/**
	 * Last modified.
	 *
	 * @param path the path
	 * @return the date
	 */
	public static Date lastModified(String path) {
		File file = new File(path);
		Date lastModDate = new Date(file.lastModified());
		return lastModDate;
	}

	/**
	 * Open file.
	 *
	 * @param path the path
	 * @return the file output stream
	 */
	public static FileOutputStream openFile(String path) {
		try {
			FileOutputStream fout = context.openFileOutput(path,
					Context.MODE_PRIVATE);
			if (fout != null) {
				return fout;
			}
		} catch (IOException e) {
			Log.v(TAG, "write failed: " + e.toString());
		}
		return null;
	}

	/**
	 * Read file.
	 *
	 * @param path the path
	 * @return the file input stream
	 */
	public static FileInputStream readFile(String path) {
		try {
			FileInputStream fin = context.openFileInput(path);
			if (fin != null) {
				return fin;
			}
		} catch (IOException e) {
			Log.v(TAG, "read failed: " + e.toString());
		}
		return null;
	}

	/**
	 * Read byte.
	 *
	 * @param fin the fin
	 * @return the byte
	 */
	public static byte readByte(FileInputStream fin) {
		byte data = 0;
		try {
			DataInputStream dataStream = new DataInputStream(fin);
			if (dataStream.available() > 0) {
				data = dataStream.readByte();
			}
		} catch (IOException e) {
			Log.v(TAG, "read data failed: " + e.toString());
		}
		return data;
	}

	/**
	 * Close file.
	 *
	 * @param fout the fout
	 */
	public static void closeFile(FileOutputStream fout) {
		try {
			fout.close();
		} catch (IOException e) {
			Log.v(TAG, "close failed: " + e.toString());
		}
	}

	/**
	 * Close file.
	 *
	 * @param fin the fin
	 */
	public static void closeFile(FileInputStream fin) {
		try {
			fin.close();
		} catch (IOException e) {
			Log.v(TAG, "close failed: " + e.toString());
		}
	}

	/**
	 * Write data.
	 *
	 * @param fout the fout
	 * @param data the data
	 */
	public static void writeData(FileOutputStream fout, byte[] data) {
		try {
			DataOutputStream dataOutputStream = new DataOutputStream(fout);
			dataOutputStream.write(data);
		} catch (IOException e) {
			Log.v(TAG, "write data failed: " + e.toString());
		}
	}

	/**
	 * Write byte.
	 *
	 * @param fout the fout
	 * @param data the data
	 */
	public static void writeByte(FileOutputStream fout, byte data) {
		try {
			DataOutputStream dataOutputStream = new DataOutputStream(fout);
			dataOutputStream.write(data);
		} catch (IOException e) {
			Log.v(TAG, "write data failed: " + e.toString());
		}
	}

	/**
	 * Write string n.
	 *
	 * @param fout the fout
	 * @param data the data
	 */
	public static void writeStringN(FileOutputStream fout, String data) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fout);
			BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
			bwriter.write(data);
			bwriter.newLine();
		} catch (IOException e) {
			Log.v(TAG, "write string failed: " + e.toString());
		}
	}

	/**
	 * Write string.
	 *
	 * @param fout the fout
	 * @param data the data
	 */
	public static void writeString(FileOutputStream fout, String data) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fout);
			outputStreamWriter.write(data);
		} catch (IOException e) {
			Log.v(TAG, "write string failed: " + e.toString());
		}
	}

	/**
	 * Write to file.
	 *
	 * @param path the path
	 * @param data the data
	 */
	public static void writeToFile(String path, byte[] data) {
		try {
			FileOutputStream fout = context.openFileOutput(path,
					Context.MODE_PRIVATE);
			if (fout != null) {
				DataOutputStream dataOutputStream = new DataOutputStream(fout);
				dataOutputStream.write(data);
				fout.close();
			}
		} catch (IOException e) {
			Log.v(TAG, "File write failed: " + e.toString());
		}
	}

	/**
	 * Creates the directory.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	public static boolean createDirectory(String path) {
		File folder = new File(path);
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		return success;
	}

	/**
	 * Compress object.
	 *
	 * @param object the object
	 * @param path the path
	 */
	public static void compressObject(Object object, String path) {
		byte[] data = DataFactory.compress(object);
		if (data != null) {
			FileOutputStream file = openFile(path);
			if (file != null) {
				writeData(file, data);
				closeFile(file);
			}
		}
	}

	/**
	 * Save object.
	 *
	 * @param object the object
	 * @param path the path
	 */
	public static void saveObject(Object object, String path) {
		saveObject(object, path, true);
	}

	/**
	 * Save object.
	 *
	 * @param object the object
	 * @param path the path
	 * @param internal the internal
	 */
	public static void saveObject(Object object, String path, boolean internal) {
		Log.v(TAG, "\nSave Object: " + path);
		FileOutputStream fileOut;
		try {
			storageSemaphore.acquire();
		} catch (InterruptedException e) {
		}
		try {
			if (internal == false) {
				fileOut = new FileOutputStream(path);// context.openFileInput(path);//
			} else {
				fileOut = context.openFileOutput(path, Context.MODE_PRIVATE);//
			}
			if (fileOut != null) {
				ObjectOutputStream out;
				out = new ObjectOutputStream(fileOut);
				out.writeObject(object);
				out.close();
				fileOut.close();
			}
			Log.v(TAG, "Object saved: " + path);
		} catch (FileNotFoundException e) {
			Log.v(TAG, "saveObject - FileNotFoundException", e);
		} catch (IOException e) {
			Log.v(TAG, "saveObject - IOException", e);
		} catch (Exception e) {
			Log.v(TAG, "saveObject - Exception", e);
		} catch (StackOverflowError e) {
			Log.v(TAG, "saveObject - StackOverflowError", e);
		}
		storageSemaphore.release();
	}

	/**
	 * Load object.
	 *
	 * @param path the path
	 * @return the object
	 */
	public static Object loadObject(String path) { // TODO
		Log.v(TAG, "\nLoad Object: " + path);
		Object object = null;
		String baseDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String path2 = baseDir + "/" + path;
		object = loadObject(path2, false);
		if (object == null) {
			object = loadObject(path, true);
		} else {
			saveObject(object, path);
			File file = new File(path2);
			if (file != null) {
				if (file.exists()) {
					file.delete();
				}
			}
		}
		return object;
	}

	/**
	 * Load object.
	 *
	 * @param path the path
	 * @param internal the internal
	 * @return the object
	 */
	public static Object loadObject(String path, boolean internal) {
		try {
			storageSemaphore.acquire();
		} catch (InterruptedException e) {
		}
		Log.v("DEBUG", "loadObject: " + path);
		Object object = null;
		try {

			FileInputStream fileIn = null;
			if (internal == false) {
				fileIn = new FileInputStream(path);// context.openFileInput(path);//
			} else {
				fileIn = context.openFileInput(path);//
			}
			if (fileIn != null) {
				ObjectInputStream in = new ObjectInputStream(fileIn);
				object = in.readObject();
				in.close();
				fileIn.close();
			}
			Log.v(TAG, "Object loaded: " + path);
		} catch (IOException i) {
			object = null;
			// Log.v(TAG,"loadObject - IOException",i);
		} catch (ClassNotFoundException c) {
			object = null;
			Log.v(TAG, "loadObject - ClassNotFoundException", c);
		} catch (ArrayIndexOutOfBoundsException a) {
			object = null;
			Log.v(TAG, "ArrayIndexOutOfBoundsException");
		} catch (Exception e) {
			object = null;
			Log.v(TAG, "loadObject - Exception", e);
		} catch (StackOverflowError e) {
			Log.v("DEBUG", "loadObject - StackOverflowError: " + path, e);
		}
		storageSemaphore.release();
		return object;
	}

	/**
	 * Write to file.
	 *
	 * @param path the path
	 * @param data the data
	 */
	public static void writeToFile(String path, String data) {
		try {
			FileOutputStream fout = context.openFileOutput(path,
					Context.MODE_PRIVATE);
			if (fout != null) {
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						fout);
				outputStreamWriter.write(data);
				outputStreamWriter.close();
				fout.close();
				Log.v(TAG, "OutputStreamWriter finished!");
			} else
				Log.v(TAG, "FileOutputStream == null");
		} catch (IOException e) {
			Log.v(TAG, "File write failed: " + e.toString());
		}

	}

	/**
	 * Read from file.
	 *
	 * @param path the path
	 * @return the string
	 */
	public static String readFromFile(String path) {

		String ret = "";

		try {
			InputStream inputStream = context.openFileInput(path);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.v(TAG, "File not found: " + e.toString());
		} catch (IOException e) {
			Log.v(TAG, "Can not read file: " + e.toString());
		}

		return ret;
	}

}
