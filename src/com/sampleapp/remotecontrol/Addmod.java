package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Addmod extends Activity {

	Switch sw_switch[] = new Switch[5];
	RadioGroup rg_mode[] = new RadioGroup[5];
	SharedPreferences sh;
	EditText et_mod_name;
	SQLiteDatabase ourDatabase;
	CreateDb entry;
	Button bt_sync;
	static int[] sw_default_states = { 2, 2, 2, 2, 2 };
	static String[] rg_default_mode = { "NRM", "NRM", "NRM", "NRM", "NRM" };
	static String mod_name = "";
	int i = 0;
	int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_mod);
		init();

		bt_sync.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mod_name = (et_mod_name.getText()).toString();
				for (i = 0; i < 5; i++) {
					if (sw_switch[i].isChecked()) {
						sw_default_states[i] = 0;
					}
					RadioButton rb_checked = (RadioButton) findViewById(rg_mode[i]
							.getCheckedRadioButtonId());
					rg_default_mode[i] = rb_checked.getText().toString();
				}

				try {
					addData();
					x++;
					Editor editor = sh.edit();
					editor.putInt("size", x);
					editor.commit();
					et_mod_name.setText("Module "+(x+1));
				
					
				} catch (Exception e) {
					Dialog d = new Dialog(Addmod.this);
					d.setTitle("ERROR!");
					TextView tv = new TextView(Addmod.this);
					tv.setText("Something is Wrong:" + e);
					d.setContentView(tv);
					d.show();

				}
				 
				new AlertDialog.Builder(Addmod.this)
						.setTitle("Success!")
						.setMessage(
								"Your Module has been successfully integrated.Press OK to proceed.")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int whichButton) {
										//finish();
									}
								}).show();
			
				

			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub
		sw_switch[0] = (Switch) findViewById(R.id.switch1);
		sw_switch[1] = (Switch) findViewById(R.id.switch2);
		sw_switch[2] = (Switch) findViewById(R.id.switch3);
		sw_switch[3] = (Switch) findViewById(R.id.switch4);
		sw_switch[4] = (Switch) findViewById(R.id.switch5);
		rg_mode[0] = (RadioGroup) findViewById(R.id.radioGroup1);
		rg_mode[1] = (RadioGroup) findViewById(R.id.radioGroup2);
		rg_mode[2] = (RadioGroup) findViewById(R.id.radioGroup3);
		rg_mode[3] = (RadioGroup) findViewById(R.id.radioGroup4);
		rg_mode[4] = (RadioGroup) findViewById(R.id.radioGroup5);
		bt_sync = (Button) findViewById(R.id.btSync);
		et_mod_name = (EditText) findViewById(R.id.etModname);
		entry = new CreateDb(this);
		sh = getSharedPreferences("myprefs", 0);
		x = sh.getInt("size", -1);
		et_mod_name.setText("Module "+x);
		if (x <1) {
			Editor editor = sh.edit();
			editor.putInt("size", 0);
			editor.commit();
			x = 0;
			et_mod_name.setText("Module 1");
		}
		
	}

	private void addData() {
		// TODO Auto-generated method stub
		try {
			ourDatabase = entry.openSp();
			ContentValues cv1, cv2;
			cv1 = new ContentValues();
			cv1.put("mname", mod_name);
			ourDatabase.insert(CreateDb.DATABASE_TABLE1, null, cv1);
			ourDatabase.close();

			ourDatabase = entry.openSp();
			cv2 = new ContentValues();
			for (int i = 0; i < 5; i++) {
				cv2.put("swstate", sw_default_states[i]);
				cv2.put("mode", rg_default_mode[i]);
				ourDatabase.insert(CreateDb.DATABASE_TABLE2, null, cv2);
			}
			ourDatabase.close();
		} catch (Exception e) {
			Dialog d = new Dialog(this);
			d.setTitle("addData()");
			TextView tv = new TextView(this);
			tv.setText("ERROR :" + e + "\n");
			d.setContentView(tv);
			d.show();
		}
	}

}
