package com.bikeonet.android.periodtracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.about);
		
		findViewById(R.id.about_okbutton1).setOnClickListener(okOnClick);
	}

	View.OnClickListener okOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			AboutActivity.this.finish();
			
		}
	};
	
}
