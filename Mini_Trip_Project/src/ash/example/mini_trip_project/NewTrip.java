package ash.example.mini_trip_project;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewTrip extends Activity implements OnClickListener {
	Button btn_ok;
	Button btn_cancel;
	EditText dest;
	EditText event;
	Intent data;
	Date presentTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtrip);
		
		dest = (EditText)findViewById(R.id.destination);
		event = (EditText)findViewById(R.id.event);
		
		btn_ok = (Button)findViewById(R.id.ok_button);
		btn_cancel = (Button)findViewById(R.id.cancel_button);
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		presentTime = new Date();

	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ok_button:
			
			/**
			 * Send the parameter value
			 * to the previous activity
			 * as result when the OK
			 * button clicked
			 */
			
			data = new Intent();
			data.putExtra("paramDest", dest.getText().toString());
			data.putExtra("paramEvent", event.getText().toString());
			data.putExtra("paramDate", 
					(presentTime.getYear()+1900) + "-" + 
					(presentTime.getMonth()+1) + "-" + 
					presentTime.getDate());
			
			setResult(RESULT_OK, data);
			finish();
			break;

		case R.id.cancel_button:
			finish();
			break;
		}
	}
}
