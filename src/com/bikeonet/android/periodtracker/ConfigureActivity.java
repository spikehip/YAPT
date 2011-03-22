package com.bikeonet.android.periodtracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class ConfigureActivity extends Activity {

	private SeekBar seekBar ;
	private CheckBox radio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure);
		ConfigurePreferences cp = new ConfigurePreferences(this);
		
		findViewById(R.id.configure_savebutton).setOnClickListener(saveOnClick);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		radio = (CheckBox) findViewById(R.id.check0);
		cp.loadPreferences(seekBar, radio);
		
		
	}

	View.OnClickListener saveOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

    		//save preferences     		
			ConfigurePreferences cp = new ConfigurePreferences(ConfigureActivity.this);
    		cp.savePreferences(seekBar.getProgress(), radio.isChecked());
    		
			ConfigureActivity.this.finish();
			
		}
	};
	
}
