package com.bikeonet.android.periodtracker;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class YAPTAppWidgetConfigure extends Activity {

	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	private SeekBar seekBar ;
	private CheckBox radio;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_CANCELED);
		
		setContentView(R.layout.configure);
		
		findViewById(R.id.configure_savebutton).setOnClickListener(saveOnClickListener);
		
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		radio = (CheckBox) findViewById(R.id.check0);
		ConfigurePreferences cp = new ConfigurePreferences(this);		
		cp.loadPreferences(seekBar, radio);
				
        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
	}

	View.OnClickListener saveOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
            final Context context = YAPTAppWidgetConfigure.this;
                            		
    		//save preferences     		
    		ConfigurePreferences cp = new ConfigurePreferences(context);		
    		cp.savePreferences(seekBar.getProgress(), radio.isChecked());
    		
    		/**
    		 * call update service to update the widget
    		 */
            Intent intent3 = new Intent(context, UpdateService.class);
            intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);            
            context.startService(intent3);
			
    		Intent resultValue = new Intent();
    		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    		setResult(RESULT_OK, resultValue);
    		finish();		
		}
	};
		
}