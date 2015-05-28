package com.sampleapp.remotecontrol;

import java.util.Arrays;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.LinearLayout.LayoutParams;

public class MsterControl extends Activity {
	CreateDb entry;
	SQLiteDatabase ourDatabase;
	SharedPreferences sh;
	int maxSwCount, i,j, y = 0, maxModCount;
	String[] mod_name_list, 
			 switch_state_list, 
			 switch_mode_list,
			 preset_name_list,
			 preset_sequence_list;
	int x[]=new int[999];
			 

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
			y=0;
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
					if("2".equals(switch_state_list[y])) {
						tb.setEnabled(false);
					}
					x[y]=Integer.parseInt(switch_state_list[y]);
					y++;
					tb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int id=tb.getId();
							if(tb.isChecked()){
								switch_Toggled(id-1,1);
								x[id-1]=0;
							}
								else {
									switch_Toggled(id-1,1);
									x[id-1]=0;
								}
							}
						
					});
					panel.addView(tb);
				}
				space.addView(panel);
			}	
			

		} catch (Exception e) {
			Dialog d = new Dialog(this);
			d.setTitle("onCreate(Bundle savedInstanceState)");
			TextView tv = new TextView(this);
			tv.setText("ERROR :" +e+"\n"
									+"maxModCount:"+maxModCount+"\n"
									+"i:"+i+"\n"
									+"j:"+j+"\n"
									+"y:"+y+"\n"
									+"x[y]:"+x[y]+"\n"
									+"switch_state_list[y]:"+switch_state_list[y]+"\n"
					);
					
			d.setContentView(tv);
			d.show();
		}

	}

	protected void switch_Toggled(int id, int c) {
		// TODO Auto-generated method stub
		try{ourDatabase = entry.openSp();
		ContentValues cv= new ContentValues();
		cv.put("swstate", c);
		ourDatabase.update(CreateDb.DATABASE_TABLE2, cv, "_id = "+id, null);
		}
		catch(Exception e){
			Dialog d = new Dialog(this);
			d.setTitle("switch_Toggled(int id, int c)");
			TextView tv = new TextView(this);
			tv.setText("ERROR :" +e);
					
			d.setContentView(tv);
			d.show();
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
		try{c = ourDatabase.query(CreateDb.DATABASE_TABLE1, columns1, null, null,
				null, null, null);
		pull_data = "";
		col = c.getColumnIndex("mname");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			pull_data = pull_data + c.getString(col) + ",";
		}
		mod_name_list = pull_data.split(",");
		ourDatabase.close();

		ourDatabase = entry.openSp();
		c = ourDatabase.query(CreateDb.DATABASE_TABLE2, columns2, null, null,
				null, null, null);
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
		c = ourDatabase.query(CreateDb.DATABASE_TABLE3, columns3, null, null,
				null, null, null);
		pull_data = "";
		pull_data1 = "";
		col = c.getColumnIndex("prstname");
		col1 = c.getColumnIndex("seqn");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			pull_data = pull_data + c.getString(col) + ",";
			pull_data1 = pull_data1 + c.getString(col1) + ",";
		}
		preset_name_list = pull_data.split(",");
		pull_data.split(",");
		preset_sequence_list = pull_data1.split(",");
		entry.close();
	}catch(Exception e){}
		
		finally{
		Dialog d = new Dialog(this);
		d.setTitle("loadTableData()");
		TextView tv = new TextView(this);
		tv.setText("ERROR :" +"\n"+ "mod_name_list "+ Arrays.toString(mod_name_list)+"\n"+
				"switch_state_list "+ Arrays.toString(switch_state_list)+"\n"+
				"switch_mode_list "+ Arrays.toString(switch_mode_list)+"\n"+
				"preset_name_list "+ Arrays.toString(preset_name_list)+"\n"+
				"preset_sequence_list "+ Arrays.toString(preset_sequence_list)+"\n"
				+"maxModCount"+maxModCount+"\n"
				+"maxSwCount"+maxSwCount);
				
		d.setContentView(tv);
		d.show();
			}
	}
}
