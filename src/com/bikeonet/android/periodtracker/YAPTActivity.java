package com.bikeonet.android.periodtracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bikeonet.android.periodtracker.entity.PeriodEntity;
import com.bikeonet.android.periodtracker.util.ConfigurePreferences;
import com.bikeonet.android.periodtracker.util.DataHelper;

public class YAPTActivity extends Activity {
	public static final SimpleDateFormat formatYearMonth = new SimpleDateFormat("yyyy MMMMM");
	
    public static final String ACTION_WIDGET_YES = "com.bikeonet.android.periodtracker.widget.yes";
    public static final String ACTION_WIDGET_NO = "com.bikeonet.android.periodtracker.widget.no";
    public static final String EXTRA_PERIODENTITY_ID = "com.bikeonet.android.periodtracker.periodentity.id";

	public static final String ACTION_UPDATE_CONTENTS = "com.bikeonet.android.periodtracker.YAPTActivity.updatecontents";

    @Override
	protected void onStart() {
    	if ( getIntent()!=null && getIntent().getAction()!=null && getIntent().getAction().equals(ACTION_UPDATE_CONTENTS)
    			){ 
    		updateContents();
    	}
		super.onStart();
	}

	private List<PeriodEntity> periodList;
    private LinearLayout listScrollView;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        listScrollView = (LinearLayout) findViewById(R.id.listlinearLayout1);
        updateContents();
    }
    
    

    protected PeriodEntity getPeriodById(int position) {
		return periodList.get(position);
	}

	private void updateContents() {
	      
        DataHelper dh = new DataHelper(getApplicationContext());
        ConfigurePreferences cp = new ConfigurePreferences(this);
        boolean showAll = cp.getShowNonPeriods();
        
        periodList = dh.selectAll(showAll);
        dh.closeDb();

        Integer month = null;
        listScrollView.removeAllViews();
        for (PeriodEntity periodEntity : periodList) {
        	
        	if ( month == null || periodEntity.getTs_date().getMonth()!=month) { 
        		addMonthLine(periodEntity.getTs_date());
        		month = periodEntity.getTs_date().getMonth();
        	}
        	
        	View item = getLayoutInflater().inflate(R.layout.item, null);
        	TextView label = (TextView) item.findViewById(R.id.itemText1);
        	ImageView image = (ImageView) item.findViewById(R.id.itemImage1);
        	label.setText(periodEntity.getTs_dateString());
        	image.setImageResource(periodEntity.isIs_period()?R.drawable.yes:R.drawable.no);        	
        	
        	item.setOnClickListener(new PeriodOnClickListener(periodEntity.getId()));
        	
        	listScrollView.addView(item);
		}
	}
	
	private void addMonthLine(Date ts_date) {
    	View item = getLayoutInflater().inflate(R.layout.item, null);
    	item.setBackgroundColor(Color.TRANSPARENT);
    	LinearLayout innerLayout = (LinearLayout) item.findViewById(R.id.itemInnerLayout);
    	innerLayout.setBackgroundColor(Color.TRANSPARENT);
    	item.setMinimumHeight(80);
    	item.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 80));
    	TextView label = (TextView) item.findViewById(R.id.itemText1);
    	label.setPadding(20, 0, 0, 0);
//    	label.setTextColor(Color.WHITE);
    	label.setTypeface(Typeface.DEFAULT_BOLD);
    	label.setTextSize(24);
    	ImageView image = (ImageView) item.findViewById(R.id.itemImage1);
    	label.setText(formatYearMonth.format(ts_date)); 
    	image.setVisibility(View.INVISIBLE);
    	listScrollView.addView(item);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) { 
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.yapt_calendar_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	DataHelper dh ;    	
    	switch(item.getItemId()) {
    	case R.id.calendar_menu_add:
			dh = new DataHelper(this);
			if ( !dh.isPeriodDay(new Date()) ) {
				Intent addIntent = new Intent(YAPTActivity.this, AddnewActivity.class);
				YAPTActivity.this.startActivity(addIntent);
			}
			else { 
				Toast.makeText(this, "Next time gadget, next time!", Toast.LENGTH_SHORT).show();					
			}
			dh.closeDb();
			
    		break;
    	case R.id.calendar_menu_settings:     		
    		YAPTActivity.this.startActivity(new Intent(YAPTActivity.this, ConfigureActivity.class));    		    		
    		break;
    	case R.id.calendar_menu_about:
    		YAPTActivity.this.startActivity(new Intent(YAPTActivity.this, AboutActivity.class));    		
    		break;	
    	case R.id.calendar_menu_removeall:
    		doDeleteAll();
    		break;
    	case R.id.calendar_menu_delete:
    		dh = new DataHelper(this);
			if ( dh.isPeriodDay(new Date()) ) {
				doDeleteToday();
			}
			else {
				Toast.makeText(this, "Sorry, no record to delete yet!", Toast.LENGTH_SHORT).show();					
			}
			break;
    	default: 
			Toast.makeText(this, "Feature not implemented yet ", Toast.LENGTH_SHORT).show();
			break;
    	}
    	return true;
    }

	private void doDeleteToday() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to remove today's record?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		   			DataHelper dh = new DataHelper(YAPTActivity.this);			
					dh.removeToday();
					dh.closeDb();
					updateContents();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();	
		alert.show();
	}

	private void doDeleteAll() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to remove all data?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		   			DataHelper dh = new DataHelper(YAPTActivity.this);			
					dh.deleteAll();
					dh.closeDb();
					updateContents();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();	
		alert.show();
	}

	private class PeriodOnClickListener implements View.OnClickListener {
		
		private final Long periodId;
		
		public PeriodOnClickListener(Long periodId) { 
			this.periodId = periodId;
		}
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(YAPTActivity.this, DetailsActivity.class);
			//set intent extras
			intent.putExtra(EXTRA_PERIODENTITY_ID, periodId);
			YAPTActivity.this.startActivity(intent);
		}
	}
    
}