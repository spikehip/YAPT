package com.bikeonet.android.periodtracker.service;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bikeonet.android.periodtracker.AddnewActivity;
import com.bikeonet.android.periodtracker.util.ConfigurePreferences;
import com.bikeonet.android.periodtracker.util.DataHelper;
import com.bikeonet.android.periodtracker.widget.YAPTAppWidgetProvider;

public class UpdateService extends Service {

	private static final int NOTIFICATION_ID = 1;
	
	@Override
	public void onStart(Intent intent, int startId) {
		Context context = getApplicationContext();
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		Integer appWidgetId = null;
		if (intent.getExtras() != null ) {
			appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
		}

		DataHelper dh = new DataHelper(context);
		ConfigurePreferences pref = new ConfigurePreferences(context);

		boolean isAnswered = dh.isPeriodDay(new Date());
		dh.closeDb();

		Log.i("UpdateService", isAnswered ? "today is answered"
				: "today is not answered");

		if (isAnswered) {
			if (appWidgetId != null)
				YAPTAppWidgetProvider.setWidgetAnswered(context, appWidgetManager,
					appWidgetId);
		} else {
			final Date current = new Date();

			if (!pref.getOnly95()
					|| (pref.getOnly95() && (current.getHours() >= 9 && current
							.getHours() <= 17))) {

				NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				int icon = android.R.drawable.stat_sys_warning;
				CharSequence tickerText = "Answer a question!";
				Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
				CharSequence contentText = "Do you have your period today?";
				Intent answerIntent = new Intent(this, AddnewActivity.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, answerIntent, 0);
				notification.setLatestEventInfo(context, tickerText, contentText, pendingIntent);
				
				if (pref.getVibrate()) {
					notification.defaults |= Notification.DEFAULT_VIBRATE;				}
				if (pref.getPlaySound()) {
					notification.defaults |= Notification.DEFAULT_SOUND;										
				}
				notification.flags |= Notification.FLAG_AUTO_CANCEL;				
				notificationManager.notify(NOTIFICATION_ID, notification);
				
			}
			if (appWidgetId != null)			
				YAPTAppWidgetProvider.setWidgetUnanswered(context,
					appWidgetManager, appWidgetId);
		}

		// super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
