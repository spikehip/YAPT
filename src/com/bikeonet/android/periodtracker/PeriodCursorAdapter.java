package com.bikeonet.android.periodtracker;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bikeonet.android.periodtracker.util.DataHelper;

public class PeriodCursorAdapter<PeriodEntity> extends SimpleCursorAdapter {

	private int layout;
	
	public PeriodCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.layout = layout;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		int dateCol = cursor.getColumnIndex("ts_date");
		int isPeriodCol = cursor.getColumnIndex("is_period");
		
		Date tsDate;
		
		try { 
			tsDate = DataHelper.format.parse(cursor.getString(dateCol));
		}
		catch ( java.text.ParseException e ) { 
			return;
		}		
		boolean isPeriod = cursor.getInt(isPeriodCol) == 1 ? true : false;
		
    	TextView label = (TextView) view.findViewById(R.id.itemText1);
    	ImageView image = (ImageView) view.findViewById(R.id.itemImage1);
    	if ( label != null)
    		label.setText(DataHelper.format.format(tsDate));
    	if ( image != null )
    		image.setImageResource(isPeriod?R.drawable.yes:R.drawable.no);        	
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		Cursor c = getCursor();
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(layout, parent, false);
		
		int dateCol = c.getColumnIndex("ts_date");
		int isPeriodCol = c.getColumnIndex("is_period");
		Date tsDate;
		
		try { 
			tsDate = DataHelper.format.parse(cursor.getString(dateCol));
		}
		catch ( java.text.ParseException e ) { 
			return null;
		}		
		boolean isPeriod = cursor.getInt(isPeriodCol) == 1 ? true : false;
		
    	TextView label = (TextView) v.findViewById(R.id.itemText1);
    	ImageView image = (ImageView) v.findViewById(R.id.itemImage1);
    	label.setText(DataHelper.format.format(tsDate));
    	image.setImageResource(isPeriod?R.drawable.yes:R.drawable.no);        	
		
		return v;
	}

	
	
}
