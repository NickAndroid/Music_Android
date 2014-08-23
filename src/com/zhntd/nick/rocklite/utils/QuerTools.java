package com.zhntd.nick.rocklite.utils;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhntd.nick.rocklite.modle.Track;

public class QuerTools {

	private Context mContext;
	private ArrayList<Track> resultList = null;
	private DataBaseHelper helper;
	private SQLiteDatabase database;
	private Cursor cursor;
	private String TAG = "howard database";

	/**
	 * @param mContext
	 */
	public QuerTools(Context mContext) {
		super();
		this.mContext = mContext;
	}

	/**
	 * @param dbName
	 * @param tableName
	 * @param dbVersion
	 * @param order
	 * @return
	 */
	public ArrayList<Track> getListFrmDataBase(String dbName, String tableName,
			int dbVersion, String order, boolean limitCount) {

		try {

			helper = new DataBaseHelper(mContext, dbName, null, dbVersion);
			database = helper.getWritableDatabase();
			cursor = database.query(tableName, null, null, null, null, null,
					order);
			cursor.moveToFirst();
			Log.i(TAG, cursor.getCount() + "::::::::::::::::::::::");
			resultList = new ArrayList<Track>();

			// TITLE TEXT,ARTIST TEXT,ALBUM TEXT,DURATION TEXT,LETTER TEXT,
			// PATH TEXT,SONG_ID INTEGER,ALBUM_ID INTEGER
			while (!cursor.isAfterLast() && cursor.getCount() > 0) {
				Log.i(TAG,
						"begin---------finding in db------------>>>>>>>>>>>>>>>>>>>");
				Track track = new Track();
				track.setTitle(cursor.getString(1));
				track.setArtist(cursor.getString(2));
				track.setUrl(cursor.getString(3));
				track.setId(cursor.getLong(4));
				track.setAlbumId(cursor.getLong(5));

				resultList.add(track);

				if (limitCount) {
					if (resultList.size() > 12) {
						return resultList;
					}
				}
				cursor.moveToNext();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// recreate the table
			++dbVersion;
			helper.onUpgrade(database, --dbVersion, dbVersion);
		}

		return resultList;
	}

	/**
	 * @param song_id
	 * @param dbName
	 * @param tableName
	 * @param dbVersion
	 * @return
	 */
	public boolean checkIfHasAsFavourite(long song_id, String dbName,
			String tableName, int dbVersion) {
		try {

			helper = new DataBaseHelper(mContext, dbName, null, dbVersion);
			database = helper.getWritableDatabase();
			cursor = database.query(tableName, null, "SONG_ID=" + song_id,
					null, null, null, "TITLE DESC");
			if (cursor.getCount() > 0) {
				return true;
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			++dbVersion;
			helper.onUpgrade(database, --dbVersion, dbVersion);
		}
		return false;
	}

	public void removeTrackFrmDatabase(long song_id, String dbName,
			String tableName, int dbVersion) {
		try {
			helper = new DataBaseHelper(mContext, dbName, null, dbVersion);
			database = helper.getWritableDatabase();
			database.delete(tableName, "SONG_ID=?",
					new String[] { song_id + "" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			++dbVersion;
			helper.onUpgrade(database, --dbVersion, dbVersion);
		}
	}

	public void addToDb(ContentValues values, String dbName, String tableName,
			int dbVersion) {

		helper = new DataBaseHelper(mContext, dbName, null, dbVersion);
		database = helper.getWritableDatabase();
		database.insert(tableName, null, values);

	}
}
