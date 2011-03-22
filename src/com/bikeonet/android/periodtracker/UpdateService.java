package com.bikeonet.android.periodtracker;

import java.util.Date;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

public class UpdateService extends Service {
	
	@Override
	public void onStart(Intent intent, int startId) {
		Context context = getApplicationContext();
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
		
        DataHelper dh = new DataHelper(context);
        boolean isAnswered = dh.isPeriodDay(new Date());
        dh.closeDb();
    	
        if ( isAnswered ) { 
        	YAPTAppWidgetProvider.setWidgetAnswered(context, appWidgetManager, appWidgetId);
        }
        else { 
			Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			v.vibrate(5000);        	
			
			YAPTAppWidgetProvider.setWidgetUnanswered(context, appWidgetManager, appWidgetId);
        }
                
//		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
}
