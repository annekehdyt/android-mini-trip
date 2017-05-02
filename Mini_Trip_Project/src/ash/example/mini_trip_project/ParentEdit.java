package ash.example.mini_trip_project;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ParentEdit extends Activity implements OnClickListener {

	EditText dest_edit;
	EditText event_edit;
	Date time;
	Intent data;
	Button btn_ok;
	Button btn_cancel;
	int key;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtrip);
		
		dest_edit = (EditText)findViewById(R.id.destination);
		event_edit = (EditText)findViewById(R.id.event);
		btn_ok = (Button)findViewById(R.id.ok_button);
		btn_cancel = (Button)findViewById(R.id.cancel_button);
		time = new Date();
		
		Intent intent = getIntent();
		key = intent.getIntExtra("paramKey", 0);
		dest_edit.setText(intent.getStringExtra("paramDest"));
		event_edit.setText(intent.getStringExtra("paramEvent"));
		
		
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		
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
			data.putExtra("paramDest", dest_edit.getText().toString());
			data.putExtra("paramEvent", event_edit.getText().toString());
			data.putExtra("paramDate", 
					(time.getYear()+1900) + "-" + 
					(time.getMonth()+1) + "-" + 
					time.getDate());
			data.putExtra("paramKey", key);
			
			setResult(RESULT_OK, data);
			finish();
			break;

		case R.id.cancel_button:
			finish();
			break;
		}
		
	}
	
	
}
