package com.bikeonet.android.periodtracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bikeonet.android.periodtracker.entity.PeriodEntity;
import com.bikeonet.android.periodtracker.util.DataHelper;

public class DetailsActivity extends Activity {

	private Long id = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		if ( getIntent().getExtras() != null ) { 
			id = getIntent().getExtras().getLong(YAPTActivity.EXTRA_PERIODENTITY_ID);
			
			DataHelper dh = new DataHelper(this);
			PeriodEntity period = dh.selectById(id);
			dh.closeDb();

			if ( period != null) { 
			TextView notes = (TextView) findViewById(R.id.details_notesText1);
			notes.setText(period.getNotes());	
			
			CheckBox isPeriodBox = (CheckBox) findViewById(R.id.details_isPeriodCheck1);
			CheckBox isPillBox = (CheckBox) findViewById(R.id.details_tookPillCheck1);
			isPeriodBox.setText(period.getTs_dateString());
			isPeriodBox.setChecked(period.isIs_period());
			
			isPillBox.setChecked(period.isTook_pill());
			
			RatingBar strengthBar = (RatingBar) findViewById(R.id.details_ratingBar1);
			strengthBar.setRating(period.getStrength());
			
			}
			else { 
				Toast.makeText(this, "Entity not found error", Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}
	
	public void onSaveClicked(View sender) { 
		
		if ( id != null ) { 
			DataHelper dh = new DataHelper(this);
			CheckBox isPillBox = (CheckBox) findViewById(R.id.details_tookPillCheck1);
			TextView notesText = (TextView) findViewById(R.id.details_notesText1);
			RatingBar strengthBar = (RatingBar) findViewById(R.id.details_ratingBar1);
			
			boolean took_pill = isPillBox.isChecked();
			String notes = notesText.getText().toString();
			float strength = strengthBar.getRating();
			
			dh.update(id, took_pill, strength, notes);
			
			Toast.makeText(this, "Saved changes.", Toast.LENGTH_SHORT).show();
		}
		
		DetailsActivity.this.finish();		
	}
	
	public void onBackClicked(View sender) {
		DetailsActivity.this.finish();		
	}

	
	
}
