package com.sampleapp.remotecontrol;

import java.util.Arrays;

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
	Spinner sp_preset_selector;
	Button bt_save, bt_edit, bt_delete, bt_new;
	EditText et_preset_name;
	String[] preset_list;
	String[] preset_sequences;
	String[] switch_states;
	String preset_name = "",new_sequence="";
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
				for (int j = 0; j < 5; j++) {
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
							// stub
							int pos = tb.getId() - 1;
							if (tb.isChecked())
								x[pos] = 1;
							else
								x[pos] = 0;
						}
					}

					);

					panel.addView(tb);
				}
				space.addView(panel);
			}
		} catch (Exception e) {
			Dialog d = new Dialog(Customerize.this);
			d.setTitle("onCreate(Bundle savedInstanceState) !");
			TextView tv = new TextView(Customerize.this);
			tv.setText(e + "\n" + "i " + i + "\n" + "pos " + pos + "\n" + "y"
					+ y + "\n" + "maxModCount " + maxModCount);
			d.setContentView(tv);
			d.show();
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
				n = sp_preset_selector.getSelectedItemPosition();
				String[] sequence = (preset_sequences[n]).split(",");
				ToggleButton t;
				for (i=0;i<maxSwCount;i++){
						x[i]=Integer.parseInt(sequence[i]);
						t=(ToggleButton) findViewById(x[i+1]);
						switch(x[i]){
						case 1: t.setChecked(true);
						break;
						case 0: t.setChecked(false);
						break;
						case 2: t.setEnabled(false);
						}
						
					}
				et_preset_name.setEnabled(true);
				et_preset_name.setFocusable(true);
				et_preset_name.setVisibility(Visibility.MODE_IN);
				et_preset_name.requestFocus();
				et_preset_name.selectAll();
				et_preset_name.setText(sp_preset_selector.getSelectedItem().toString());
				final int id=(int) sp_preset_selector.getSelectedItemId()-1;
							
				bt_save.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						
						try{
							new_sequence=x[0]+",";
							for(i=1;i<maxSwCount;i++){
								new_sequence=new_sequence +x[i]+",";
							}
							preset_name=et_preset_name.getText().toString();
							ourDatabase=entry.openSp();
							ContentValues cv= new ContentValues();
							cv.put("prstname",preset_name );
							cv.put("seqn",new_sequence);
							ourDatabase.update(CreateDb.DATABASE_TABLE3, cv, "_id = "+id, null);
							ourDatabase.close();
							
							
							
						}catch(Exception e){
							
							Dialog d = new Dialog(Customerize.this);
							d.setTitle("bt_save.setOnClickListener(new View.OnClickListener()");
							TextView tv = new TextView(Customerize.this);
							tv.setText("Error"+e);
							d.setContentView(tv);
							d.show();
						}
						
						Dialog d = new Dialog(Customerize.this);
						d.setTitle("Preset saved!");
						TextView tv = new TextView(Customerize.this);
						tv.setText(" New preset: name "+preset_name+"\n sequence "+new_sequence);
						d.setContentView(tv);
						d.show();

					}
				});
				
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
							for (i = 0; i < maxSwCount; i++) {
								result = result + "," + (i + 1);
							}
							Dialog d = new Dialog(Customerize.this);
							d.setTitle("For NEW button");
							TextView tv = new TextView(Customerize.this);
							tv.setText("NEW PRESET CREATED :" + result);
							d.setContentView(tv);
							d.show();
							insertPresetData(presname, result);

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
			Dialog d = new Dialog(Customerize.this);
			d.setTitle("loadTableData()");
			TextView tv = new TextView(Customerize.this);
			tv.setText("error:" + e + "\n" + "preset_list"
					+ Arrays.toString(preset_list) + "\n" + "preset_sequences"
					+ Arrays.toString(preset_sequences) + "\n"
					+ "switch_states" + Arrays.toString(switch_states) + "\n"
					+ "maxModCount" + maxModCount + "\n");
			d.setContentView(tv);
			d.show();
		}
	}

	private void insertPresetData(String name, String seq) {

		ourDatabase = entry.openSp();
		ContentValues cv;
		cv = new ContentValues();
		cv.put("prstname", name);
		cv.put("seqn", seq);
		ourDatabase.insert(CreateDb.DATABASE_TABLE3, null, cv);
		ourDatabase.close();
	}
}
