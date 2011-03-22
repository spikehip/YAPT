package com.bikeonet.android.periodtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class ConfigurePreferences {
	
	private static final String PREFERENCE_NOTICE_INTERVAL = "com.bikeonet.android.periodtracker.preference.updatetime";
	private static final String PREFERENCE_SHOW_NONPERIOD = "com.bikonet.android.periodtracker.preference.shownonperiod";
	
	private final SharedPreferences prefs;
	
	public ConfigurePreferences(Context context) {
		super();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void savePreferences(int seekbarpos, boolean shownonperiod ) {
		Editor edit = prefs.edit();
		edit.putInt(PREFERENCE_NOTICE_INTERVAL, seekbarpos);
		edit.putBoolean(PREFERENCE_SHOW_NONPERIOD, shownonperiod);
		edit.commit();		
	}
	
	public void loadPreferences(SeekBar seekBar, CheckBox radio) { 
		boolean showno = getShowNonPeriods();
		int seekpos = getNoticeInterval();
		
		seekBar.setProgress(seekpos);
		radio.setChecked(showno);		
	}
	
	public boolean getShowNonPeriods() { 
		return prefs.getBoolean(PREFERENCE_SHOW_NONPERIOD, true);
	}
	
	public int getNoticeInterval() {
		return prefs.getInt(PREFERENCE_NOTICE_INTERVAL, 3600000);
	}

}
