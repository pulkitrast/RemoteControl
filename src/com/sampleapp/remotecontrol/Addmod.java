package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class Addmod extends Activity {

	Switch s[] = new Switch[5];
	RadioGroup rg[] = new RadioGroup[5];
	SharedPreferences sh;
	EditText et;
	Button bt;
	static int[] s_d = { -1, -1, -1, -1, -1 };
	static String[] rg_d = { "NRM", "NRM", "NRM", "NRM", "NRM" };
	static String mod_n = "";
	static int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_mod);
		init();
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mod_n = (et.getText()).toString();
				if (mod_n == null) {
					mod_n = "";
					return;
				}

				Dialog d1 = new Dialog(Addmod.this);
				d1.setTitle("Attempting Sync!");
				TextView tv1 = new TextView(Addmod.this);
				tv1.setText("Enter a Name For Module");
				d1.setContentView(tv1);
				d1.show();
				
				

				try {
					CreateDb entry = new CreateDb(Addmod.this);
					entry.open();
					entry.addData();
					entry.close();
					d1.dismiss();
					Editor editor = sh.edit();
					editor.putInt("size",x);
					editor.commit();
					
				} catch (Exception e) {
					d1.dismiss();
					Dialog d = new Dialog(Addmod.this);
					d.setTitle("ERROR!");
					TextView tv = new TextView(Addmod.this);
					tv.setText("Something is Wrong:"+e);
					d.setContentView(tv);
					d.show();
					
				} 

				
				d1.dismiss();
				Dialog d = new Dialog(Addmod.this);
				d.setTitle("Successful!");
				TextView tv = new TextView(Addmod.this);
				tv.setText("You Module is ready to use.");
				d.setContentView(tv);
				d.show();

			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		s[0] = (Switch) findViewById(R.id.switch1);
		s[1] = (Switch) findViewById(R.id.switch2);
		s[2] = (Switch) findViewById(R.id.switch3);
		s[3] = (Switch) findViewById(R.id.switch4);
		s[4] = (Switch) findViewById(R.id.switch5);
		rg[0] = (RadioGroup) findViewById(R.id.radioGroup1);
		rg[1] = (RadioGroup) findViewById(R.id.radioGroup2);
		rg[2] = (RadioGroup) findViewById(R.id.radioGroup3);
		rg[3] = (RadioGroup) findViewById(R.id.radioGroup4);
		rg[4] = (RadioGroup) findViewById(R.id.radioGroup5);
		bt = (Button) findViewById(R.id.btSync);
		et = (EditText) findViewById(R.id.etModname);
		sh = getSharedPreferences("myprefes", 0);
		x = sh.getInt("size", 0) + 1;
		et.setText("Module" + x);

	}

}
