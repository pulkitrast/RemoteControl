package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SetShared extends Activity{
	SharedPreferences sh;
	SetShared(){
		SharedPreferences sh=getSharedPreferences("myprefs", 0);
		Editor editor=sh.edit();
		editor.putInt("modlen", 0);
	}
}
