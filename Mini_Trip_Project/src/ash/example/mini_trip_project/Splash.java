package ash.example.mini_trip_project;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import ash.example.mini_trip_project.R;

/**
 * Appear the image of application
 * in 2.5 seconds when the forst application
 * started
 * @author Anne Soraya
 *
 */
public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
			
			public void run() {
				finish();
				
				Intent intent = new Intent(Splash.this, MiniTrip.class);
				Splash.this.startActivity(intent);
			}
		}, 2500);
	}
}

/**
Reference
http://myandroidsolutions.blogspot.kr/
*/