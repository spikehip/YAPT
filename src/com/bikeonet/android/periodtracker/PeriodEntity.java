package com.bikeonet.android.periodtracker;

import java.util.Date;

public class PeriodEntity {

	private Date ts_date;
	private boolean is_period;
	private boolean took_pill;
	private int strength;
	private String notes;
	
	public Date getTs_date() {
		return ts_date;
	}
	public void setTs_date(Date ts_date) {
		this.ts_date = ts_date;
	}
	public boolean isIs_period() {
		return is_period;
	}
	public void setIs_period(boolean is_period) {
		this.is_period = is_period;
	}
	public boolean isTook_pill() {
		return took_pill;
	}
	public void setTook_pill(boolean took_pill) {
		this.took_pill = took_pill;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTs_dateString() {
		return DataHelper.format.format(getTs_date());
	}
	
	
	
}
