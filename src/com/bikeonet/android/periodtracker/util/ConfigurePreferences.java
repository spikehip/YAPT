package com.bikeonet.android.periodtracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class ConfigurePreferences {
	
	private static final String PREFERENCE_NOTICE_INTERVAL = "com.bikeonet.android.periodtracker.preference.updatetime";
	private static final String PREFERENCE_SHOW_NONPERIOD = "com.bikonet.android.periodtracker.preference.shownonperiod";
	private static final String PREFERENCE_PLAY_SOUND = "com.bikonet.android.periodtracker.preference.sound";
	private static final String PREFERENCE_VIBRATE = "com.bikeonet.android.periodtracker.preference.vibrate";
	private final SharedPreferences prefs;
	
	public ConfigurePreferences(Context context) {
		super();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void savePreferences(int seekbarpos, boolean shownonperiod, boolean sound, boolean vibra ) {
		Editor edit = prefs.edit();
		edit.putInt(PREFERENCE_NOTICE_INTERVAL, seekbarpos);
		edit.putBoolean(PREFERENCE_SHOW_NONPERIOD, shownonperiod);
		edit.putBoolean(PREFERENCE_PLAY_SOUND, sound);
		edit.putBoolean(PREFERENCE_VIBRATE, vibra);
		edit.commit();		
	}
	
	public void loadPreferences(SeekBar seekBar, CheckBox radio, CheckBox sound, CheckBox vibra) { 
		boolean showno = getShowNonPeriods();
		boolean playsound = getPlaySound();
		boolean dovibra = getVibrate();
		int seekpos = getNoticeInterval();
		
		seekBar.setProgress(seekpos);
		radio.setChecked(showno);		
		sound.setChecked(playsound);		
		vibra.setChecked(dovibra);		
	}
	
	public boolean getShowNonPeriods() { 
		return prefs.getBoolean(PREFERENCE_SHOW_NONPERIOD, true);
	}
	public boolean getPlaySound() { 
		return prefs.getBoolean(PREFERENCE_PLAY_SOUND, true);
	}
	public boolean getVibrate() { 
		return prefs.getBoolean(PREFERENCE_VIBRATE, false);
	}
	
	public int getNoticeInterval() {
		return prefs.getInt(PREFERENCE_NOTICE_INTERVAL, 3600000);
	}

}
