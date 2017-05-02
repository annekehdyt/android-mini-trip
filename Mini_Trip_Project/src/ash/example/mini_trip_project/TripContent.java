package ash.example.mini_trip_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * View the all content of the
 * clicked item in the previous activity
 * @author Anne Soraya
 *
 */
public class TripContent extends Activity {

	TextView mTitle;
	TextView mDest;
	TextView mLoc;
	TextView mDate;
	TextView mTime;
	
	Intent data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_content);
		
		mTitle = (TextView)findViewById(R.id.textView1);
		mDest = (TextView)findViewById(R.id.dest_text);
		mLoc = (TextView)findViewById(R.id.loc_text);
		mDate = (TextView)findViewById(R.id.date_text);
		mTime = (TextView)findViewById(R.id.time_text);
		
		data = getIntent();
		mTitle.setText(data.getStringExtra("paramTitle"));
		mDest.setText(data.getStringExtra("paramDest"));
		mLoc.setText(data.getStringExtra("paramLoc"));
		mDate.setText(data.getStringExtra("paramDate"));
		mTime.setText(data.getStringExtra("paramTime"));
		
		
	}
}
