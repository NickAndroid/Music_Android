package com.zhntd.nick.rocklite.utils;

import com.zhntd.nick.rocklite.Project;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private String TABLE_NAME = Project.TB_PRAISED_NAME;
	private String CREATE_TABLE;

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 * @param errorHandler
	 */
	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		init();
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// delete the table
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// recreate
		onCreate(db);
	}

	private void init() {
		// remember the white space
		CREATE_TABLE = "CREATE TABLE"
				+ " "
				+ TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ "TITLE TEXT,ARTIST TEXT,PATH TEXT,SONG_ID LONG,ALBUM_ID LONG)";
	}

}
