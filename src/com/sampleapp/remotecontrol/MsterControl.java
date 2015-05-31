package com.sampleapp.remotecontrol;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.LinearLayout.LayoutParams;

public class MsterControl extends FragmentActivity {
	static CreateDb entry;
	static SQLiteDatabase ourDatabase;
	SharedPreferences sh;
	Fragment fragment;
	int maxSwCount, i, j, y = 0, maxModCount;
	static String[] mod_name_list;
	static String[] switch_state_list;
	static String[] switch_mode_list;
	static String[] preset_name_list;
	static String[] preset_sequence_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mstr_control);
		init();
		loadTableData();

		try {
			final LinearLayout space = (LinearLayout) findViewById(R.id.mc_space);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			y = 0;

			for (i = 0; i < maxModCount; i++) {
				TextView lable = new TextView(this);
				lable.setText(mod_name_list[i]);
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
					if ("2".equals(switch_state_list[y])) {
						tb.setEnabled(false);
					}
					y++;

					tb.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							int id = tb.getId();
							int state = 0;
							if (tb.isEnabled()) {
								if (tb.isChecked()) {
									state = 1;
								} else
									state = 0;
							}
							switchToggledRoutine(id, state);
							refreshStatesToView();
						}
					});
					panel.addView(tb);
				}
				space.addView(panel);

			}

			FragmentManager manager = getFragmentManager();
			TestFrag.mc = this;
			Fragment frag = new TestFrag();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(R.id.body, frag).commit();

			refreshStatesToView();
		} catch (Exception e) {
			Dialog d = new Dialog(this);
			d.setTitle("onCreate(Bundle savedInstanceState)");
			TextView tv = new TextView(this);
			tv.setText(e + "\n" + "maxModCount:" + maxModCount + "\n" + "i:"
					+ i + "\n" + "j:" + j + "\n" + "y:" + y + "\n"
					+ "switch_state_list[y]:" + switch_state_list[y] + "\n");
			d.setContentView(tv);
			d.show();
		}

	}

	protected void refreshStatesToView() {
		// TODO Auto-generated method stub
		ourDatabase = entry.openSp();
		int col = 0;
		String pull_data = "";
		String[] columns2 = new String[] { "_id", "swstate", "mode" };
		Cursor c = ourDatabase.query(CreateDb.DATABASE_TABLE2, columns2, null,
				null, null, null, null);

		col = c.getColumnIndex("swstate");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			pull_data = pull_data + c.getInt(col) + ",";
		}
		switch_state_list = pull_data.split(",");
		ourDatabase.close();
		ToggleButton tb;

		for (i = 1; i <= maxSwCount; i++) {
			tb = (ToggleButton) findViewById(i);
			switch (switch_state_list[i - 1].charAt(0)) {
			case '2':
				tb.setEnabled(false);
				break;
			case '1':
				tb.setChecked(true);
				break;
			default:
				tb.setChecked(false);
			}

		}
	}

	static void switchToggledRoutine(int id, int state) {
		// TODO Auto-generated method stub
		try {
			ourDatabase = entry.openSp();
			ContentValues cv = new ContentValues();
			cv.put("swstate", state);

			ourDatabase.update(CreateDb.DATABASE_TABLE2, cv, "_id = " + id,
					null);
			ourDatabase.close();
		} catch (Exception e) {

		}
	}

	private void init() {
		// TODO Auto-generated method stub
		entry = new CreateDb(this);
		sh = getSharedPreferences("myprefs", 0);
		maxModCount = sh.getInt("size", 0);
		maxSwCount = maxModCount * 5;

	}

	private void loadTableData() {
		// TODO Auto-generated method stub
		ourDatabase = entry.openSp();
		Cursor c;
		String pull_data, pull_data1;
		int col, col1;

		String[] columns1 = new String[] { "_id", "mname" };
		String[] columns2 = new String[] { "_id", "swstate", "mode" };
		String[] columns3 = new String[] { "_id", "prstname", "seqn" };
		try {
			c = ourDatabase.query(CreateDb.DATABASE_TABLE1, columns1, null,
					null, null, null, null);
			pull_data = "";
			col = c.getColumnIndex("mname");
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				pull_data = pull_data + c.getString(col) + ",";
			}
			mod_name_list = pull_data.split(",");
			ourDatabase.close();

			ourDatabase = entry.openSp();
			c = ourDatabase.query(CreateDb.DATABASE_TABLE2, columns2, null,
					null, null, null, null);
			pull_data = "";
			pull_data1 = "";
			col = c.getColumnIndex("swstate");
			col1 = c.getColumnIndex("mode");
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				pull_data = pull_data + c.getInt(col) + ",";
				pull_data1 = pull_data1 + c.getString(col1) + ",";
			}
			switch_state_list = pull_data.split(",");
			switch_mode_list = pull_data1.split(",");
			ourDatabase.close();

			ourDatabase = entry.openSp();
			c = ourDatabase.query(CreateDb.DATABASE_TABLE3, columns3, null,
					null, null, null, null);
			pull_data = "";
			pull_data1 = "";
			col = c.getColumnIndex("prstname");
			col1 = c.getColumnIndex("seqn");
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				pull_data = pull_data + c.getString(col) + ",";
				pull_data1 = pull_data1 + c.getString(col1) + ":";
			}
			preset_name_list = pull_data.split(",");
			pull_data.split(",");
			preset_sequence_list = pull_data1.split(":");
			entry.close();
		} catch (Exception e) {
		}

		finally {
			Dialog d = new Dialog(this);
			d.setTitle("loadTableData()");
			TextView tv = new TextView(this);
			tv.setText("ERROR :" + "\n" + "mod_name_list "
					+ Arrays.toString(mod_name_list) + "\n"
					+ "switch_state_list " + Arrays.toString(switch_state_list)
					+ "\n" + "switch_mode_list "
					+ Arrays.toString(switch_mode_list) + "\n"
					+ "preset_name_list " + Arrays.toString(preset_name_list)
					+ "\n" + "preset_sequence_list "
					+ Arrays.toString(preset_sequence_list) + "\n"
					+ "maxModCount" + maxModCount + "\n" + "maxSwCount"
					+ maxSwCount);

			d.setContentView(tv);
			d.show();
		}
	}

	public void loadPresetRoutine(int position) {
		// TODO Auto-generated method stub
		// read the selected preset sequence from preset_sequence_list
		String[] selc_seq = preset_sequence_list[position].split(",");
		ContentValues cv = new ContentValues();
		y = 1;
		// change swstate colum in table 2 as per preset sequence
		ourDatabase = entry.openSp();
		for (i = 0; i < maxSwCount; i++) {
			if (((ToggleButton) findViewById(i + 1)).isEnabled()) {
				if (((i + 1) + "").equals(selc_seq[y])) {
					cv.put("swstate", 1);
					if (y < (selc_seq.length-1))
						y++;
				} else
					cv.put("swstate", 0);
				ourDatabase.update(CreateDb.DATABASE_TABLE2, cv, "_id = "
						+ (i + 1), null);

			}

		}
		ourDatabase.close();
	}
}
