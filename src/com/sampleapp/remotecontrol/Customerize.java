package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Customerize extends Activity{
	Spinner presetname;
	String[] presetList={"Evening","morning","night","sleep"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerizer);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(Customerize.this,android.R.layout.simple_spinner_item,presetList);
		presetname=(Spinner) findViewById(R.id.spinner1);
		presetname.setAdapter(adapter);
		SharedPreferences sh=getSharedPreferences(null, 0);
	}
}
