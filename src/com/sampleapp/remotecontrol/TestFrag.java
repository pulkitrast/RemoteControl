package com.sampleapp.remotecontrol;

import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestFrag extends ListFragment {

	int maxSwCount, maxModcount, i, y;
	static MsterControl mc;

	TestFrag() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.customlist, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		new AlertDialog.Builder(mc).setTitle("Inside onListItemClick(ListView l, View v, int position, long id) ").setMessage("position "+position).show();
		
		mc.loadPresetRoutine(position);
		mc.refreshStatesToView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,
				MsterControl.preset_name_list);
		setListAdapter(adapter);

	}

}
