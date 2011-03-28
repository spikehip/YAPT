package com.bikeonet.android.periodtracker.service;

import java.io.IOException;
import java.util.Date;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.bikeonet.android.periodtracker.util.ConfigurePreferences;
import com.bikeonet.android.periodtracker.util.DataHelper;
import com.bikeonet.android.periodtracker.widget.YAPTAppWidgetProvider;

public class UpdateService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		Context context = getApplicationContext();
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		int appWidgetId = intent.getExtras().getInt(
				AppWidgetManager.EXTRA_APPWIDGET_ID);

		DataHelper dh = new DataHelper(context);
		ConfigurePreferences pref = new ConfigurePreferences(context);

		boolean isAnswered = dh.isPeriodDay(new Date());
		dh.closeDb();

		Log.i("UpdateService", isAnswered ? "today is answered"
				: "today is not answered");

		if (isAnswered) {
			YAPTAppWidgetProvider.setWidgetAnswered(context, appWidgetManager,
					appWidgetId);
		} else {
			Date current = new Date();

			if (!pref.getOnly95()
					|| (pref.getOnly95() && (current.getHours() >= 9 && current
							.getHours() <= 17))) {

				if (pref.getVibrate()) {
					Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					v.vibrate(1000);
				}
				if (pref.getPlaySound()) {
					Uri alert = RingtoneManager
							.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
					MediaPlayer mMediaPlayer = new MediaPlayer();
					try {
						mMediaPlayer.setDataSource(context, alert);
						final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
						if (audioManager
								.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
							mMediaPlayer.prepare();
							mMediaPlayer.setLooping(false);
							// mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
							mMediaPlayer.start();
						}
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
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
