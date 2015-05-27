package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Customerize extends Activity {
	Spinner prstnames;
	String[] prstList;
	String result = "";
	SQLiteDatabase ourDatabase;
	int colnum;
	CreateDb entry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getPrstList();

		setContentView(R.layout.customerizer);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Customerize.this, android.R.layout.simple_spinner_item,
				prstList);
		prstnames = (Spinner) findViewById(R.id.spinner1);
		prstnames.setAdapter(adapter);
		SharedPreferences sh = getSharedPreferences(null, 0);

	}

	private void getPrstList() {
		// TODO Auto-generated method stub
		entry = new CreateDb(this);
		ourDatabase = entry.openSp();

		String[] columns = new String[] { "_id", "prstname" };
		Cursor c = ourDatabase.query(CreateDb.DATABASE_TABLE3, columns, null,
				null, null, null, null);
		colnum = c.getColumnIndex("prstname");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(colnum) + ":";
		}
		String[] prstList = result.split(":");
		ourDatabase.close();
	}
}
