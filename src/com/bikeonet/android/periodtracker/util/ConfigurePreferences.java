package com.bikeonet.android.periodtracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.CheckBox;

public class ConfigurePreferences {
	
	private static final String PREFERENCE_SHOW_NONPERIOD = "com.bikonet.android.periodtracker.preference.shownonperiod";
	private static final String PREFERENCE_PLAY_SOUND = "com.bikonet.android.periodtracker.preference.sound";
	private static final String PREFERENCE_VIBRATE = "com.bikeonet.android.periodtracker.preference.vibrate";
	private static final String PREFERENCE_ONLY95 = "com.bikeonet.android.periodtracker.preference.only95";
	
	private final SharedPreferences prefs;
	
	public ConfigurePreferences(Context context) {
		super();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void savePreferences(boolean shownonperiod, boolean sound, boolean vibra, boolean only95 ) {
		Editor edit = prefs.edit();
		edit.putBoolean(PREFERENCE_SHOW_NONPERIOD, shownonperiod);
		edit.putBoolean(PREFERENCE_PLAY_SOUND, sound);
		edit.putBoolean(PREFERENCE_VIBRATE, vibra);
		edit.putBoolean(PREFERENCE_ONLY95, only95);
		edit.commit();		
	}
	
	public void loadPreferences(CheckBox radio, CheckBox sound, CheckBox vibra, CheckBox only95) { 
		boolean showno = getShowNonPeriods();
		boolean playsound = getPlaySound();
		boolean dovibra = getVibrate();
		
		radio.setChecked(showno);		
		sound.setChecked(playsound);		
		vibra.setChecked(dovibra);		
		only95.setChecked(getOnly95());
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
	public boolean getOnly95() { 
		return prefs.getBoolean(PREFERENCE_ONLY95, true);
	}
	
}
