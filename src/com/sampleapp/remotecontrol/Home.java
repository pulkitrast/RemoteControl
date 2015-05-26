package com.sampleapp.remotecontrol;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Home extends ListActivity {
	String options[]={"Addmod","Customerize","MsterControl"};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Home.this,android.R.layout.simple_expandable_list_item_1,options));
		
	}

	
	@Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Class gotoOpt = null;
		try {
			gotoOpt = Class.forName("com.sampleapp.remotecontrol."+options[position]);
			Intent i= new Intent(Home.this,gotoOpt);
			startActivity(i);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
