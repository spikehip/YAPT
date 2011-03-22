package com.bikeonet.android.periodtracker;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YAPTActivity extends Activity {

    private static LayoutParams lparam2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
	
    public static final String ACTION_WIDGET_YES = "com.bikeonet.android.periodtracker.widget.yes";
    public static final String ACTION_WIDGET_NO = "com.bikeonet.android.periodtracker.widget.no";
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        updateContents();
                
    }

    private void updateContents() {
        LinearLayout lines = (LinearLayout) findViewById(R.id.main_linearLayout1);
        lines.removeAllViews();
        DataHelper dh = new DataHelper(getApplicationContext());
        ConfigurePreferences cp = new ConfigurePreferences(this);
        boolean showAll = cp.getShowNonPeriods();
        
        List<PeriodEntity> list = dh.selectAll(showAll);
        dh.closeDb();

        for (PeriodEntity periodEntity : list) {
        	
            LinearLayout line = new LinearLayout(this);
            line.setLayoutParams(lparam2);
            line.setMinimumHeight(60);
            line.setOrientation(LinearLayout.VERTICAL);
            line.setBackgroundColor(Color.WHITE);
            line.setPadding(0, 2, 0, 2);

            TextView titleView = new TextView(this);
            titleView.setText(periodEntity.getTs_dateString());
            titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);
            titleView.setTextSize(14);
            titleView.setTextColor(Color.BLACK);
            titleView.setLines(1);
            titleView.setPadding(20, 20, 0, 20);
            line.addView(titleView);
            
            ImageView imgView = new ImageView(this);
            imgView.setImageResource(periodEntity.isIs_period()?R.drawable.yes:R.drawable.no);
            imgView.setMinimumHeight(60);
            imgView.setMaxWidth(60);
            imgView.setPadding(5, 5, 5, 5);
            line.addView(imgView);

            lines.addView(line);
			
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) { 
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.yapt_calendar_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.calendar_menu_settings:     		
    		YAPTActivity.this.startActivity(new Intent(YAPTActivity.this, ConfigureActivity.class));    		    		
    		break;
    	case R.id.calendar_menu_about:
    		YAPTActivity.this.startActivity(new Intent(YAPTActivity.this, AboutActivity.class));    		
    		break;	
    	case R.id.calendar_menu_removeall:
			DataHelper dh = new DataHelper(this);
			dh.deleteAll();
			dh.closeDb();
			Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			v.vibrate(5000);
			updateContents();
    		break;
    	default: 
			Toast.makeText(this, "Feature not implemented yet ", Toast.LENGTH_SHORT).show();
			break;
    	}
    	return true;
    }
    
}