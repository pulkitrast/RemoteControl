package com.sampleapp.remotecontrol;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDb {


	private static final String DATABASE_NAME = "RemoteCtrlDB";
	private static final String DATABASE_TABLE1 = "ModIndex";
	private static final String DATABASE_TABLE2 = "SwchIndex";
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
			db.execSQL("Create table "+ DATABASE_TABLE1	+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, mname TEXT)");
			db.execSQL("Create table "+ DATABASE_TABLE2	+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, preset INTEGER,swstate INTEGER, mode TEXT )");
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

	public void addData() {
		// TODO Auto-generated method stub
		ContentValues cv1,cv2,cv3;
		cv1=new ContentValues();
		cv1.put("mname", Addmod.mod_n);
		ourDatabase.insert(DATABASE_TABLE1,null,cv1);
		cv2=new ContentValues();
		for (int i=0;i<5;i++)
		{
			cv2.put("preset", 0);
			cv2.put("SwState", Addmod.s_d[i]);
			cv2.put("mode", Addmod.rg_d[i]);
		
		}
		ourDatabase.insert(DATABASE_TABLE2,null,cv2);
		
		
	}
	
}
