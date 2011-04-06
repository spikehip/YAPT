package com.bikeonet.android.periodtracker.service;

import java.util.Date;

import com.bikeonet.android.periodtracker.YAPTActivity;
import com.bikeonet.android.periodtracker.util.DataHelper;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class InsertService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		Context context = getApplicationContext();
		if (intent.getExtras() != null) {
			int appWidgetId = intent.getExtras().getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID);

			DataHelper dh = new DataHelper(context);// super.onStart(intent,
													// startId);

			if (intent.getAction().equals(YAPTActivity.ACTION_WIDGET_YES)) {
				try {
					Log.i("InsertService", "yes");
					dh.insert(new Date(), true);
				} catch (SQLiteConstraintException e) {
					Log.e("InsertService", e.getMessage());
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}
			if (intent.getAction().equals(YAPTActivity.ACTION_WIDGET_NO)) {
				try {
					Log.i("InsertService", "no");
					dh.insert(new Date(), false);
				} catch (SQLiteConstraintException e) {
					Log.e("InsertService", e.getMessage());
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}

			dh.closeDb();

			Intent intent3 = new Intent(context, UpdateService.class);
			intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			context.startService(intent3);

		}

		// super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
