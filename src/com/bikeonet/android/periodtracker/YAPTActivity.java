package com.bikeonet.android.periodtracker;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bikeonet.android.periodtracker.entity.PeriodEntity;
import com.bikeonet.android.periodtracker.util.ConfigurePreferences;
import com.bikeonet.android.periodtracker.util.DataHelper;

public class YAPTActivity extends ListActivity {
	
    public static final String ACTION_WIDGET_YES = "com.bikeonet.android.periodtracker.widget.yes";
    public static final String ACTION_WIDGET_NO = "com.bikeonet.android.periodtracker.widget.no";
    public static final String EXTRA_PERIODENTITY_ID = "com.bikeonet.android.periodtracker.periodentity.id";

    private ArrayList<String> stringContent = new ArrayList<String>();
    private List<PeriodEntity> periodList;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        
        updateContents();

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringContent));
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				PeriodEntity p  = YAPTActivity.this.getPeriodById(position);
				Toast.makeText(getApplicationContext(), p.getTs_dateString(), Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(YAPTActivity.this, DetailsActivity.class);
				//set intent extras
				intent.putExtra(EXTRA_PERIODENTITY_ID, p.getId());
				YAPTActivity.this.startActivity(intent);
			}
		});
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

        for (PeriodEntity periodEntity : periodList) {

        	StringBuffer line = new StringBuffer();
        	line.append(periodEntity.getTs_dateString());
        	line.append(": ");
        	line.append(periodEntity.isIs_period()?"YES":"NO");

            stringContent.add(line.toString());
			
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
    	case R.id.calendar_menu_add:
    		YAPTActivity.this.startActivity(new Intent(YAPTActivity.this, AddnewActivity.class));    		    		
    		break;
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
			finish();
    		break;
    	default: 
			Toast.makeText(this, "Feature not implemented yet ", Toast.LENGTH_SHORT).show();
			break;
    	}
    	return true;
    }
    
}