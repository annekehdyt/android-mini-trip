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

public class NewTripChild extends Activity implements OnClickListener{
	TextView title;
	EditText edit_dest;
	EditText edit_loc;
	DatePicker date;
	TimePicker time;
	Button btn_ok;
	Button btn_cancel;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtrip_child);
		
		title = (TextView)findViewById(R.id.textView1);
		edit_dest = (EditText)findViewById(R.id.editText1);
		edit_loc = (EditText)findViewById(R.id.editText2);
		date = (DatePicker)findViewById(R.id.datePicker1);
		time = (TimePicker)findViewById(R.id.timePicker1);
		btn_ok = (Button)findViewById(R.id.ok_button_child);
		btn_cancel = (Button)findViewById(R.id.cancel_button_child);

		
		title.setText("New Trip");
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
			
			data.putExtra("paramDest", 
					edit_dest.getText().toString());
			data.putExtra("paramLoc", 
					edit_loc.getText().toString());
			data.putExtra("paramDate", 
					date.getYear() + "-" + 
					(date.getMonth()+1) + "-" + 
					date.getDayOfMonth());
			data.putExtra("paramTime", 
					time.getCurrentHour() + ":" + 
			        time.getCurrentMinute());

			setResult(RESULT_OK, data);
			finish();
			break;

		case R.id.cancel_button_child:
			finish();
			break;
		}
		
	}

}
