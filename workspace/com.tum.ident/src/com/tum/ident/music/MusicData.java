package com.tum.ident.music;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.util.Log;

import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;
import com.tum.ident.util.HashGenerator;


/**
 * The Class MusicData.
 */
public class MusicData implements Runnable {

	/** The tag. */
	private final String TAG = "MusicData";

	/** The context. */
	Context context;
	
	/** The data controller. */
	DataController dataController;
	
	/** The music item list. */
	MusicItemList musicItemList = new MusicItemList();

	/**
	 * Instantiates a new music data.
	 *
	 * @param context the context
	 * @param dataController the data controller
	 */
	public MusicData(Context context, DataController dataController) {
		this.dataController = dataController;
		this.context = context;
	}

	/**
	 * Adds the music list.
	 */
	public void addMusicList() {
		this.run();
	}

	/**
	 * Gets the music string.
	 *
	 * @return the music string
	 */
	public String getMusicString() {
		return musicItemList.getMusicString();
	}

	/**
	 * Gets the music list.
	 *
	 * @return the music list
	 */
	public MusicItemList getMusicList() {
		return musicItemList;
	}

	/* 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				AudioColumns.ARTIST + " ASC");

		if (cursor != null) {
			HashMap<String, MusicItem> map = new HashMap<String, MusicItem>();
			while (cursor.moveToNext()) {
				String artist = cursor.getString(cursor
						.getColumnIndex(AudioColumns.ARTIST));
				if(artist!=null){
					artist = artist.toLowerCase(Locale.ENGLISH);
					String artistHash = HashGenerator.hash(artist);
					MusicItem musicItem = map.get(artistHash);
					if (musicItem == null) {
						musicItem = new MusicItem(artistHash);
					} else {
						musicItem.setCounter(musicItem.getCounter() + 1);
					}
					map.put(artistHash, musicItem);
				}
			}
			Iterator<Map.Entry<String, MusicItem>> it = map.entrySet()
					.iterator();
			if (it != null) {
				while (it.hasNext()) {
					Map.Entry<String, MusicItem> pairs = it.next();
					musicItemList.add(pairs.getValue());
					it.remove();
				}
			}
			Log.v(TAG, "dataController.addData!!!!!!!!!!!!!!!!");
			Log.v(TAG, "listSize: " + musicItemList.size());

			musicItemList.sort();

			dataController.addData("", musicItemList);
		}

	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		if (musicItemList != null) {
			return new DataItem("", musicItemList);
		}
		return null;
	}

}





