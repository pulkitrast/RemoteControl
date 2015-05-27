package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

	Switch s[] = new Switch[5];
	RadioGroup rg[] = new RadioGroup[5];
	SharedPreferences sh;
	EditText etmname;
	Button btsyn;
	static int[] s_d = { -1, -1, -1, -1, -1 };
	static String[] rg_d = { "NRM", "NRM", "NRM", "NRM", "NRM" };
	static String mod_n = "";
	int i = 0;
	static int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_mod);
		init();

		btsyn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mod_n = (etmname.getText()).toString();
				for (i = 0; i < 5; i++) {
					if (s[i].isChecked()) {
						s_d[i] = 0;
					}
					RadioButton rb = (RadioButton) findViewById(rg[i]
							.getCheckedRadioButtonId());
					rg_d[i] = rb.getText().toString();
				}

				try {
					CreateDb entry = new CreateDb(Addmod.this);
					entry.open();
					entry.addData();
					entry.close();
					Editor editor = sh.edit();
					editor.putInt("size", x);
					editor.commit();

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
										finish();
									}
								}).show();

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
		btsyn = (Button) findViewById(R.id.btSync);
		etmname = (EditText) findViewById(R.id.etModname);
		sh = getSharedPreferences("myprefes", 0);
		x = sh.getInt("size", 0) + 1;
		etmname.setText("Module" + x);

	}

}
