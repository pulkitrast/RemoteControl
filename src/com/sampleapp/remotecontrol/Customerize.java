package com.sampleapp.remotecontrol;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
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
	Spinner sp_preset_selector;
	Button bt_save, bt_edit, bt_delete, bt_new;
	EditText et_preset_name;
	String[] preset_list;
	String[] preset_sequences;
	String[] switch_states;
	String preset_name = "", new_sequence = "0";
	SQLiteDatabase ourDatabase;
	CreateDb entry;
	SharedPreferences sh;
	int n, i, j, maxModCount, maxSwCount;

	static int x[] = new int[999];
	int y = 0, pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerizer);
		init();

		loadTableData();

		try {

			final LinearLayout space = (LinearLayout) findViewById(R.id.space);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			for (i = 0; i < maxModCount; i++) {
				TextView lable = new TextView(this);
				lable.setText("Module name");
				space.addView(lable);
				LinearLayout panel = new LinearLayout(this);
				panel.setOrientation(LinearLayout.HORIZONTAL);
				panel.setGravity(Gravity.CENTER);
				panel.setLayoutParams(params);
				for (j = 0; j < 5; j++) {
					final ToggleButton tb = new ToggleButton(this);
					tb.setId(y + 1);
					tb.setText("sw" + (y + 1));
					tb.setTextOff("sw" + (y + 1));
					tb.setTextOn("sw" + (y + 1));

					if ("2".equals(switch_states[y])) {
						x[y] = 2;
						tb.setEnabled(false);
					}
					y++;
					tb.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-button.requestFocus();generated method
							ToggleButton t;
							for (int k = 0; k < maxSwCount; k++) {
								t = (ToggleButton) findViewById(k + 1);
								if (t.isChecked())
									x[k] = 1;
								else if (!t.isEnabled())
									x[k] = 2;
								else
									x[k] = 0;

							}

						}
					}

					);

					panel.addView(tb);
				}
				space.addView(panel);
			}
		} catch (Exception e) {
			message("onCreate(Bundle savedInstanceState) !", "\n" + "i " + i
					+ "\n" + "j " + j + "\n" + "y" + y + "\n" + "maxModCount "
					+ maxModCount + "\n" + "maxSwCount" + maxSwCount);

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Customerize.this, android.R.layout.simple_spinner_item,
				preset_list);
		sp_preset_selector.setAdapter(adapter);

		setListerners();

	}

	private void setListerners() {
		// TODO Auto-generated method stub\
		bt_edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				n = (int) sp_preset_selector.getSelectedItemId();
				String[] sequence = (preset_sequences[n]).split(",");
				ToggleButton t;
				new AlertDialog.Builder(Customerize.this)
						.setTitle("loaded preset")
						.setMessage(
								"preset name "
										+ sp_preset_selector.getSelectedItem()
												.toString()
										+ "\n"
										+ "preset sequence: "
										+ Arrays.toString(sequence)
										+ "\n"
										+ "item position "
										+ sp_preset_selector
												.getSelectedItemId()).show();
				y = 1;
				for (i = 0; i < maxSwCount; i++) {
					t = (ToggleButton) findViewById(i + 1);
					if (!t.isEnabled()) {
						x[i] = 2;
					}
					if (((i + 1)+"").equals(sequence[y])) {
						x[i] = 1;
						if (y < (sequence.length-1))
							y++;
					} else
						x[i] = 0;

				}
				loadCurrentSwitchState();
				et_preset_name.setEnabled(true);
				et_preset_name.setFocusable(true);
				et_preset_name.setVisibility(Visibility.MODE_IN);
				et_preset_name.requestFocus();
				et_preset_name.selectAll();
				et_preset_name.setText(sp_preset_selector.getSelectedItem()
						.toString());

				bt_save.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							for (i = 0; i < maxSwCount; i++) {
								ToggleButton t = (ToggleButton) findViewById(i + 1);
								if (!t.isEnabled())
									x[i] = 2;
								else if (t.isChecked())
									x[i] = 1;
								else
									x[i] = 0;

							}
							new_sequence = "0";
							for (i = 0; i < maxSwCount; i++) {
								if (x[i] == 1)
									new_sequence = new_sequence + "," + (i + 1);
							}
							int id = (int) sp_preset_selector
									.getSelectedItemId() + 1;
							preset_name = et_preset_name.getText().toString();
							message("Saving preset", "preset name "
									+ preset_name + "\n" + "preset sequence: "
									+ new_sequence + "\n id=" + id);

							ourDatabase = entry.openSp();
							ContentValues cv = new ContentValues();
							cv.put("prstname", preset_name);
							cv.put("seqn", new_sequence);
							ourDatabase.update(CreateDb.DATABASE_TABLE3, cv,
									"_id = " + id, null);
							ourDatabase.close();

						} catch (Exception e) {

							message("bt_save.setOnClickListener(new View.OnClickListener()",
									"Error" + e);

						}

						message("Preset saved!", " New preset: name "
								+ preset_name + "\n sequence " + new_sequence);

						refresh();
					}
				});

			}

		});

		bt_new.setOnClickListener(new View.OnClickListener() {
			@Override
			// ON Pressing new
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_preset_name.clearComposingText();
				et_preset_name.setEnabled(true);
				et_preset_name.setFocusable(true);
				et_preset_name.setVisibility(Visibility.MODE_IN);
				et_preset_name.requestFocus();
				bt_save.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// ON Pressing Save after new
						preset_name = et_preset_name.getText().toString();
						if (!preset_name.isEmpty()) {
							// read buttons and store states in x[]
							for (i = 0; i < maxSwCount; i++) {
								ToggleButton t = (ToggleButton) findViewById(i + 1);
								if (!t.isEnabled())
									x[i] = 2;
								else if (t.isChecked())
									x[i] = 1;
								else
									x[i] = 0;

							}
							// make new preset sequence from x[]
							new_sequence = "0";
							for (i = 0; i < maxSwCount; i++) {
								if (x[i] == 1)
									new_sequence = new_sequence + "," + (i + 1);
							}
							// insert the new preset name and sequence in Db
							ourDatabase = entry.openSp();
							ContentValues cv;
							cv = new ContentValues();
							cv.put("prstname", preset_name);
							cv.put("seqn", new_sequence);
							ourDatabase.insert(CreateDb.DATABASE_TABLE3, null,
									cv);
							ourDatabase.close();

							message("bt_new onclick", " New preset: name "
									+ preset_name + "\n sequence "
									+ new_sequence);

						} else {
							message("bt_new onclick",
									"Enter a vaild name for preset. ");

						}
						refresh();
					}
				});
			}
		});

		bt_delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = (int) sp_preset_selector.getSelectedItemId() + 1;
				if (id != 1) {
					ourDatabase = entry.openSp();
					ourDatabase.delete(CreateDb.DATABASE_TABLE3, "_id = " + id,
							null);
					ourDatabase.close();
					refresh();
				}
			}
		});
	}

	protected void refresh() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	protected void loadCurrentSwitchState() {
		// set switch states to x[]
		for (i = 1; i <= maxSwCount; i++) {
			ToggleButton t = (ToggleButton) findViewById(i);

			switch (x[i - 1]) {
			case 1:
				t.setChecked(true);
				break;
			case 0:
				t.setChecked(false);
				break;
			}
		}
	}

	private void init() {

		bt_save = (Button) findViewById(R.id.btsave);
		bt_edit = (Button) findViewById(R.id.btedit);
		bt_delete = (Button) findViewById(R.id.btdelete);
		bt_new = (Button) findViewById(R.id.btnew);
		sp_preset_selector = (Spinner) findViewById(R.id.spinner1);
		et_preset_name = (EditText) findViewById(R.id.etpname);
		entry = new CreateDb(this);
		sh = getSharedPreferences("myprefs", 0);
		maxModCount = sh.getInt("size", 1);
		maxSwCount = maxModCount * 5;
		final ToggleButton NULL = new ToggleButton(this);
		NULL.setChecked(true);
		NULL.setId(0);
	}

	private void loadTableData() {
		// TODO Auto-generated method stub
		try {

			ourDatabase = entry.openSp();
			String pull_data, pull_data1;
			int col, col1;

			String[] columns3 = new String[] { "_id", "prstname", "seqn" };
			String[] columns2 = new String[] { "swstate", "mode" };

			Cursor c = ourDatabase.query(CreateDb.DATABASE_TABLE3, columns3,
					null, null, null, null, null);
			col = c.getColumnIndex("prstname");
			col1 = c.getColumnIndex("seqn");
			pull_data = "";
			pull_data1 = "";
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				pull_data = pull_data + c.getString(col) + ":";
				pull_data1 = pull_data1 + c.getString(col1) + ":";
			}
			preset_list = pull_data.split(":");
			preset_sequences = pull_data1.split(":");
			ourDatabase.close();

			ourDatabase = entry.openSp();
			pull_data = "";
			c = ourDatabase.query(CreateDb.DATABASE_TABLE2, columns2, null,
					null, null, null, null);
			col = c.getColumnIndex("swstate");
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				pull_data = pull_data + c.getInt(col) + ":";
			}
			switch_states = pull_data.split(":");
			ourDatabase.close();

		} catch (Exception e) {
			message("loadTableData()", "error:" + e + "\n" + "preset_list"
					+ Arrays.toString(preset_list) + "\n" + "preset_sequences"
					+ Arrays.toString(preset_sequences) + "\n"
					+ "switch_states" + Arrays.toString(switch_states) + "\n"
					+ "maxModCount" + maxModCount + "\n");

		}
	}

	private void message(String title, String body) {
		Dialog d = new Dialog(Customerize.this);
		d.setTitle(title);
		TextView tv = new TextView(Customerize.this);
		tv.setText(body);
		d.setContentView(tv);
		d.show();
	}
}