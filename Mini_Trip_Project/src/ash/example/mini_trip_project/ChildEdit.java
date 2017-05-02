package ash.example.mini_trip_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class ChildEdit extends Activity implements OnClickListener {
	TextView title;
	EditText edit_dest;
	EditText edit_loc;
	DatePicker mDate;
	TimePicker mTime;
	Button btn_ok;
	Button btn_cancel;
	Intent data;
	
	
	String date;
	String time;
	String[] tokenDate;
	String[] tokenTime;
	int key;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtrip_child);
		
		data = getIntent();
		key = data.getIntExtra("paramKey", 0);
		title = (TextView)findViewById(R.id.textView1);
		edit_dest = (EditText)findViewById(R.id.editText1);
		edit_loc = (EditText)findViewById(R.id.editText2);
		mDate = (DatePicker)findViewById(R.id.datePicker1);
		mTime = (TimePicker)findViewById(R.id.timePicker1);
		btn_ok = (Button)findViewById(R.id.ok_button_child);
		btn_cancel = (Button)findViewById(R.id.cancel_button_child);
		
		title.setText(data.getStringExtra("paramTitle"));
		edit_dest.setText(data.getStringExtra("paramDest"));
		edit_loc.setText(data.getStringExtra("paramPlace"));
		
		date = data.getStringExtra("paramDate");
		time = data.getStringExtra("paramTime");
		tokenDate = date.split("-");
		mDate.updateDate(
				Integer.parseInt(tokenDate[0]), 
				Integer.parseInt(tokenDate[1])-1, 
				Integer.parseInt(tokenDate[2]));
		
		tokenTime = time.split(":");
		mTime.setCurrentHour(
				Integer.parseInt(tokenTime[0]));
		mTime.setCurrentMinute(
				Integer.parseInt(tokenTime[1]));
		
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ok_button_child:
			
			/**
			 * Send the parameter value
			 * to the previous activity
			 * as result when the OK
			 * button clicked
			 */
			
			Intent data = new Intent();
			data.putExtra("paramKey", key);
			data.putExtra("paramTitle", 
					title.getText().toString());
			data.putExtra("paramDest", 
					edit_dest.getText().toString());
			data.putExtra("paramLoc", 
					edit_loc.getText().toString());
			data.putExtra("paramDate", 
					mDate.getYear() + "-" + 
					(mDate.getMonth()+1) + "-" + 
					mDate.getDayOfMonth());
			data.putExtra("paramTime", 
					mTime.getCurrentHour() + ":" + 
			        mTime.getCurrentMinute());
			
			setResult(RESULT_OK, data);
			finish();
			break;

		case R.id.cancel_button_child:
			finish();
			break;
		}
		
	}
}
