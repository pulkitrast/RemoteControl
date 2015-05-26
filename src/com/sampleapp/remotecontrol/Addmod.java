package com.sampleapp.remotecontrol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class Addmod extends Activity implements OnClickListener
		 {
	Switch s[]=new Switch[5];
	RadioGroup rg[]=new RadioGroup[5];
	SharedPreferences sh;
	EditText et;
	Button bt;
	static int[] s_d={-1,-1,-1,-1,-1};
	static	String[] rg_d={"NRM","NRM","NRM","NRM","NRM"};
	static	String mod_n="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		sh = getSharedPreferences("myprefes", 0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_mod);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		s[0] = (Switch) findViewById(R.id.switch1);
		s[1] = (Switch) findViewById(R.id.switch2);
		s[2] = (Switch) findViewById(R.id.switch3);
		s[3] = (Switch) findViewById(R.id.switch4);
		s[4] = (Switch) findViewById(R.id.switch5);
		rg[0]=(RadioGroup) findViewById(R.id.radioGroup1);
		rg[1]=(RadioGroup) findViewById(R.id.radioGroup2);
		rg[2]=(RadioGroup) findViewById(R.id.radioGroup3);
		rg[3]=(RadioGroup) findViewById(R.id.radioGroup4);
		rg[4]=(RadioGroup) findViewById(R.id.radioGroup5);
		bt=(Button) findViewById(R.id.btSync);
		et=(EditText) findViewById(R.id.etModname);
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==bt.getId())
		{
			
			
			for(int i=0;i<5;i++){
				if(s[i].isChecked()) s_d[i]=0;
				rg_d[i] = ((RadioButton) findViewById(rg[i].getCheckedRadioButtonId())).getText().toString();
				mod_n=(et.getText()).toString();
				if (mod_n==null) return;
				CreateDb entry = new CreateDb(Addmod.this);
				entry.open();
				entry.addRow();
				
			}
			
			
		
		
		
		}
			
			
		}

	}


	


