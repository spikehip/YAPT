package com.bikeonet.android.periodtracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.bikeonet.android.periodtracker.R;
import com.bikeonet.android.periodtracker.YAPTActivity;
import com.bikeonet.android.periodtracker.service.InsertService;
import com.bikeonet.android.periodtracker.service.UpdateService;

public class YAPTAppWidgetProvider extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
    		            
            Intent intent3 = new Intent(context, UpdateService.class);
            intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);            
            context.startService(intent3);
            
//            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, cp.getNoticeInterval(), pendingIntent);
            
        }
    }

	public static void setWidgetAnswered(Context context, AppWidgetManager appWidgetManager, int appWidgetId) { 
		Log.i("WidgetProvider", "setAnswered");
		
    	RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.yapt_appwidget);
		
    	rv.setViewVisibility(R.id.widget_no, View.GONE);
    	rv.setViewVisibility(R.id.widget_yes, View.GONE);
    	rv.setViewVisibility(R.id.widget_answeredLayout, View.VISIBLE);
    	
    	Intent intent1 = new Intent(context, YAPTActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
    	rv.setOnClickPendingIntent(R.id.widget_openApp, pendingIntent);
		
        appWidgetManager.updateAppWidget(appWidgetId, rv);
	}
	
	public static void setWidgetUnanswered(Context context, AppWidgetManager appWidgetManager, int appWidgetId) { 
		
		Log.i("WidgetProvider", "setUnanswered");
		
    	RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.yapt_appwidget);
		
    	rv.setViewVisibility(R.id.widget_no, View.VISIBLE);
    	rv.setViewVisibility(R.id.widget_yes, View.VISIBLE);
    	rv.setViewVisibility(R.id.widget_answeredLayout, View.GONE);
    	
        Intent intent1 = new Intent(context, InsertService.class);
        intent1.setAction(YAPTActivity.ACTION_WIDGET_YES);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent2 = new Intent(context, InsertService.class);
        intent2.setAction(YAPTActivity.ACTION_WIDGET_NO);
        intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent2 = PendingIntent.getService(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        
        // Get the layout for the App Widget and attach an on-click listener to the button
        rv.setOnClickPendingIntent(R.id.widget_yes, pendingIntent);
        rv.setOnClickPendingIntent(R.id.widget_no, pendingIntent2);
		
        appWidgetManager.updateAppWidget(appWidgetId, rv);
        
	}
	
	
	
}
