package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Customerize extends Activity {
	Spinner prstnames;
	String[] prstList;
	String result = "";
	SQLiteDatabase ourDatabase;
	int colnum;
	CreateDb entry;
	static int x[] = new int[999];
	int y = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerizer);
		final LinearLayout space = (LinearLayout) findViewById(R.id.space);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	
		for (int i = 0; i < 5; i++) {
			TextView lable = new TextView(this);
			lable.setText("Module name");
			space.addView(lable);
			

			LinearLayout panel = new LinearLayout(this);
			panel.setOrientation(LinearLayout.HORIZONTAL);

			panel.setGravity(Gravity.CENTER);
			panel.setLayoutParams(params);
			for (int j = 0; j < 5; j++) {
				x[y++] = -1;
				final ToggleButton tb = new ToggleButton(this);
				tb.setId(y);
				tb.setText("sw"+ y);
				tb.setTextOff("sw"+ y);
				tb.setTextOn("sw"+ y);
				tb.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-button.requestFocus();generated method stub
					
						if(tb.isChecked()) x[tb.getId()]=1;
					
					}
				}

				);
				
panel.addView(tb);
			}
space.addView(panel);
		}

		getPrstList();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Customerize.this, android.R.layout.simple_spinner_item,
				prstList);
		prstnames = (Spinner) findViewById(R.id.spinner1);
		prstnames.setAdapter(adapter);

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
		prstList= result.split(":");
		ourDatabase.close();
	}
}
