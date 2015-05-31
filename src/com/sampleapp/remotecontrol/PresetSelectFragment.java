/*package com.sampleapp.remotecontrol;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PresetSelectFragment extends ListFragment  {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
	View view = inflater.inflate(R.layout.customlist, container, false);
	return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    super.onActivityCreated(savedInstanceState);
    loadTableData();
    
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, preset_list);
    setListAdapter(adapter);
    
	}



}
*/