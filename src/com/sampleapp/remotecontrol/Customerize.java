package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Customerize extends Activity {
	Spinner prstnamesSel;
	Button btsave, btedit, btdelete, btnew;
	EditText etpname;

	String[] prstList;
	String[] modSeqn1;
	String[] states;
	String pr = "", sq = "", presname = "";
	SQLiteDatabase ourDatabase;
	SharedPreferences sh;
	int colnum1, colnum2, n, i, j, maxSw = 0;
	CreateDb entry;
	static int x[] = new int[999];
	int y = 0, pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerizer);
		init();
		getPrstList();
		setListerners();
		try {

			final LinearLayout space = (LinearLayout) findViewById(R.id.space);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			n = maxSw / 5;
			for (i = 0; i < n; i++) {
				TextView lable = new TextView(this);
				lable.setText("Module name");
				space.addView(lable);
				LinearLayout panel = new LinearLayout(this);
				panel.setOrientation(LinearLayout.HORIZONTAL);

				panel.setGravity(Gravity.CENTER);
				panel.setLayoutParams(params);
				for (int j = 0; j < 5; j++) {

					final ToggleButton tb = new ToggleButton(this);
					tb.setId(y + 1);
					tb.setText("sw" + (y + 1));
					tb.setTextOff("sw" + (y + 1));
					tb.setTextOn("sw" + (y + 1));

					if ("-1".equals(states[y])) {
						x[y] = -1;
						tb.setEnabled(false);
					}
					y++;
					tb.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-button.requestFocus();generated method
							// stub

							if (tb.isChecked())
								x[tb.getId() - 1] = 1;

						}
					}

					);

					panel.addView(tb);
				}
				space.addView(panel);
			}
		} catch (Exception e) {
			Dialog d = new Dialog(Customerize.this);
			d.setTitle("Successful!");
			TextView tv = new TextView(Customerize.this);
			tv.setText(e.getMessage());
			d.setContentView(tv);
			d.show();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Customerize.this, android.R.layout.simple_spinner_item,
				prstList);

		prstnamesSel.setAdapter(adapter);

	}

	private void setListerners() {
		// TODO Auto-generated method stub\

		btedit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				n = prstnamesSel.getSelectedItemPosition();
				String[] sequence = (modSeqn1[n]).split(","); // gets preset
																// sequence
																// string for
				try {

					j = 0;
					for (i = 0; i < maxSw; i++) {
						pos = Integer.parseInt(sequence[j]) - 1;
						if (i == pos) {
							if (!(j == (sequence.length - 1)))

							{
								j++;
							}
							switch (x[pos]) {
							case 0:
								x[pos] = 1;
								break;
							}
						} else {
							x[i] = 0;
						}
					}
				} catch (Exception e) {
					Dialog d = new Dialog(Customerize.this);
					d.setTitle("Error");
					TextView tv = new TextView(Customerize.this);
					tv.setText("i= " + i + "pos= " + pos);
					d.setContentView(tv);
					d.show();
				}
				ToggleButton tb;
				for (i = 0; i < maxSw; i++) {
					tb = (ToggleButton) findViewById(i + 1);
					switch (x[i]) {
					case 1:
						tb.setChecked(true);
						break;
					case -1:
						tb.setEnabled(false);
						break;
					case 0:
						tb.setChecked(false);
						break;
					}
				}
				etpname.setEnabled(true);
				etpname.setFocusable(true);
				etpname.setVisibility(Visibility.MODE_IN);
				etpname.requestFocus();
				etpname.selectAll();
				etpname.setText(prstnamesSel.getSelectedItem().toString());
				btsave.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// Push pname and sequence
						Dialog d = new Dialog(Customerize.this);
						d.setTitle("For edit save");
						TextView tv = new TextView(Customerize.this);
						tv.setText(" PRESET MODIFIED ");
						d.setContentView(tv);
						d.show();

					}
				});
				/*
				 * Dialog d = new Dialog(Customerize.this);
				 * d.setTitle("Successful!"); TextView tv = new
				 * TextView(Customerize.this); tv.setText(modSeqn1[n]);
				 * d.setContentView(tv); d.show();
				 */
			}

		});

		btnew.setOnClickListener(new View.OnClickListener() {
			@Override
			// ON Pressing new
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etpname.setEnabled(true);
				etpname.setFocusable(true);
				etpname.setVisibility(Visibility.MODE_IN);
				btsave.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// ON Pressing Save after new
						presname = etpname.getText().toString();
						if (!presname.isEmpty()) {
							String result = "";
							for (i = 0; i < maxSw; i++) {
								if (x[i] == 1)
									result = result + "," + (i + 1);
							}
							Dialog d = new Dialog(Customerize.this);
							d.setTitle("For NEW button");
							TextView tv = new TextView(Customerize.this);
							tv.setText("NEW PRESET CREATED :" + result);
							d.setContentView(tv);
							d.show();
							addPrstData(presname, result);
							
						} else {
							Dialog d = new Dialog(Customerize.this);
							d.setTitle("WARNING");
							TextView tv = new TextView(Customerize.this);
							tv.setText("Enter a vaild name for preset. ");
							d.setContentView(tv);
							d.show();
						}
					}
				});
			}
		});

	}

	private void init() {

		sh = getSharedPreferences("myprefes", 0);
		btsave = (Button) findViewById(R.id.btsave);
		btedit = (Button) findViewById(R.id.btedit);
		btdelete = (Button) findViewById(R.id.btdelete);
		btnew = (Button) findViewById(R.id.btnew);
		prstnamesSel = (Spinner) findViewById(R.id.spinner1);
		etpname = (EditText) findViewById(R.id.etpname);
		entry = new CreateDb(this);
		maxSw = sh.getInt("size", 1) * 5;

	}

	private void getPrstList() {
		// TODO Auto-generated method stub

		ourDatabase = entry.openSp();
		String[] columns = new String[] { "_id", "prstname", "seqn" };
		Cursor c = ourDatabase.query(CreateDb.DATABASE_TABLE3, columns, null,
				null, null, null, null);
		colnum1 = c.getColumnIndex("prstname");
		colnum2 = c.getColumnIndex("seqn");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			pr = pr + c.getString(colnum1) + ":";
			sq = sq + c.getString(colnum2) + ":";
		}
		prstList = pr.split(":");
		modSeqn1 = sq.split(":");
		columns = new String[] { "swstate", "mode" };

		ourDatabase = entry.openSp();
		c = ourDatabase.query(CreateDb.DATABASE_TABLE2, columns, null, null,
				null, null, null);
		colnum1 = c.getColumnIndex("swstate");
		String s = "";
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			s = s + c.getInt(colnum1) + ",";
		}
		states = s.split(",");
		Dialog d = new Dialog(Customerize.this);
		d.setTitle("States column");
		TextView tv = new TextView(Customerize.this);
		tv.setText(s + "    " + prstList.length);
		d.setContentView(tv);
		d.show();
		ourDatabase.close();
	}

	private void addPrstData(String name, String seq) {

		ourDatabase = entry.openSp();
		ContentValues cv;
		cv = new ContentValues();
		cv.put("prstname", name);
		cv.put("seqn", seq);
		ourDatabase.insert(CreateDb.DATABASE_TABLE3, null, cv);
		ourDatabase.close();
	}
}
