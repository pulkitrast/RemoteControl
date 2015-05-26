package com.sampleapp.remotecontrol;

import android.content.Context;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDb {


	private static final String DATABASE_NAME = "RemoteCtrlDB";
	private static final String DATABASE_TABLE1 = "ModIndex";
	private static final String DATABASE_TABLE2 = "SwchIndex";
	private static final String DATABASE_TABLE3 = "IcnIndex";
	private static final int DATABASE_VERSION = 1;

	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	private DbHelper ourHelper;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("Create table "
					+ DATABASE_TABLE1
					+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, mname TEXT NOT NULL)");
			db.execSQL("Create table "
					+ DATABASE_TABLE2
					+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, sname TEXT NOT NULL,SwState, iconid INTEGER, mode TEXT NOT NULL)");
			db.execSQL("Create table "
					+ DATABASE_TABLE3
					+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT NOT NULL)");
			new SetShared();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS ");
			onCreate(db);
		}
	}

	public CreateDb(Context c) {
		ourContext = c;
	}
	
	public CreateDb open(){
		
		ourHelper= new DbHelper(ourContext);
		ourDatabase=ourHelper.getWritableDatabase();
	return this;
	}
	public void close(){
		ourHelper.close();
	}

	public void addRow() {
		// TODO Auto-generated method stub
		
	}
	
}
