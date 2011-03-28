package com.bikeonet.android.periodtracker;

import com.bikeonet.android.periodtracker.util.ConfigurePreferences;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class ConfigureActivity extends Activity {

	private SeekBar seekBar ;
	private CheckBox radio;
	private CheckBox sound;
	private CheckBox vibrate;
	private CheckBox only95;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure);
		ConfigurePreferences cp = new ConfigurePreferences(this);
		
		findViewById(R.id.configure_savebutton).setOnClickListener(saveOnClick);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		radio = (CheckBox) findViewById(R.id.check0);
		sound = (CheckBox) findViewById(R.id.config_soundCheck1);
		vibrate = (CheckBox) findViewById(R.id.config_vibrateCheck1);
		only95 = (CheckBox) findViewById(R.id.config_notify95Check1);
		cp.loadPreferences(seekBar, radio, sound, vibrate, only95);
		
		
	}

	View.OnClickListener saveOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

    		//save preferences     		
			ConfigurePreferences cp = new ConfigurePreferences(ConfigureActivity.this);
    		cp.savePreferences(seekBar.getProgress(), radio.isChecked(), sound.isChecked(), vibrate.isChecked(), only95.isChecked());
    		
			ConfigureActivity.this.finish();
			
		}
	};
	
}
