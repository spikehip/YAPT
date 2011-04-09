package com.bikeonet.android.periodtracker;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bikeonet.android.periodtracker.util.DataHelper;

public class AddnewActivity extends Activity {

	private Float rating = new Float(0.0);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
				
		RatingBar strengthBar = (RatingBar) findViewById(R.id.details_ratingBar1);
		strengthBar.setEnabled(false);
		
		CheckBox isPeriodBox = (CheckBox) findViewById(R.id.details_isPeriodCheck1);
		isPeriodBox.setClickable(true);
		isPeriodBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				RatingBar strengthBar = (RatingBar) findViewById(R.id.details_ratingBar1);
				
				if ( !isChecked ) { 
					AddnewActivity.this.setRating(strengthBar.getRating());
					strengthBar.setRating(new Float(0.0));
					strengthBar.setEnabled(false);
				}
				else {
					strengthBar.setRating(AddnewActivity.this.getRating());
					strengthBar.setEnabled(true);
				}
			}
		});
		
	}

	public void onSaveClicked(View sender) {

		DataHelper dh = new DataHelper(this);
		CheckBox isPeriod = (CheckBox) findViewById(R.id.details_isPeriodCheck1);
		CheckBox isPillBox = (CheckBox) findViewById(R.id.details_tookPillCheck1);
		TextView notesText = (TextView) findViewById(R.id.details_notesText1);
		RatingBar strengthBar = (RatingBar) findViewById(R.id.details_ratingBar1);

		boolean took_pill = isPillBox.isChecked();
		String notes = notesText.getText().toString();
		float strength = strengthBar.getRating();

		try {
			long id = dh.insert(new Date(), isPeriod.isChecked());
			if ( id != -1 ) { 
				dh.update(id, took_pill, strength, notes);
				Toast.makeText(this, "Saved changes.", Toast.LENGTH_SHORT).show();
			}
		} catch (SQLiteConstraintException e) {
			Log.e("InsertService", e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
		Intent mainIntent = new Intent(AddnewActivity.this, YAPTActivity.class);
		mainIntent.setAction(YAPTActivity.ACTION_UPDATE_CONTENTS);
		startActivity(mainIntent);
	}

	public void onBackClicked(View sender) {
		AddnewActivity.this.finish();
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Float getRating() {
		return rating;
	}
	
	
}
